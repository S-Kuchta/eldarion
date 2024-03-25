package kuchtastefan.region.location;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.region.event.FindItemEvent;

public class LocationStageFindItem extends LocationStage {
//    private final int itemId;
    private final int[] itemsId;

    public LocationStageFindItem(String stageName, /*int itemId,*/ int[] itemsId) {
        super(stageName);
//        this.itemId = itemId;
        this.itemsId = itemsId;
    }

    @Override
    public boolean exploreStage(Hero hero, Location location) {
//        new FindItemEvent(location.getLocationId(), this.itemId).eventOccurs(hero);
        for (int itemId : this.itemsId) {
            new FindItemEvent(location.getLocationLevel(), itemId).eventOccurs(hero);
        }
        return true;
    }
}
