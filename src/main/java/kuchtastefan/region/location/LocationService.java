package kuchtastefan.region.location;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.hero.HeroMenuService;
import kuchtastefan.hint.HintName;
import kuchtastefan.hint.HintUtil;
import kuchtastefan.item.ItemDB;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

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
        HintUtil.printHint(HintName.LOCATION_HINT);

        while (true) {
            PrintUtil.printLongDivider();
            System.out.println(ConsoleColor.YELLOW + "\t" + location.getLocationName() + ConsoleColor.RESET
                    + "\tLocation level: " + location.getLocationLevel() + " "
                    + "\tStages completed " + location.stageCompleted + " / " + location.stageTotal);
            PrintUtil.printLongDivider();

            // Print stage menu
            System.out.println("\tWhat do you want to do?");
            PrintUtil.printIndexAndText("0", "Go back on the path");
            System.out.println();
            PrintUtil.printIndexAndText("1", "Explore location");
            System.out.println();
            PrintUtil.printIndexAndText("2", "Hero Menu");
            System.out.println();

            // Print discovered location Stages
            int index = 3;
            System.out.println(ConsoleColor.YELLOW_UNDERLINED + "\t\t\t\t\t\tLocation Stages\t\t\t\t\t\t" + ConsoleColor.RESET);
            for (Map.Entry<Integer, LocationStage> locationStage : location.locationStages.entrySet()) {
                if (locationStage.getValue().isStageDiscovered()) {
                    String completed = locationStage.getValue().isStageCompleted() ? " - COMPLETED -" : "";
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
                    if (location.locationStages.get(location.stageCompleted) instanceof LocationStageQuestGiver
                            && location.locationStages.get(location.stageCompleted + 1) != null
                            && location.locationStages.get(location.stageCompleted + 1).isStageDiscovered()) {

                        exploreLocationStage(hero, location, location.stageCompleted + 1);
                    } else {
                        exploreLocationStage(hero, location, location.stageCompleted);
                    }
                } else if (choice == 2) {
                    heroMenuService.heroCharacterMenu(hero);
                } else {
                    if (!location.getLocationStages().get(choice - index).isStageDiscovered()) {
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
        LocationStage locationStage = location.locationStages.get(locationStageOrder);

        // Conditions for check if Hero can explore current Stage
        if (locationStage instanceof LocationStageQuestGiver) {
            location.getLocationStages().get(locationStageOrder + 1).setStageDiscovered(true);
        }

        if (location.isCleared()) {
            System.out.println("\tLocation Cleared!");
            return;
        }

        if (!locationStage.isStageDiscovered()) {
            locationStage.setStageDiscovered(true);
        }

        if (!locationStage.canHeroEnterStage(hero)) {
            System.out.println("\tYou don't have needed keys to enter location! You need: ");
            for (Integer i : locationStage.getItemsIdNeededToEnterStage()) {
                System.out.println("\t" + ItemDB.returnItemFromDB(i).getName());
            }
            return;
        }

        if (locationStage.isStageCompleted()) {
            System.out.println("\t" + locationStage.getStageName() + " is cleared!");
            return;
        }
        // End of conditions for explore current Stage

        // LocationStage header
        PrintUtil.printLongDivider();
        System.out.println("\t\t\t" + locationStage.getStageName());
        PrintUtil.printLongDivider();

        // Explore location stage
        boolean isStageCompleted = locationStage.exploreStage(hero, location);

        // check if stage is successfully cleared
        if (isStageCompleted) {
            location.stageCompleted++;
            locationStage.setStageCompleted(true);

            if (locationStage instanceof RemoveLocationStageProgress) {
                ((RemoveLocationStageProgress) locationStage).removeProgressAfterCompletedStage();
            }

            if (location.getLocationStages().get(locationStageOrder + 1) != null) {
                location.getLocationStages().get(locationStageOrder + 1).setStageDiscovered(true);
            }
        } else {
            return;
        }

        // Reward for completing all location stages
        if (location.stageCompleted == location.stageTotal) {
            location.setCleared(true);
            location.rewardAfterCompletedAllStages(hero);
            location.setCanLocationBeExplored(false);
            hero.checkIfQuestObjectivesAndQuestIsCompleted();
        }
    }
}


