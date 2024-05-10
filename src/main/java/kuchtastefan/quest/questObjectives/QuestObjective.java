package kuchtastefan.quest.questObjectives;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.utility.ConsoleColor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public abstract class QuestObjective {

    private final int questObjectiveId;
    protected boolean completed;
    protected final String questObjectiveName;

    public QuestObjective(int questObjectiveId, String questObjectiveName) {
        this.questObjectiveId = questObjectiveId;
        this.completed = false;
        this.questObjectiveName = questObjectiveName;
    }

    public abstract void questObjectiveAssignment(Hero hero);

    public abstract void checkIfQuestObjectiveIsCompleted(Hero hero);

    public String getQuestObjectiveName() {
        return ConsoleColor.YELLOW + questObjectiveName + ConsoleColor.RESET;
    }

    public void setCompleted(Hero hero, boolean completed) {
        this.completed = completed;
        System.out.println("\tYou have completed " + this.getQuestObjectiveName() + " quest objective");
        hero.getHeroQuests().checkQuestCompletion(hero, this.questObjectiveId);
    }
}
