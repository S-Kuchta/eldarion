package kuchtastefan.region.location;


import kuchtastefan.character.hero.Hero;
import kuchtastefan.region.event.FindTreasureEvent;

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
