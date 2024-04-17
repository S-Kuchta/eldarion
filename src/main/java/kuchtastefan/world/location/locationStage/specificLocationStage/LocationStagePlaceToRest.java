package kuchtastefan.world.location.locationStage.specificLocationStage;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import kuchtastefan.world.location.Location;
import kuchtastefan.world.location.locationStage.CanEnterStageAfterComplete;
import kuchtastefan.world.location.locationStage.LocationStage;

public class LocationStagePlaceToRest extends LocationStage implements CanEnterStageAfterComplete {

    public LocationStagePlaceToRest(String stageName) {
        super(stageName);
    }

    @Override
    public boolean exploreStage(Hero hero, Location location) {
        PrintUtil.printIndexAndText("0", "Go back");
        System.out.println();
        PrintUtil.printIndexAndText("1", "Rest");
        System.out.println();

        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {}
            case 1 -> hero.rest();
        }

        return true;
    }
}
