package kuchtastefan.regions.locations;

import kuchtastefan.characters.enemy.Enemy;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Location {

    protected final String locationName;
    protected final int locationLevel;
    protected int stageTotal;
    protected int stageCompleted;
    protected boolean cleared;
    protected List<Enemy> enemyList;
    protected final LocationType locationType;

    public Location(String locationName, int locationLevel, int stageTotal, LocationType locationType) {
        this.locationName = locationName;
        this.locationLevel = locationLevel;
        this.stageTotal = stageTotal;
        this.stageCompleted = 0;
        this.cleared = false;
        this.enemyList = new ArrayList<>();
        this.locationType = locationType;
    }

    public void locationMenu() {
        PrintUtil.printLongDivider();
        System.out.println("\t\t" + this.locationName + ", Location level: " + this.locationLevel);
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

    public boolean isCleared() {
        return cleared;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return locationLevel == location.locationLevel && cleared == location.cleared && stageTotal == location.stageTotal && Objects.equals(locationName, location.locationName) && locationType == location.locationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationName, locationLevel, stageTotal, locationType, cleared);
    }
}
