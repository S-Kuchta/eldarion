package kuchtastefan.regions;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.characters.hero.HeroCharacterService;
import kuchtastefan.items.ItemsLists;
import kuchtastefan.regions.locations.Location;
import kuchtastefan.regions.locations.LocationService;
import kuchtastefan.regions.locations.LocationType;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

import java.util.ArrayList;
import java.util.List;

public class ForestRegionService extends Region {

    public ForestRegionService(String regionName, String regionDescription, ItemsLists itemsLists, Hero hero, int minimumRegionLevel, int maximumRegionLevel) {
        super(regionName, regionDescription, itemsLists, hero, minimumRegionLevel, maximumRegionLevel);
    }

    @Override
    public void adventuringAcrossTheRegion(HeroCharacterService heroCharacterService) {

        while (true) {
            System.out.println();
            PrintUtil.printLongDivider();
            System.out.println("\t\t" + getRegionName()
                    + " \tRegion level: 1 - 5"
                    + " \tDiscovered locations: "
                    + getDiscoveredLocations().size() + " / "
                    + allLocations.size());
            PrintUtil.printLongDivider();

            System.out.println("\t0. Go back to the city");
            System.out.println("\t1. Travel across region " + getRegionName());
            System.out.println("\t2. Hero menu");

            int index = 3;
            for (Location discoveredLocation : this.discoveredLocations) {
                System.out.println("\t" + index + ". " + discoveredLocation.getLocationName() + " (recommended level: " + discoveredLocation.getLocationLevel() + ")");
                index++;
            }

            int choice = InputUtil.intScanner();
            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> super.eventService.randomRegionEventGenerate(super.hero, LocationType.FOREST);
                case 2 -> heroCharacterService.heroCharacterMenu(this.hero);
                default -> {
                    try {
                        new LocationService().locationMenu(hero, this.discoveredLocations.get(choice - 3));
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("\tEnter valid input");
                    }
                }
            }
        }
    }

    @Override
    protected List<Location> initializeLocationForRegion() {
        List<Location> locationList = new ArrayList<>();
        locationList.add(new Location("Mystic cave", 2, 10, LocationType.CAVE, true));
        locationList.add(new Location("Old mine", 2, 10, LocationType.MINE, true));
        locationList.add(new Location("Castle ruins", 3, 10, LocationType.CASTLE, true));
        locationList.add(new Location("Cemetery", 4, 10, LocationType.CEMETERY, true));
        locationList.add(new Location("Tower of Damned", 5, 10, LocationType.TOWER, true));
        return locationList;
    }

}
