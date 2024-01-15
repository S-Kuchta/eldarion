package kuchtastefan.regions;

import kuchtastefan.characters.enemy.EnemyList;
import kuchtastefan.characters.hero.Hero;
import kuchtastefan.characters.hero.HeroCharacterService;
import kuchtastefan.items.ItemsLists;
import kuchtastefan.regions.events.EventService;
import kuchtastefan.regions.locations.Location;

import java.util.ArrayList;
import java.util.List;

public abstract class Region {
    protected String regionName;
    protected String regionDescription;
    protected List<Location> allLocations;
    protected List<Location> discoveredLocations;
    protected List<Location> clearedLocations;
    protected int minimumRegionLevel;
    protected int maximumRegionLevel;
    protected ItemsLists itemsLists;
    protected Hero hero;
    protected final EventService eventService;


    public Region(String regionName, String regionDescription, ItemsLists itemsLists, Hero hero, int minimumRegionLevel, int maximumRegionLevel) {
        this.regionName = regionName;
        this.regionDescription = regionDescription;
        this.allLocations = initializeLocationForRegion();
        this.discoveredLocations = new ArrayList<>();
        this.clearedLocations = new ArrayList<>();
        this.minimumRegionLevel = minimumRegionLevel;
        this.maximumRegionLevel = maximumRegionLevel;
        this.hero = hero;
        this.itemsLists = itemsLists;
        this.eventService = new EventService(this.itemsLists, this.allLocations, this.discoveredLocations);
    }

    public abstract void adventuringAcrossTheRegion(HeroCharacterService heroCharacterService);

    protected abstract List<Location> initializeLocationForRegion();

    public String getRegionName() {
        return regionName;
    }

    public List<Location> getDiscoveredLocations() {
        return discoveredLocations;
    }

    public List<Location> getAllLocations() {
        return allLocations;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }
}
