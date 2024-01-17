package kuchtastefan.quest.questObjectives;

import kuchtastefan.characters.hero.Hero;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
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

}
