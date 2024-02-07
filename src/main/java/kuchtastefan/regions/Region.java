package kuchtastefan.regions;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.characters.hero.HeroCharacterInfoService;
import kuchtastefan.regions.events.EventService;
import kuchtastefan.regions.locations.Location;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
        this.eventService = new EventService(this.allLocations, this.discoveredLocations);
    }

    public abstract void adventuringAcrossTheRegion(HeroCharacterInfoService heroCharacterInfoService);

    protected abstract List<Location> initializeLocationForRegion();

}
