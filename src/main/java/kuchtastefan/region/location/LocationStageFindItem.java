package kuchtastefan.region.location;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.region.event.FindItemEvent;

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
