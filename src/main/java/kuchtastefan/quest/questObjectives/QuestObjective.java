package kuchtastefan.quest.questObjectives;

import kuchtastefan.characters.hero.Hero;

public class QuestObjective {
    protected boolean completed;
    protected final String questObjectiveName;

    public QuestObjective(String questObjectiveName) {
        this.completed = false;
        this.questObjectiveName = questObjectiveName;
    }

    public void printQuestObjectiveAssignment(Hero hero) {

    }

    public void checkQuestObjectiveCompleted(Hero hero) {

    }

    public void removeCompletedItemsOrEnemies(Hero hero) {

    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getQuestObjectiveName() {
        return questObjectiveName;
    }
}
