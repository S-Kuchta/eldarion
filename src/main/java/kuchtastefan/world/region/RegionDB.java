package kuchtastefan.world.region;

import kuchtastefan.world.location.LocationDB;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegionDB {
    @Getter
    private static final Map<Integer, Region> REGION_DB = new HashMap<>();

    public static void addRegionToDB(Region region) {
        if (region.getAllLocations() == null) {
            region.setAllLocations(new ArrayList<>());
        }

        for (int locationId : region.getLocationsId()) {
            region.allLocations.add(LocationDB.returnLocation(locationId));
        }

        REGION_DB.put(region.getRegionId(), region);
    }

    public static Region returnRegion(int regionId) {
        return REGION_DB.get(regionId);
    }

    public static String returnRegionName(int regionId) {
        return REGION_DB.get(regionId).getRegionName();
    }
}
