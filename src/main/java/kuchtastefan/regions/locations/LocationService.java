package kuchtastefan.regions.locations;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.regions.events.CombatEvent;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

public class LocationService {

    public void locationMenu(Hero hero, Location location) {
        PrintUtil.printLongDivider();
        System.out.println("\t\t" + location.getLocationName() + "\t\t Location level: " + location.getLocationLevel());
        PrintUtil.printLongDivider();

        System.out.println("\tWhat do you want to do?");
        System.out.println("\t0. Go back on the path");
        System.out.println("\t1. Explore location");

        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
            }
            case 1 -> {
                if (location.isCanLocationBeExplored()) {
                    exploreLocation(hero, location);
                } else {
                    System.out.println("Location can not be explored yet");
                }
            }
            default -> System.out.println("Enter valid input");
        }
    }

    public void exploreLocation(Hero hero, Location location) {
        while (location.stageCompleted < location.stageTotal) {
            PrintUtil.printLongDivider();
            System.out.println("\t\t\t" + location.locationName + "\t\t\tStages completed " + location.stageCompleted + " / " + location.stageTotal);
            PrintUtil.printLongDivider();
            if (new CombatEvent(location.getLocationLevel(), location.enemyList(), location.getLocationType()).eventOccurs(hero)) {
                location.stageCompleted++;
            } else {
                return;
            }

            if (location.stageCompleted == location.stageTotal) {
                location.setCleared(true);
                location.rewardAfterCompletedAllStages(hero);
            }
        }
    }
}


