package kuchtastefan.quest.questObjectives;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.utility.ConsoleColor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


@Setter
@Getter
public abstract class QuestObjective {

    private final int id;
    protected boolean completed;
    protected final String title;

    public QuestObjective(int id, String title) {
        this.id = id;
        this.completed = false;
        this.title = title;
    }

    public abstract void printQuestObjectiveProgress(Hero hero);

    public abstract void verifyQuestObjectiveCompletion(Hero hero);

    public void printProgress(Hero hero) {
        if (hero.getSaveGameEntities().getHeroQuestObjectives().containsEntity(this.id)) {
            printQuestObjectiveProgress(hero);
        }
    }

    public String getTitle() {
        return ConsoleColor.YELLOW + title + ConsoleColor.RESET;
    }

    public void setCompleted(Hero hero, boolean completed) {
        if (!this.completed) {
            this.completed = completed;

            if (hero.getSaveGameEntities().getHeroQuestObjectives().containsEntity(this.id)) {
                System.out.println("\tYou have completed " + this.getTitle() + " quest objective");
            }
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        QuestObjective that = (QuestObjective) object;
        return id == that.id && completed == that.completed && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, completed, title);
    }
}
