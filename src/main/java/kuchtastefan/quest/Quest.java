package kuchtastefan.quest;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.hero.save.quest.HeroQuest;
import kuchtastefan.character.hero.save.quest.HeroQuestObjective;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.ResetObjectiveProgress;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.annotationStrategy.Exclude;
import kuchtastefan.utility.printUtil.PrintUtil;
import kuchtastefan.utility.printUtil.QuestPrint;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Quest {

    private final int id;
    private final int level;
    private final int[] objectivesIds;
    private final String title;
    private final String description;
    private final QuestReward reward;
    private final boolean instantTurnIn;
    @Exclude
    private Map<Integer, QuestObjective> objectives;
    @Exclude
    private QuestStatus status;
    @Exclude
    private String statusIcon;

    public Quest(int id, String title, String description, int level,
                 int[] objectivesIds, QuestReward reward, boolean instantTurnIn) {

        this.objectives = new HashMap<>();
        this.id = id;
        this.title = title;
        this.description = description;
        this.level = level;
        this.objectivesIds = objectivesIds;
        this.reward = reward;
        this.instantTurnIn = instantTurnIn;
    }


    public void startTheQuest(Hero hero) {
        this.status = QuestStatus.ACCEPTED;
        this.verifyQuestObjectiveCompletion(hero);
        this.checkIfQuestIsCompleted(hero);
        this.setStatusIcon();
        hero.getSaveGameEntities().getHeroQuests().addEntity(new HeroQuest(this.id, this.status));
        hero.getSaveGameEntities().getHeroQuestObjectives().addEntities(getConvertedObjectiveMap());
    }

    public Map<Integer, HeroQuestObjective> getConvertedObjectiveMap() {
        Map<Integer, HeroQuestObjective> convertedQuestObjectiveMap = new HashMap<>();
        for (QuestObjective questObjective : this.objectives.values()) {
            HeroQuestObjective heroQuestObjective = new HeroQuestObjective(questObjective.getId(), questObjective.isCompleted());
            convertedQuestObjectiveMap.put(questObjective.getId(), heroQuestObjective);
        }

        return convertedQuestObjectiveMap;
    }

    public boolean containsQuestObjective(int questObjectiveId) {
        return this.objectives.containsKey(questObjectiveId);
    }

    /**
     * Check if Quest is completed
     * Quest is completed if all questObjectives belonging to quest are completed.
     */
    public void checkIfQuestIsCompleted(Hero hero) {
        if (this.status != QuestStatus.ACCEPTED) {
            return;
        }

        for (QuestObjective questObjective : this.objectives.values()) {
            if (!questObjective.isCompleted()) {
                return;
            }
        }

        if (hero.getSaveGameEntities().getHeroQuests().containsEntity(this.id)) {
            QuestPrint.printCompleteQuestText(this.title);
        }

        this.status = QuestStatus.COMPLETED;
        if (this.instantTurnIn) {
            this.turnInTheQuest(hero);
        }
    }

    private void verifyQuestObjectiveCompletion(Hero hero) {
        for (QuestObjective questObjective : this.objectives.values()) {
            questObjective.verifyQuestObjectiveCompletion(hero);
        }
    }

    /**
     * Turn in the quest, give quest reward to hero and remove items/killed enemy etc. from hero
     */
    public void turnInTheQuest(Hero hero) {
        if (this.getStatus().equals(QuestStatus.COMPLETED)) {
            PrintUtil.printLongDivider();
            QuestPrint.printCompleteQuestText(this.title);
            this.reward.giveQuestReward(hero);
            this.status = QuestStatus.TURNED_IN;
            PrintUtil.printLongDivider();
        }

        for (QuestObjective questObjective : this.objectives.values()) {
            if (questObjective instanceof ResetObjectiveProgress resetObjectiveProgress) {
                resetObjectiveProgress.resetCompletedQuestObjectiveAssignment(hero);
            }
        }
    }

    public void setInitialQuestStatus(Hero hero) {
        if (this.status == null || this.status == QuestStatus.UNAVAILABLE) {
            if (this.canBeQuestAccepted(hero)) {
                this.status = QuestStatus.AVAILABLE;
            } else {
                this.status = QuestStatus.UNAVAILABLE;
            }
        }
    }

    public boolean canBeQuestAccepted(Hero hero) {
        return hero.getLevel() >= this.level;
    }

    public void setStatusIcon() {
        switch (this.getStatus()) {
            case QuestStatus.UNAVAILABLE -> statusIcon = "-" + ConsoleColor.WHITE + "!" + ConsoleColor.RESET + "-";
            case QuestStatus.AVAILABLE ->
                    statusIcon = "-" + ConsoleColor.YELLOW_BOLD_BRIGHT + "!" + ConsoleColor.RESET + "-";
            case QuestStatus.COMPLETED ->
                    statusIcon = "-" + ConsoleColor.YELLOW_BOLD_BRIGHT + "?" + ConsoleColor.RESET + "-";
            case QuestStatus.TURNED_IN -> statusIcon = " -- Completed --";
            default -> statusIcon = "";
        }
    }
}
