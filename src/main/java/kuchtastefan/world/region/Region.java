package kuchtastefan.world.region;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.hint.HintDB;
import kuchtastefan.hint.HintName;
import kuchtastefan.service.EventService;
import kuchtastefan.service.HeroMenuService;
import kuchtastefan.service.LocationService;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.printUtil.CharacterPrint;
import kuchtastefan.utility.printUtil.PrintUtil;
import kuchtastefan.utility.printUtil.SpellAndActionPrint;
import kuchtastefan.world.Biome;
import kuchtastefan.world.location.Location;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Region {
    private final int regionId;
    protected String regionName;
    protected String regionDescription;
    protected int minimumRegionLevel;
    protected int maximumRegionLevel;
    protected Biome biome;
    private int[] locationsId;
    protected List<Location> allLocations;


    public Region(int regionId, String regionName, String regionDescription, int minimumRegionLevel, int maximumRegionLevel) {
        this.regionId = regionId;
        this.regionName = regionName;
        this.regionDescription = regionDescription;
        this.minimumRegionLevel = minimumRegionLevel;
        this.maximumRegionLevel = maximumRegionLevel;
    }

    public void adventuringAcrossTheRegion(HeroMenuService heroMenuService, Hero hero) {
        EventService eventService = new EventService(this.getAllLocations());
        HintDB.printHint(HintName.REGION_HINT);

        while (true) {
            // Printing region information and options
            System.out.println();
            CharacterPrint.printHeaderWithStatsBar(hero);
            SpellAndActionPrint.printBuffTable(hero);
            PrintUtil.printExtraLongDivider();

            System.out.println("\n\t" + this.getRegionName()
                    + " \tRegion level: " + this.getMinimumRegionLevel() + " - " + this.getMaximumRegionLevel()
                    + " \tDiscovered locations: " + hero.getDiscoveredLocationList().size() + " / " + this.getAllLocations().size() + "\n");

            PrintUtil.printMenuOptions("Go back to the city", "Travel across region " + this.getRegionName(), "Hero menu");

            // Printing discovered locations
            int index = 3;
            List<Location> locations = new ArrayList<>();
            for (Map.Entry<Integer, Location> location : hero.getDiscoveredLocationList().entrySet()) {
                String s = location.getValue().getLocationName() + " " + location.getValue().printLocationServices();

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
                case 1 -> eventService.randomRegionEventGenerate(hero, this.getBiome());
                case 2 -> heroMenuService.heroCharacterMenu(hero); // Open hero menu
                default -> {
                    try {
                        new LocationService(locations.get(choice - 3)).locationMenu(hero, heroMenuService);
                    } catch (IndexOutOfBoundsException e) {
                        PrintUtil.printEnterValidInput();
                    }
                }
            }
        }
    }


    public String getRegionName() {
        return ConsoleColor.YELLOW + regionName + ConsoleColor.RESET;
    }
}
