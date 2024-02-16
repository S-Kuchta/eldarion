package kuchtastefan.regions.locations;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.regions.events.FindItemEvent;

public class LocationStageFindItem extends LocationStage {
    private final int itemId;

    public LocationStageFindItem(String stageName, int itemId) {
        super(stageName);
        this.itemId = itemId;
    }

    @Override
    public boolean exploreStage(Hero hero, Location location) {
        new FindItemEvent(location.getLocationId(), this.itemId).eventOccurs(hero);
        return true;
    }
}
