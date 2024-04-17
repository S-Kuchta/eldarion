package kuchtastefan.world.location.locationStage.specificLocationStage;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.world.event.specificEvent.NoOutcomeEvent;
import kuchtastefan.world.location.Location;
import kuchtastefan.world.location.locationStage.CanEnterStageAfterComplete;
import kuchtastefan.world.location.locationStage.LocationStage;

public class LocationStageNothingOutcome extends LocationStage implements CanEnterStageAfterComplete {

    public LocationStageNothingOutcome(String stageName) {
        super(stageName);
    }

    @Override
    public boolean exploreStage(Hero hero, Location location) {
        return new NoOutcomeEvent(location.getLocationLevel()).eventOccurs(hero);
    }
}
