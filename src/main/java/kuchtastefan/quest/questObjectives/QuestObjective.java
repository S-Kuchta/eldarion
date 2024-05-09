package kuchtastefan.quest.questObjectives;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.utility.ConsoleColor;
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

    public abstract void questObjectiveAssignment(Hero hero);

    public abstract void checkIfQuestObjectiveIsCompleted(Hero hero);

    public String getQuestObjectiveName() {
        return ConsoleColor.YELLOW + questObjectiveName + ConsoleColor.RESET;
    }

    public abstract boolean makeProgressInQuestObjective(QuestObjectiveTarget questObjectiveTarget, Hero hero);

}
