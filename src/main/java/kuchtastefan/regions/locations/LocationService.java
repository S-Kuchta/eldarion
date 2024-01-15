package kuchtastefan.regions.locations;

import kuchtastefan.characters.enemy.Enemy;
import kuchtastefan.characters.enemy.EnemyList;
import kuchtastefan.characters.hero.Hero;
import kuchtastefan.regions.events.CombatEvent;
import kuchtastefan.regions.events.FindItemEvent;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import kuchtastefan.utility.RandomNumberGenerator;

import java.util.List;

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

        for (int i = location.stageCompleted; i < location.stageTotal; i++) {
            int randomNum = RandomNumberGenerator.getRandomNumber(0,1);
            if (randomNum == 0) {
                List<Enemy> suitableEnemies = EnemyList.returnStrongerEnemyListByLocationTypeAndLevel(location.getLocationType(), location.getLocationLevel(), null, 1.4);
                new CombatEvent(location.getLocationLevel(), suitableEnemies, location.getLocationType()).eventOccurs(hero);
            } else if (randomNum == 1) {
                new FindItemEvent(location.getLocationLevel()).eventOccurs(hero);
            }
        }
    }
}
