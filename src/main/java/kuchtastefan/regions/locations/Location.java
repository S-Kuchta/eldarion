package kuchtastefan.regions.locations;

import kuchtastefan.characters.enemy.Enemy;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

import java.util.ArrayList;
import java.util.List;

public class Location {

    protected final String locationName;
    protected final int locationLevel;
    protected int stageTotal;
    protected int stageComplete;
    protected boolean cleared;
    protected List<Enemy> enemies;
    protected final LocationType locationType;

    public Location(String locationName, int locationLevel, int stageTotal, LocationType locationType) {
        this.locationName = locationName;
        this.locationLevel = locationLevel;
        this.stageTotal = stageTotal;
        this.stageComplete = 0;
        this.cleared = false;
        this.enemies = new ArrayList<>();
        this.locationType = locationType;
    }

    public void locationMenu() {
        PrintUtil.printLongDivider();
        System.out.println("\t\t" + this.locationName + ", location level: " + this.locationLevel);
        PrintUtil.printLongDivider();

        System.out.println("\tWhat do you want to do?");
        System.out.println("\t0. Go back on the path");
        System.out.println("\t1. Explore location");

        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
            }
            case 1 -> System.out.println("Exploring location");
            default -> System.out.println("Enter valid input");
        }
    }

    public String getLocationName() {
        return locationName;
    }

    public int getLocationLevel() {
        return locationLevel;
    }
}
