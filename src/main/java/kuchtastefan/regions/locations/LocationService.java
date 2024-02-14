package kuchtastefan.regions.locations;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.characters.hero.HeroCharacterInfoService;
import kuchtastefan.items.ItemsLists;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

import java.util.Map;

public class LocationService {

    public void locationMenu(Hero hero, Location location, HeroCharacterInfoService heroCharacterInfoService) {


        while (true) {
            PrintUtil.printLongDivider();
            System.out.println("\t" + location.getLocationName() + "\tLocation level: "
                    + location.getLocationLevel() + " "
                    + "\tStages completed " + location.stageCompleted + " / " + location.stageTotal);
            PrintUtil.printLongDivider();

            System.out.println("\tWhat do you want to do?");
            PrintUtil.printIndexAndText("0", "Go back on the path");
            System.out.println();
            PrintUtil.printIndexAndText("1", "Explore location");
            System.out.println();
            PrintUtil.printIndexAndText("2", "Hero Menu");
            System.out.println();

            int index = 3;
            for (Map.Entry<Integer, LocationStage> locationStage : location.locationStages.entrySet()) {
                if (locationStage.getValue().isStageDiscovered()) {
                    String completed = locationStage.getValue().isStageCompleted() ? " - COMPLETED -" : "";
                    PrintUtil.printIndexAndText(String.valueOf(index + locationStage.getKey()),
                            locationStage.getValue().getStageName() + " " + completed);

                    System.out.println();
                }
            }

            try {
                int choice = InputUtil.intScanner();
                if (choice == 0) {
                    System.out.println("\tGoing back on path");
                    break;
                } else if (choice == 1) {
                    if (location.locationStages.get(location.stageCompleted) instanceof LocationStageQuestGiver
                            && location.locationStages.get(location.stageCompleted + 1) != null
                            && location.locationStages.get(location.stageCompleted + 1).isStageDiscovered()) {

                        exploreLocation(hero, location, location.stageCompleted + 1);
                    } else {
                        exploreLocation(hero, location, location.stageCompleted);
                    }
                } else if (choice == 2) {
                    heroCharacterInfoService.heroCharacterMenu(hero);
                } else {
                    if (!location.getLocationStages().get(choice - index).isStageDiscovered()) {
                        PrintUtil.printEnterValidInput();
                        continue;
                    }

                    exploreLocation(hero, location, choice - index);
                }
            } catch (IndexOutOfBoundsException e) {
                PrintUtil.printEnterValidInput();
            }
        }
    }

    public void exploreLocation(Hero hero, Location location, int locationStageOrder) {
        LocationStage locationStage = location.locationStages.get(locationStageOrder);

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
                System.out.println("\t" + ItemsLists.getItemMapIdItem().get(i).getName());
            }
            return;
        }

        if (locationStage.isStageCompleted()) {
            System.out.println("\t" + locationStage.getStageName() + " is cleared!");
            return;
        }

        PrintUtil.printLongDivider();
        System.out.println("\t\t\t" + locationStage.getStageName());
        PrintUtil.printLongDivider();

        boolean isStageCompleted = locationStage.exploreStage(hero, location);

        if (isStageCompleted) {
            location.stageCompleted++;
            locationStage.afterSuccessfullyCompletedStage();
//            locationStage.setStageCompleted(true);
//            locationStage.completeStage();
            if (location.getLocationStages().get(locationStageOrder + 1) != null) {
                location.getLocationStages().get(locationStageOrder + 1).setStageDiscovered(true);
            }
        } else {
            return;
        }

        if (location.stageCompleted == location.stageTotal) {
            location.setCleared(true);
            location.rewardAfterCompletedAllStages(hero);
            location.setCanLocationBeExplored(false);
            hero.checkIfQuestObjectivesAndQuestIsCompleted();
        }
    }
}


