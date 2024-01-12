package kuchtastefan.quest;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.utility.PrintUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Quest {
    private final String questName;
    private final String questDescription;
    private final int questLevel;
    private boolean questCompleted;
    private List<QuestObjective> questObjectives;
    private final QuestReward questReward;
    private boolean isTurnedIn;


    public Quest(String questName, String questDescription, int questLevel, List<QuestObjective> questObjectives, QuestReward questReward) {
        this.questName = questName;
        this.questDescription = questDescription;
        this.questLevel = questLevel;
        this.questObjectives = questObjectives;
        this.questReward = questReward;
        this.questCompleted = false;
        this.isTurnedIn = false;
        questReward.setQuestLevel(this.questLevel);
    }

    public Quest() {
    }

    public Quest(String questName, String questDescription, int questLevel, QuestReward questReward) {
        this.questName = questName;
        this.questDescription = questDescription;
        this.questLevel = questLevel;
        this.questObjectives = new ArrayList<>();
        this.questReward = questReward;
        this.questCompleted = false;
        this.isTurnedIn = false;
        questReward.setQuestLevel(this.questLevel);
    }

    public void checkQuestObjectivesCompleted() {
        boolean completed = true;
        for (QuestObjective questObjective : this.questObjectives) {
            if (!questObjective.isCompleted()) {
                completed = false;
                break;
            }
        }
        if (completed) {
            System.out.println("\t--> You completed Quest " + this.questName + " <--");
            this.questCompleted = true;
        }
    }

    public void completeTheQuest(Hero hero) {
        if (this.questCompleted) {
            PrintUtil.printLongDivider();
            System.out.println("\t\t-- You completed the Quest " + this.questName);
            PrintUtil.printLongDivider();
            this.questReward.giveQuestReward(hero);
        }
    }

    public String getQuestName() {
        return questName;
    }

    public String getQuestDescription() {
        return questDescription;
    }

    public int getQuestLevel() {
        return questLevel;
    }

    public QuestReward getQuestReward() {
        return questReward;
    }

    public boolean isCompleted() {
        return questCompleted;
    }

    public void setCompleted(boolean completed) {
        this.questCompleted = completed;
    }

    public List<QuestObjective> getQuestObjectives() {
        return questObjectives;
    }

    public void setQuestObjectives(List<? extends QuestObjective> questObjectives) {
        this.questObjectives = (List<QuestObjective>) questObjectives;
    }

    public boolean isTurnedIn() {
        return isTurnedIn;
    }

    public void setTurnedIn(boolean turnedIn) {
        isTurnedIn = turnedIn;
    }

    public boolean isQuestCompleted() {
        return questCompleted;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quest quest = (Quest) o;

        return questLevel == quest.questLevel && Objects.equals(questName, quest.questName) && Objects.equals(questDescription, quest.questDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questName, questDescription, questLevel);
    }

    @Override
    public String toString() {
        return "Quest{" +
                "questName='" + questName + '\'' +
                ", questDescription='" + questDescription + '\'' +
                ", questLevel=" + questLevel +
                ", questCompleted=" + questCompleted +
                ", questObjectives=" + questObjectives +
                ", questReward=" + questReward +
                ", isTurnedIn=" + isTurnedIn +
                '}';
    }
}
