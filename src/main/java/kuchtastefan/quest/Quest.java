package kuchtastefan.quest;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.RemoveObjectiveProgress;
import kuchtastefan.service.QuestService;
import kuchtastefan.utility.PrintUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Quest {
    private final int questId;
    private final String questName;
    private final String questDescription;
    private final int questLevel;
    private List<? extends QuestObjective> questObjectives;
    private final QuestReward questReward;
    private QuestStatus questStatus;
    private final boolean instantTurnIn;


    public Quest(int questId, String questName, String questDescription, int questLevel,
                 List<? extends QuestObjective> questObjectives, QuestReward questReward, boolean instantTurnIn) {
        this.questId = questId;
        this.questName = questName;
        this.questDescription = questDescription;
        this.questLevel = questLevel;
        this.questObjectives = questObjectives;
        this.questReward = questReward;
        this.instantTurnIn = instantTurnIn;
    }


    public void startTheQuest(Hero hero) {
        if (!hero.getHeroAcceptedQuest().containsValue(this)) {
            hero.getHeroAcceptedQuest().put(this.getQuestId(), this);
            hero.checkIfQuestObjectivesAndQuestIsCompleted();
            this.setQuestStatus(QuestStatus.ACCEPTED);
        }
    }

    /**
     * Check if is Quest completed, Quest is completed if all
     * questObjectives belonging to quest are completed.
     */
    public void checkIfAllQuestObjectivesAreCompleted(Hero hero) {
        boolean completed = true;
        for (QuestObjective questObjective : this.questObjectives) {
            if (!questObjective.isCompleted()) {
                completed = false;
                break;
            }
        }

        if (completed && !this.questStatus.equals(QuestStatus.COMPLETED)) {
            PrintUtil.printCompleteQuestText(this.questName);
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
            PrintUtil.printCompleteQuestText(this.getQuestName());
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
