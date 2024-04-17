package kuchtastefan.world.location.locationStage;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.world.event.specificEvent.UseItemEvent;
import kuchtastefan.world.location.Location;
import lombok.Getter;

@Getter
public class LocationStageUseItem extends LocationStage {

    private final int itemId;

    public LocationStageUseItem(String stageName, int itemId) {
        super(stageName);
        this.itemId = itemId;
    }

    @Override
    public boolean exploreStage(Hero hero, Location location) {
        return new UseItemEvent(location.getLocationLevel(), this.itemId).eventOccurs(hero);
    }
}
