package kuchtastefan.regions;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.regions.locations.Location;
import kuchtastefan.regions.locations.LocationsList;

import java.util.ArrayList;
import java.util.List;

public class ForestRegionService extends Region {

    public ForestRegionService(String regionName, String regionDescription, Hero hero, int minimumRegionLevel, int maximumRegionLevel) {
        super(regionName, regionDescription, hero, minimumRegionLevel, maximumRegionLevel);
    }
    
    @Override
    protected List<Location> initializeLocationForRegion() {
        List<Location> locationList = new ArrayList<>();
        locationList.add(LocationsList.getLocationList().get(0));
        locationList.add(LocationsList.getLocationList().get(1));
//        locationList.add(new Location(0, "Enchanted Mine", 2, 5, LocationType.MINE, true));
//        locationList.add(new Location(0, "Abyssal Hollows", 2, 10, LocationType.CAVE, true));
//        locationList.add(new Location(0, "Ruins of Eldoria", 3, 10, LocationType.CASTLE, true));
//        locationList.add(new Location(0, "Necropolis Valley", 4, 10, LocationType.CEMETERY, true));
//        locationList.add(new Location(0, "Tower of Damned", 5, 10, LocationType.TOWER, true));
//        LocationsList.getLocationList().addAll(locationList);
        return locationList;
    }

}
