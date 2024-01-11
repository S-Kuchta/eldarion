package kuchtastefan.quest;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.utility.PrintUtil;

import java.util.List;

public class Quest {
    private final String questName;
    private final String questDescription;
    private final int questLevel;
    private boolean questCompleted;
    private final List<QuestObjective> questObjectives;
    private final QuestReward questReward;


    public Quest(String questName, String questDescription, int questLevel, List<QuestObjective> questObjectives, QuestReward questReward) {
        this.questName = questName;
        this.questDescription = questDescription;
        this.questLevel = questLevel;
        this.questObjectives = questObjectives;
        this.questReward = questReward;
        this.questCompleted = false;
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
}
