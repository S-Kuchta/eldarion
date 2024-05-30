package kuchtastefan.quest.questObjectives;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.utility.ConsoleColor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


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

    public abstract void printQuestObjectiveProgress(Hero hero);

    public abstract void verifyQuestObjectiveCompletion(Hero hero);

    public String getQuestObjectiveName() {
        return ConsoleColor.YELLOW + questObjectiveName + ConsoleColor.RESET;
    }

    public void setCompleted(Hero hero, boolean completed) {
        System.out.println(this.hashCode());
        this.completed = completed;
        System.out.println("\tYou have completed " + this.getQuestObjectiveName() + " quest objective");
        hero.getHeroQuests().checkQuestCompletion(hero, this.questObjectiveId);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        QuestObjective that = (QuestObjective) object;
        return questObjectiveId == that.questObjectiveId && completed == that.completed && Objects.equals(questObjectiveName, that.questObjectiveName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questObjectiveId, completed, questObjectiveName);
    }
}
