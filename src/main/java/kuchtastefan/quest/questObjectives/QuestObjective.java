package kuchtastefan.quest.questObjectives;

import kuchtastefan.characters.hero.Hero;

public abstract class QuestObjective {
    private boolean completed;

    public QuestObjective() {
        this.completed = false;
    }

    public abstract void checkCompleted(Hero hero);

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
