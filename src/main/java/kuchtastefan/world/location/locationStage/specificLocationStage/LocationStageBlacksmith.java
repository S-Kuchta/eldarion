package kuchtastefan.world.location.locationStage.specificLocationStage;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.service.BlacksmithService;
import kuchtastefan.service.BlacksmithingService;
import kuchtastefan.world.location.Location;
import kuchtastefan.world.location.locationStage.CanEnterStageAfterComplete;
import kuchtastefan.world.location.locationStage.LocationStage;

public class LocationStageBlacksmith extends LocationStage implements CanEnterStageAfterComplete {

    public LocationStageBlacksmith(String stageName) {
        super(stageName);
    }

    @Override
    public boolean exploreStage(Hero hero, Location location) {
//        BlacksmithService blacksmithService = new BlacksmithService();
//        blacksmithService.blacksmithMenu(hero);
        BlacksmithingService blacksmithingService = new BlacksmithingService();
        blacksmithingService.blacksmithingMenu(hero);
        return true;
    }
}
