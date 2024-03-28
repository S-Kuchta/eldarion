package kuchtastefan.world.region;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.hero.HeroMenuService;
import kuchtastefan.hint.HintName;
import kuchtastefan.hint.HintDB;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import kuchtastefan.world.event.EventService;
import kuchtastefan.world.location.Location;
import kuchtastefan.world.location.LocationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RegionService {

    public void adventuringAcrossTheRegion(HeroMenuService heroMenuService, Region region, Hero hero) {
        EventService eventService = new EventService(region.allLocations);
        HintDB.printHint(HintName.REGION_HINT);

        while (true) {
            // Printing region information and options
            System.out.println();
            PrintUtil.printHeaderWithStatsBar(hero);
            PrintUtil.printRegionBuffs(hero);
            PrintUtil.printExtraLongDivider();
            PrintUtil.printLongDivider();
            System.out.println("\t\t" + region.regionName
                    + " \tRegion level: " + region.minimumRegionLevel + " - " + region.maximumRegionLevel
                    + " \tDiscovered locations: "
                    + hero.getDiscoveredLocationList().size() + " / "
                    + region.allLocations.size());
            PrintUtil.printLongDivider();

            PrintUtil.printIndexAndText("0", "Go back to the city");
            System.out.println();
            PrintUtil.printIndexAndText("1", "Travel across region " + region.regionName);
            System.out.println();
            PrintUtil.printIndexAndText("2", "Hero menu");
            System.out.println();

            // Printing discovered locations
            int index = 3;
            List<Location> locations = new ArrayList<>();
            for (Map.Entry<Integer, Location> location : hero.getDiscoveredLocationList().entrySet()) {
                String s = location.getValue().getLocationName()
                        + " (recommended level: "
                        + location.getValue().getLocationLevel() + ")";

                PrintUtil.printIndexAndText(String.valueOf(index), s);
                locations.add(location.getValue());
                System.out.println();
                index++;
            }

            // Getting user choice
            int choice = InputUtil.intScanner();
            switch (choice) {
                case 0 -> {
                    return; // Go back to the city
                }
                // Generate a random event while traveling across the region
                case 1 -> eventService.randomRegionEventGenerate(hero, region.biome);
                case 2 -> heroMenuService.heroCharacterMenu(hero); // Open hero menu
                default -> {
                    try {
                        // Open the menu for the selected location
                        new LocationService().locationMenu(hero, locations.get(choice - 3), heroMenuService);
                    } catch (IndexOutOfBoundsException e) {
                        PrintUtil.printEnterValidInput();
                    }
                }
            }
        }
    }
}
