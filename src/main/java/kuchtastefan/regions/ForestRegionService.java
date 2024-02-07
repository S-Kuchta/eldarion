package kuchtastefan.regions;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.characters.hero.HeroCharacterInfoService;
import kuchtastefan.regions.locations.Location;
import kuchtastefan.regions.locations.LocationService;
import kuchtastefan.regions.locations.LocationType;
import kuchtastefan.regions.locations.LocationsList;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ForestRegionService extends Region {

    public ForestRegionService(String regionName, String regionDescription, Hero hero, int minimumRegionLevel, int maximumRegionLevel) {
        super(regionName, regionDescription, hero, minimumRegionLevel, maximumRegionLevel);
    }

    @Override
    public void adventuringAcrossTheRegion(HeroCharacterInfoService heroCharacterInfoService) {

        while (true) {
            System.out.println();
            PrintUtil.printHeaderWithStatsBar(getHero());
            PrintUtil.printRegionBuffs(getHero());
            PrintUtil.printExtraLongDivider();
            PrintUtil.printLongDivider();
            System.out.println("\t\t" + getRegionName()
                    + " \tRegion level: " + this.minimumRegionLevel + " - " + this.maximumRegionLevel
                    + " \tDiscovered locations: "
                    + this.hero.getDiscoveredLocationList().size() + " / "
                    + allLocations.size());
            PrintUtil.printLongDivider();

            System.out.println("\t0. Go back to the city");
            System.out.println("\t1. Travel across region " + getRegionName());
            System.out.println("\t2. Hero menu");

            int index = 3;
            List<Location> locations = new ArrayList<>();
            for (Map.Entry<Integer, Location> location : this.hero.getDiscoveredLocationList().entrySet()) {
                String s = location.getValue().getLocationName()
                        + " (recommended level: "
                        + location.getValue().getLocationLevel() + ")";

                PrintUtil.printIndexAndText(String.valueOf(index), s);
                locations.add(location.getValue());
                System.out.println();
                index++;

            }
//            for (Location discoveredLocation : this.hero.getDiscoveredLocationList()) {
//                System.out.println("\t" + index + ". " + discoveredLocation.getLocationName() + " (recommended level: " + discoveredLocation.getLocationLevel() + ")");
//                index++;
//            }

            int choice = InputUtil.intScanner();
            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> super.eventService.randomRegionEventGenerate(super.hero, LocationType.FOREST,
                        this.minimumRegionLevel, this.maximumRegionLevel);
                case 2 -> heroCharacterInfoService.heroCharacterMenu(this.hero);
                default -> {
                    try {
                        new LocationService().locationMenu(this.hero, /*this.hero.getDiscoveredLocationList().get(choice - 3)*/locations.get(choice - 3));
                    } catch (IndexOutOfBoundsException e) {
                        PrintUtil.printEnterValidInput();
                    }
                }
            }
        }
    }

    @Override
    protected List<Location> initializeLocationForRegion() {
        List<Location> locationList = new ArrayList<>();
        locationList.add(LocationsList.getLocationList().get(0));
//        locationList.add(new Location(0, "Enchanted Mine", 2, 5, LocationType.MINE, true));
//        locationList.add(new Location(0, "Abyssal Hollows", 2, 10, LocationType.CAVE, true));
//        locationList.add(new Location(0, "Ruins of Eldoria", 3, 10, LocationType.CASTLE, true));
//        locationList.add(new Location(0, "Necropolis Valley", 4, 10, LocationType.CEMETERY, true));
//        locationList.add(new Location(0, "Tower of Damned", 5, 10, LocationType.TOWER, true));
//        LocationsList.getLocationList().addAll(locationList);
        return locationList;
    }

}
