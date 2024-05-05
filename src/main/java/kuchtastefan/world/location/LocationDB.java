package kuchtastefan.world.location;

import java.util.HashMap;
import java.util.Map;

public class LocationDB {

    private static final Map<Integer, Location> LOCATION_DB = new HashMap<>();

    public static Location returnLocation(int locationId) {
        return LOCATION_DB.get(locationId);
    }

    public static void addLocationToDB(Location location) {
        LOCATION_DB.put(location.getLocationId(), location);
    }
}
