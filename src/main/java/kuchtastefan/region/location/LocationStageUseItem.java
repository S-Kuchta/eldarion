package kuchtastefan.region.location;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.region.event.UseItemEvent;
import lombok.Getter;

@Getter
public class LocationStageUseItem extends LocationStage {

    private final int itemId;
    private final LocationStage locationStage;

    public LocationStageUseItem(String stageName, int itemId, LocationStage locationStage) {
        super(stageName);
        this.itemId = itemId;
        this.locationStage = locationStage;
    }

    @Override
    public boolean exploreStage(Hero hero, Location location) {
        if (new UseItemEvent(location.getLocationLevel(), this.itemId).eventOccurs(hero)) {
            if (this.locationStage.exploreStage(hero, location)) {
                this.locationStage.setStageCompleted(true);
                ((RemoveLocationStageProgress) locationStage).removeProgressAfterCompletedStage();
                return true;
            }
        }

        return false;
    }
}
