package kuchtastefan.quest;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.quest.questObjectives.QuestObjective;
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
    private boolean questCompleted;
    private List<? extends QuestObjective> questObjectives;
    private final QuestReward questReward;
    private boolean isTurnedIn;


    public Quest(int questId, String questName, String questDescription, int questLevel, List<? extends QuestObjective> questObjectives, QuestReward questReward) {
        this.questId = questId;
        this.questName = questName;
        this.questDescription = questDescription;
        this.questLevel = questLevel;
        this.questObjectives = questObjectives;
        this.questReward = questReward;
        this.questCompleted = false;
        this.isTurnedIn = false;
        questReward.setQuestLevel(this.questLevel);
    }

    public void checkIfQuestAndQuestObjectivesIsCompleted() {
        boolean completed = true;
        for (QuestObjective questObjective : this.questObjectives) {
            if (!questObjective.isCompleted()) {
                completed = false;
                break;
            }
        }
        if (completed && !this.questCompleted) {
            System.out.println("\t--> You have completed Quest " + this.questName + " <--");
            this.questCompleted = true;
        }
    }

    public void turnInTheQuestAndGiveReward(Hero hero) {
        if (this.questCompleted) {
            PrintUtil.printLongDivider();
            System.out.println("\t\t-- You have completed Quest " + this.questName);
            PrintUtil.printLongDivider();
            this.questReward.giveQuestReward(hero);
        }
    }

//    public String getQuestName() {
//        return questName;
//    }
//
//    public String getQuestDescription() {
//        return questDescription;
//    }
//
//    public int getQuestLevel() {
//        return questLevel;
//    }
//
//    public QuestReward getQuestReward() {
//        return questReward;
//    }
//
//    public boolean isCompleted() {
//        return questCompleted;
//    }
//
//    public void setCompleted(boolean completed) {
//        this.questCompleted = completed;
//    }
//
//    public List<? extends QuestObjective> getQuestObjectives() {
//        return questObjectives;
//    }
//
//    public void setQuestObjectives(List<? extends QuestObjective> questObjectives) {
//        this.questObjectives = questObjectives;
//    }
//
//    public boolean isTurnedIn() {
//        return isTurnedIn;
//    }
//
//    public void setTurnedIn(boolean turnedIn) {
//        isTurnedIn = turnedIn;
//    }
//
//    public boolean isQuestCompleted() {
//        return questCompleted;
//    }
//
//    public int getQuestId() {
//        return questId;
//    }

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
