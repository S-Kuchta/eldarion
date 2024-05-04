package kuchtastefan.service;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.hint.HintDB;
import kuchtastefan.hint.HintName;
import kuchtastefan.item.ItemDB;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import kuchtastefan.world.location.Location;
import kuchtastefan.world.location.locationStage.CanEnterStageAfterComplete;
import kuchtastefan.world.location.locationStage.LocationStage;
import kuchtastefan.world.location.locationStage.RemoveLocationStageProgress;
import kuchtastefan.world.location.locationStage.specificLocationStage.LocationStageBlacksmith;
import kuchtastefan.world.location.locationStage.specificLocationStage.LocationStagePlaceToRest;
import kuchtastefan.world.location.locationStage.specificLocationStage.LocationStageQuestGiver;
import kuchtastefan.world.location.locationStage.specificLocationStage.LocationStageVendor;

import java.util.Map;

public class LocationService {

    /**
     * Displays the menu for interacting with a specific location.
     * Allows the hero to explore the location, view hero menu options, or go back on the path.
     * Also displays discovered stages within the location and their completion status.
     *
     * @param hero            The hero exploring the location.
     * @param location        The location being explored.
     * @param heroMenuService The service handling hero menu actions.
     */
    public void locationMenu(Hero hero, Location location, HeroMenuService heroMenuService) {
        HintDB.printHint(HintName.LOCATION_HINT);

        while (true) {
            PrintUtil.printExtraLongDivider();
            System.out.println(ConsoleColor.YELLOW + "\t" + location.getLocationName() + ConsoleColor.RESET
                    + "\tLocation level: " + location.getLocationLevel() + " "
                    + "\tStages completed: " + location.getStageCompleted() + " / " + location.getStageTotal() + " "
                    + "\tLocation difficulty: " + location.getLocationDifficulty());
            PrintUtil.printExtraLongDivider();

            // Print stage menu

            System.out.println("\tWhat do you want to do?");
            PrintUtil.printMenuOptions("Go back on the path", "Explore location", "Hero Menu");

            // Print discovered location Stages
            int index = 3;
            System.out.println(ConsoleColor.YELLOW_UNDERLINED + "\t\t\t\t\t\t\tLocation Stages\t\t\t\t\t\t\t" + ConsoleColor.RESET);
            for (Map.Entry<Integer, LocationStage> locationStage : location.getLocationStages().entrySet()) {
                if (locationStage.getValue().isStageDiscovered()) {
                    String completed;
                    if (locationStage.getValue() instanceof CanEnterStageAfterComplete && location.isCleared()) {
                        completed = "";
                    } else {
                        completed = locationStage.getValue().isStageCompleted() ? " - COMPLETED -" : "";
                    }

                    PrintUtil.printIndexAndText(String.valueOf(index + locationStage.getKey()),
                            locationStage.getValue().getStageName() + " " + completed);

                    System.out.println();
                }
            }

            try {
                // Select location
                int choice = InputUtil.intScanner();
                if (choice == 0) {
                    System.out.println("\tGoing back on path");
                    break;
                } else if (choice == 1) {
                    if (location.getLocationStages().get(location.getStageCompleted()) instanceof LocationStageQuestGiver
                            && location.getLocationStages().get(location.getStageCompleted() + 1) != null
                            && location.getLocationStages().get(location.getStageCompleted() + 1).isStageDiscovered()) {

                        exploreLocationStage(hero, location, location.getStageCompleted() + 1);
                    } else {
                        exploreLocationStage(hero, location, location.getStageCompleted());
                    }
                } else if (choice == 2) {
                    heroMenuService.heroCharacterMenu(hero);
                } else {
                    try {
                        if (!location.getLocationStages().get(choice - index).isStageDiscovered()) {
                            PrintUtil.printEnterValidInput();
                            continue;
                        }
                    } catch (NullPointerException e) {
                        PrintUtil.printEnterValidInput();
                        continue;
                    }

                    exploreLocationStage(hero, location, choice - index);
                }
            } catch (IndexOutOfBoundsException e) {
                PrintUtil.printEnterValidInput();
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
     * @param locationStageOrder order in location
     */
    public void exploreLocationStage(Hero hero, Location location, int locationStageOrder) {
        LocationStage locationStage = location.getLocationStages().get(locationStageOrder);

        // Conditions for check if Hero can explore current Stage
        if (locationStage instanceof LocationStageQuestGiver) {
            location.getLocationStages().get(locationStageOrder + 1).setStageDiscovered(true);
        }

        if (!locationStage.isStageDiscovered()) {
            locationStage.setStageDiscovered(true);
        }

        if (!locationStage.canHeroEnterStage(hero)) {
            System.out.println("\tYou don't have needed keys to enter location! You need: ");
            for (int itemId : locationStage.getItemsIdNeededToEnterStage()) {
                System.out.println("\t" + ItemDB.returnItemFromDB(itemId).getName());
            }

            return;
        }

//        if (locationStage.isStageCompleted()) {
            if (!(locationStage instanceof CanEnterStageAfterComplete) && locationStage.isStageCompleted()) {
                System.out.println("\tNothing new to do in " + locationStage.getStageName());
                return;
            }
//        }

        // LocationStage header
        PrintUtil.printLongDivider();
        System.out.println("\t\t\t" + locationStage.getStageName());
        PrintUtil.printLongDivider();

        // Explore location stage
        boolean isStageCompleted = locationStage.exploreStage(hero, location);

        // check if stage is successfully cleared
        if (isStageCompleted && !locationStage.isStageCompleted()) {
            location.incrementStageCompleted();
            locationStage.setStageCompleted(true);
            hero.restoreHealthAndManaAfterTurn();

            if (locationStage instanceof RemoveLocationStageProgress removeStageProgress) {
                removeStageProgress.removeProgressAfterCompletedStage();
            }

            if (location.getLocationStages().get(locationStageOrder + 1) != null) {
                location.getLocationStages().get(locationStageOrder + 1).setStageDiscovered(true);
            }
        } else {
            return;
        }

        // Completing all location stages
        if (location.getStageCompleted() == location.getStageTotal()) {
            location.setCleared(true);
            location.setCanLocationBeExplored(false);
            hero.checkIfQuestObjectivesAndQuestIsCompleted();
        }
    }
}


