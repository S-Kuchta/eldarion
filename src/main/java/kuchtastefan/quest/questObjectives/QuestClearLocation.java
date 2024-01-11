package kuchtastefan.quest.questObjectives;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.regions.locations.Location;

public class QuestClearLocation extends QuestObjective {
    private final Location location;

    public QuestClearLocation(Location location) {
        this.location = location;
    }

    @Override
    public void checkCompleted(Hero hero) {
        setCompleted(this.location.isCleared());
    }
}
