package kuchtastefan.service;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.hint.HintDB;
import kuchtastefan.hint.HintName;
import kuchtastefan.quest.questGiver.QuestGiverCharacterDB;
import kuchtastefan.utility.AutosaveCount;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import kuchtastefan.world.location.Location;
import kuchtastefan.world.location.locationStage.LocationStage;
import kuchtastefan.world.location.locationStage.specificLocationStage.LocationStageQuestGiver;

public class LocationService {

    private final Location location;

    public LocationService(Location location) {
        this.location = location;
    }

    /**
     * Displays the menu for interacting with a specific location.
     * Allows the hero to explore the location, view hero menu options, or go back on the path.
     * Also displays discovered stages within the location and their completion status.
     *
     * @param hero            The hero exploring the location.
     * @param heroMenuService The service handling hero menu actions.
     */
    public void locationMenu(Hero hero, HeroMenuService heroMenuService) {
        HintDB.printHint(HintName.LOCATION_HINT);
        int index = 3;

        while (true) {
            QuestGiverCharacterDB.setAllQuestGiversName(hero);
            location.printHeader();
            location.printMenu(index);

            int choice = InputUtil.intScanner();
            if (choice < 0 || choice > location.getLocationStages().size() + 2) {
                PrintUtil.printEnterValidInput();
            } else {
                if (choice == 0) {
                    break;
                }

                if (choice == 1) {
                    exploreLocationStage(hero, location, determineLocationStage(location, location.getStageCompleted()));
                    continue;
                }

                if (choice == 2) {
                    heroMenuService.heroCharacterMenu(hero);
                    continue;
                }

                if (location.getLocationStage(choice - index).isStageDiscovered()) {
                    exploreLocationStage(hero, location, location.getLocationStage(choice - index));
                } else {
                    PrintUtil.printEnterValidInput();
                }
            }
        }
    }

    /**
     * Method is responsible for exploring LocationStage.
     * You can explore LocationStage only if certain conditions are fulfilled
     * If stage is successfully completed, increase counter of stageCompleted located in Location
     * If stageCompleted meet value of stageTotal, location is completed and rewards are granted
     *
     * @param hero               Exploring LocationStage
     * @param location           which stage belong
     */
    public void exploreLocationStage(Hero hero, Location location, LocationStage locationStage) {
        AutosaveCount.checkAutosaveCount(hero);

        locationStage.setStageDiscovered(true);
        if (!locationStage.canHeroEnterStage(hero)) {
            return;
        }

        // LocationStage header
        PrintUtil.printMenuHeader(locationStage.getStageName());
//        PrintUtil.printLongDivider();
//        System.out.println("\t\t\t" + locationStage.getStageName());
//        PrintUtil.printLongDivider();

        // Explore location stage
        boolean isStageCompleted = locationStage.exploreStage(hero, location);

        // check if stage is successfully cleared
        if (isStageCompleted && !locationStage.isStageCompleted()) {
            discoverNextStage(location, location.getStageCompleted() + 1);
            locationStage.completeStage();
            hero.restoreHealthAndManaAfterTurn();
        }

        // Completing all location stages
        if (location.getStageCompleted() == location.getStageTotal()) {
            location.setCleared(true);
            hero.checkIfQuestObjectivesAndQuestIsCompleted();
        }
    }

    private void discoverNextStage(Location location, int locationStageOrder) {
        LocationStage nextLocationStage = location.getLocationStages().get(locationStageOrder);
        if (nextLocationStage != null) {
            nextLocationStage.setStageDiscovered(true);

            if (nextLocationStage instanceof LocationStageQuestGiver locationStageQuestGiver) {
                locationStageQuestGiver.setStageName(QuestGiverCharacterDB.returnQuestGiverName(locationStageQuestGiver.getQuestGiverId()));
            }
        }
    }

    private LocationStage determineLocationStage(Location location, int locationStageOrder) {
        LocationStage locationStage = location.getLocationStages().get(locationStageOrder);

        if (locationStage instanceof LocationStageQuestGiver) {
            if (locationStage.isStageDiscovered() && location.getLocationStage(locationStageOrder + 1) != null) {
                locationStage = location.getLocationStages().get(locationStageOrder + 1);
            } else {
                discoverNextStage(location, locationStageOrder + 1);
            }
        } else {
            if (locationStage != null && locationStage.isStageCompleted()) {
                locationStage = location.getLocationStages().get(locationStageOrder + 1);

                discoverNextStage(location, locationStageOrder);
            }
        }

        if (locationStage == null) {
            locationStage = location.getLocationStages().get(location.getStageCompleted() - 1);
        }

        return locationStage;
    }
}


