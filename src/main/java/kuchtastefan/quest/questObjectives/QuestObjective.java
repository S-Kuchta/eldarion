package kuchtastefan.quest.questObjectives;

import kuchtastefan.characters.hero.Hero;

public abstract class QuestObjective {
    protected boolean completed;
    protected final String questObjectiveName;

    public QuestObjective(String questObjectiveName) {
        this.completed = false;
        this.questObjectiveName = questObjectiveName;
    }

    public abstract void printQuestObjectiveAssignment(Hero hero);

    public abstract void checkQuestObjectiveCompleted(Hero hero);

    public abstract void removeCompletedItemsOrEnemies(Hero hero);

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
