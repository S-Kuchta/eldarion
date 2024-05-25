package kuchtastefan.quest;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.QuestObjectiveDB;
import kuchtastefan.quest.questObjectives.RemoveObjectiveProgress;
import kuchtastefan.utility.printUtil.PrintUtil;
import kuchtastefan.utility.printUtil.QuestPrint;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Quest {
    private final int questId;
    private final String questName;
    private final String questDescription;
    private final int questLevel;
    private final int[] questObjectivesIds;
    private final QuestReward questReward;
    private QuestStatus questStatus;
    private final boolean instantTurnIn;


    public Quest(int questId, String questName, String questDescription, int questLevel,
                 int[] questObjectivesIds, QuestReward questReward, boolean instantTurnIn) {
        this.questId = questId;
        this.questName = questName;
        this.questDescription = questDescription;
        this.questLevel = questLevel;
        this.questObjectivesIds = questObjectivesIds;
        this.questReward = questReward;
        this.instantTurnIn = instantTurnIn;
    }


    public void startTheQuest(Hero hero) {
        if (!hero.getHeroQuests().containsQuest(this.questId)) {
            hero.getHeroQuests().addQuestToHeroAcceptedQuest(this);
            this.setQuestStatus(QuestStatus.ACCEPTED);
            hero.getHeroQuests().makeProgressInQuestObjective(hero, this.questId);
            this.checkIfQuestIsCompleted(hero);
        }
    }

    public List<QuestObjective> getQuestObjectives() {
        List<QuestObjective> questObjectives = new ArrayList<>();
        for (int questObjectiveId : this.questObjectivesIds) {
            questObjectives.add(QuestObjectiveDB.returnQuestObjectiveFromDb(questObjectiveId));
        }

        return questObjectives;
    }

    /**
     * Check if Quest is completed
     * Quest is completed if all questObjectives belonging to quest are completed.
     */
    public void checkIfQuestIsCompleted(Hero hero) {
        for (QuestObjective questObjective : this.getQuestObjectives()) {
            if (!questObjective.isCompleted()) {
                return;
            }
        }

        if (!this.questStatus.equals(QuestStatus.COMPLETED)) {
            QuestPrint.printCompleteQuestText(this.questName);
            this.questStatus = QuestStatus.COMPLETED;
        }

        if (this.instantTurnIn) {
            this.turnInTheQuest(hero);
        }
    }

    /**
     * Turn in the quest, give quest reward to hero and remove items/killed enemy etc. from hero
     */
    public void turnInTheQuest(Hero hero) {
        if (this.getQuestStatus().equals(QuestStatus.COMPLETED)) {
            PrintUtil.printLongDivider();
            QuestPrint.printCompleteQuestText(this.questName);
            PrintUtil.printLongDivider();
            this.getQuestReward().giveQuestReward(hero);
            this.setQuestStatus(QuestStatus.TURNED_IN);
        }

        for (QuestObjective questObjective : this.getQuestObjectives()) {
            if (questObjective instanceof RemoveObjectiveProgress removeObjectiveProgress) {
                removeObjectiveProgress.removeCompletedQuestObjectiveAssignment(hero);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quest quest = (Quest) o;

        return questId == quest.questId && questLevel == quest.questLevel && Objects.equals(questName, quest.questName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questName, questDescription, questLevel);
    }
}
