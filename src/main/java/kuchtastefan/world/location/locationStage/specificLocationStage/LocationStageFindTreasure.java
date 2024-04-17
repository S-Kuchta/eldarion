package kuchtastefan.world.location.locationStage.specificLocationStage;


import kuchtastefan.character.hero.Hero;
import kuchtastefan.world.event.specificEvent.FindTreasureEvent;
import kuchtastefan.world.location.Location;
import kuchtastefan.world.location.locationStage.LocationStage;

public class LocationStageFindTreasure extends LocationStage {

    public LocationStageFindTreasure(String stageName) {
        super(stageName);
    }

    @Override
    public boolean exploreStage(Hero hero, Location location) {
        if (!this.isStageCompleted()) {
            new FindTreasureEvent(location.getLocationLevel()).eventOccurs(hero);
        }

        return true;
    }
}
