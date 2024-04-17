package kuchtastefan.world.location.locationStage.specificLocationStage;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.world.event.specificEvent.FindItemEvent;
import kuchtastefan.world.location.Location;
import kuchtastefan.world.location.locationStage.LocationStage;

public class LocationStageFindItem extends LocationStage {
    private final int[] itemsId;

    public LocationStageFindItem(String stageName, int[] itemsId) {
        super(stageName);
        this.itemsId = itemsId;
    }

    @Override
    public boolean exploreStage(Hero hero, Location location) {
        for (int itemId : this.itemsId) {
            new FindItemEvent(location.getLocationLevel(), itemId).eventOccurs(hero);
        }
        return true;
    }
}
