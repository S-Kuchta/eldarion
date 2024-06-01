package kuchtastefan.quest;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.hero.save.quest.HeroQuest;
import kuchtastefan.character.hero.save.quest.HeroQuestObjective;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.QuestObjectiveDB;
import kuchtastefan.quest.questObjectives.RemoveObjectiveProgress;
import kuchtastefan.utility.Exclude;
import kuchtastefan.utility.printUtil.PrintUtil;
import kuchtastefan.utility.printUtil.QuestPrint;
import lombok.*;

import java.util.*;

@Getter
@Setter
public class Quest {

    private final int questId;
    private final String questName;
    private final String questDescription;
    private final int questLevel;
    private final int[] questObjectivesIds;
    @Exclude
    private final Map<Integer, QuestObjective> questObjectives;
    private final QuestReward questReward;
    private QuestStatus questStatus;
    private final boolean instantTurnIn;

    public Quest(int questId, String questName, String questDescription, int questLevel,
                 int[] questObjectivesIds, QuestReward questReward, boolean instantTurnIn) {

        this.questObjectives = new HashMap<>();
        this.questId = questId;
        this.questName = questName;
        this.questDescription = questDescription;
        this.questLevel = questLevel;
        this.questObjectivesIds = questObjectivesIds;
        this.questReward = questReward;
        this.instantTurnIn = instantTurnIn;
    }


    public void startTheQuest(Hero hero) {
        this.setQuestStatus(QuestStatus.ACCEPTED);
        this.verifyQuestObjectiveCompletion(hero);
        this.checkIfQuestIsCompleted(hero);
        hero.getHeroQuestList().addEntity(new HeroQuest(this.questId, this.questStatus, getHeroQuestObjectiveMap()));
    }

    public Map<Integer, HeroQuestObjective> getHeroQuestObjectiveMap() {
        Map<Integer, HeroQuestObjective> heroQuestObjectiveMap = new HashMap<>();
        for (QuestObjective questObjective : this.getQuestObjectivesList()) {
            HeroQuestObjective heroQuestObjective = new HeroQuestObjective(questObjective.getQuestObjectiveId(), questObjective.isCompleted());
            heroQuestObjectiveMap.put(questObjective.getQuestObjectiveId(), heroQuestObjective);
        }

        return heroQuestObjectiveMap;
    }

    public List<QuestObjective> getQuestObjectivesList() {
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
        verifyQuestObjectiveCompletion(hero);
        for (QuestObjective questObjective : this.questObjectives.values()) {
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

    private void verifyQuestObjectiveCompletion(Hero hero) {
        for (QuestObjective questObjective : this.questObjectives.values()) {
            questObjective.verifyQuestObjectiveCompletion(hero);
        }
    }

    /**
     * Turn in the quest, give quest reward to hero and remove items/killed enemy etc. from hero
     */
    public void turnInTheQuest(Hero hero) {
        if (this.getQuestStatus().equals(QuestStatus.COMPLETED)) {
            PrintUtil.printLongDivider();
            QuestPrint.printCompleteQuestText(this.questName);
            this.getQuestReward().giveQuestReward(hero);
            this.setQuestStatus(QuestStatus.TURNED_IN);
            PrintUtil.printLongDivider();
        }

        for (QuestObjective questObjective : this.getQuestObjectivesList()) {
            if (questObjective instanceof RemoveObjectiveProgress removeObjectiveProgress) {
                removeObjectiveProgress.removeCompletedQuestObjectiveAssignment(hero);
            }
        }
    }

    public boolean containsQuestObjective(int questObjectiveId) {
        return this.questObjectives.containsKey(questObjectiveId);
    }

    public void convertIdToQuestObjectiveMap() {
        for (int questObjectiveId : this.questObjectivesIds) {
            questObjectives.put(questObjectiveId, QuestObjectiveDB.getQuestObjectiveById(questObjectiveId));
        }
    }

    public boolean canBeQuestAccepted(Hero hero) {
        return hero.getLevel() >= this.questLevel;
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
