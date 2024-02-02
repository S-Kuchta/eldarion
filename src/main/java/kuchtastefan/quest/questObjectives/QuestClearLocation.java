package kuchtastefan.quest.questObjectives;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.regions.locations.Location;
import lombok.Getter;

@Getter
public class QuestClearLocation extends QuestObjective {
    private final Location location;

    public QuestClearLocation(String questObjectiveName, Location location) {
        super(questObjectiveName);
        this.location = location;
    }

    @Override
    public void checkIfQuestObjectiveIsCompleted(Hero hero) {
        setCompleted(this.location.isCleared());
    }

    @Override
    public void removeCompletedItemsOrEnemies(Hero hero) {

    }

    @Override
    public void printQuestObjectiveAssignment(Hero hero) {

    }
}
