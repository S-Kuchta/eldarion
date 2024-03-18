package kuchtastefan.region;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.region.location.Location;
import kuchtastefan.region.location.LocationDB;

import java.util.ArrayList;
import java.util.List;

public class ForestRegion extends Region {

    public ForestRegion(String regionName, String regionDescription, Hero hero, int minimumRegionLevel, int maximumRegionLevel) {
        super(regionName, regionDescription, hero, minimumRegionLevel, maximumRegionLevel);
    }

    @Override
    protected List<Location> initializeLocationForRegion() {
        List<Location> locationList = new ArrayList<>();
        locationList.add(LocationDB.returnLocation(0));
        locationList.add(LocationDB.returnLocation(1));
        locationList.add(LocationDB.returnLocation(2));
//        locationList.add(new Location(0, "Enchanted Mine", 2, 5, LocationType.MINE, true));
//        locationList.add(new Location(0, "Abyssal Hollows", 2, 10, LocationType.CAVE, true));
//        locationList.add(new Location(0, "Ruins of Eldoria", 3, 10, LocationType.CASTLE, true));
//        locationList.add(new Location(0, "Necropolis Valley", 4, 10, LocationType.CEMETERY, true));
//        locationList.add(new Location(0, "Tower of Damned", 5, 10, LocationType.TOWER, true));
//        LocationsList.getLocationList().addAll(locationList);
        return locationList;
    }

}
