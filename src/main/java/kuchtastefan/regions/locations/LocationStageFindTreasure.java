package kuchtastefan.regions.locations;


import kuchtastefan.characters.hero.Hero;
import kuchtastefan.regions.events.FindTreasureEvent;

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
