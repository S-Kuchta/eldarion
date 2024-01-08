package kuchtastefan.regions;

import kuchtastefan.domain.Hero;
import kuchtastefan.item.ItemsLists;
import kuchtastefan.regions.events.EventService;

public abstract class Region {
    protected String regionName;
    protected String regionDescription;
    protected int allLocations;
    protected int discoveredLocations;
    protected int clearedLocations;
    protected ItemsLists itemsLists;
    protected Hero hero;
    protected final EventService eventService;


    public Region(String regionName, String regionDescription, ItemsLists itemsLists, Hero hero) {
        this.regionName = regionName;
        this.regionDescription = regionDescription;
        this.allLocations = 0;
        this.discoveredLocations = 0;
        this.clearedLocations = 0;
        this.hero = hero;
        this.itemsLists = itemsLists;
        this.eventService = new EventService(this.itemsLists);
    }

    public abstract void adventuringAcrossTheRegion();

    public String getRegionName() {
        return regionName;
    }
}
