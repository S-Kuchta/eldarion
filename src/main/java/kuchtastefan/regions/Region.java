package kuchtastefan.regions;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.characters.hero.HeroCharacterInfoService;
import kuchtastefan.regions.events.EventService;
import kuchtastefan.regions.locations.Location;
import kuchtastefan.regions.locations.LocationService;
import kuchtastefan.regions.locations.LocationType;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public abstract class Region {
    protected String regionName;
    protected String regionDescription;
    protected List<Location> allLocations;
    protected List<Location> discoveredLocations;
    protected List<Location> clearedLocations;
    protected int minimumRegionLevel;
    protected int maximumRegionLevel;
    protected Hero hero;
    protected final EventService eventService;


    public Region(String regionName, String regionDescription, Hero hero, int minimumRegionLevel, int maximumRegionLevel) {
        this.regionName = regionName;
        this.regionDescription = regionDescription;
        this.allLocations = initializeLocationForRegion();
        this.discoveredLocations = new ArrayList<>();
        this.clearedLocations = new ArrayList<>();
        this.minimumRegionLevel = minimumRegionLevel;
        this.maximumRegionLevel = maximumRegionLevel;
        this.hero = hero;
        this.eventService = new EventService(this.allLocations);
    }

    public void adventuringAcrossTheRegion(HeroCharacterInfoService heroCharacterInfoService) {
        while (true) {
            System.out.println();
            PrintUtil.printHeaderWithStatsBar(getHero());
            PrintUtil.printRegionBuffs(getHero());
            PrintUtil.printExtraLongDivider();
            PrintUtil.printLongDivider();
            System.out.println("\t\t" + this.regionName
                    + " \tRegion level: " + this.minimumRegionLevel + " - " + this.maximumRegionLevel
                    + " \tDiscovered locations: "
                    + this.hero.getDiscoveredLocationList().size() + " / "
                    + this.allLocations.size());
            PrintUtil.printLongDivider();

            PrintUtil.printIndexAndText("0", "Go back to the city");
            System.out.println();
            PrintUtil.printIndexAndText("1", "Travel across region " + getRegionName());
            System.out.println();
            PrintUtil.printIndexAndText("2", "Hero menu");
            System.out.println();


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

            int choice = InputUtil.intScanner();
            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> this.eventService.randomRegionEventGenerate(this.hero, LocationType.FOREST,
                        this.minimumRegionLevel, this.maximumRegionLevel);
                case 2 -> heroCharacterInfoService.heroCharacterMenu(this.hero);
                default -> {
                    try {
                        new LocationService().locationMenu(this.hero, locations.get(choice - 3), heroCharacterInfoService);
                    } catch (IndexOutOfBoundsException e) {
                        PrintUtil.printEnterValidInput();
                    }
                }
            }
        }
    }

    protected abstract List<Location> initializeLocationForRegion();

}
