package kuchtastefan.world.region;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.hint.HintDB;
import kuchtastefan.hint.HintName;
import kuchtastefan.service.EventService;
import kuchtastefan.service.HeroMenuService;
import kuchtastefan.service.LocationService;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.annotationStrategy.Exclude;
import kuchtastefan.utility.printUtil.CharacterPrint;
import kuchtastefan.utility.printUtil.PrintUtil;
import kuchtastefan.utility.printUtil.SpellAndActionPrint;
import kuchtastefan.world.Biome;
import kuchtastefan.world.location.Location;
import kuchtastefan.world.location.LocationDB;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Region {
    private final int regionId;
    protected String regionName;
    protected int minimumRegionLevel;
    protected int maximumRegionLevel;
    protected Biome biome;
    private int[] locationsId;


    public Region(int regionId, String regionName, int minimumRegionLevel, int maximumRegionLevel) {
        this.regionId = regionId;
        this.regionName = regionName;
        this.minimumRegionLevel = minimumRegionLevel;
        this.maximumRegionLevel = maximumRegionLevel;
    }

    public void adventuringAcrossTheRegion(HeroMenuService heroMenuService, Hero hero) {
        HintDB.printHint(HintName.REGION_HINT);
        int index = 3;
        boolean flag = true;

        while (flag) {
            printRegionHeader(hero);
            printDiscoveredLocations(index);
            flag = handleUserInput(hero, heroMenuService, index);
        }
    }

    private boolean handleUserInput(Hero hero, HeroMenuService heroMenuService, int index) {
        EventService eventService = new EventService(LocationDB.getLocationListByIds(this.locationsId));
        List<Location> discoveredLocations = LocationDB.getClearedAndDiscoveredLocationsByIds(this.locationsId);

        while (true) {
            int choice = InputUtil.intScanner();
            if (choice > discoveredLocations.size() + index || choice < 0) {
                continue;
            }

            if (choice == 0) {
                return false;
            } else if (choice == 1) {
                eventService.randomRegionEventGenerate(hero, this.getBiome());
            } else if (choice == 2) {
                heroMenuService.heroCharacterMenu(hero);
            } else {
                new LocationService(discoveredLocations.get(choice - index)).locationMenu(hero, heroMenuService);
            }

            return true;
        }
    }

    private void printRegionHeader(Hero hero) {
        System.out.println();
        CharacterPrint.printHeaderWithStatsBar(hero);
        SpellAndActionPrint.printBuffTable(hero);
        PrintUtil.printExtraLongDivider();

        System.out.println("\n\t" + this.getRegionName()
                           + " \tRegion level: " + this.getMinimumRegionLevel() + " - " + this.getMaximumRegionLevel()
                           + " \tDiscovered locations: " + LocationDB.getClearedAndDiscoveredLocationsByIds(this.locationsId).size()
                           + " / " + this.locationsId.length + "\n");

        PrintUtil.printMenuOptions("Go back to the city", "Travel across region " + this.getRegionName(), "Hero menu");
    }

    private void printDiscoveredLocations(int index) {
        for (Location location : LocationDB.getClearedAndDiscoveredLocationsByIds(this.locationsId)) {
            String s = location.getLocationName() + " " + location.printLocationServices();
            PrintUtil.printIndexAndText(String.valueOf(index), s);
            System.out.println();
            index++;
        }
    }

    public String getRegionName() {
        return ConsoleColor.YELLOW + regionName + ConsoleColor.RESET;
    }
}
