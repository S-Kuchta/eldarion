package kuchtastefan.world.location;

import kuchtastefan.character.hero.save.location.HeroLocationStage;
import kuchtastefan.world.location.locationStage.LocationStage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationDB {

    private static final Map<Integer, Location> LOCATION_DB = new HashMap<>();

    public static Location returnLocation(int locationId) {
        return LOCATION_DB.get(locationId);
    }

    public static void addLocationToDB(Location location) {
        LOCATION_DB.put(location.getLocationId(), location);
    }

    public static Map<Integer, HeroLocationStage> returnLocationStages(int locationId) {
        HeroLocationStage heroLocationStage;
        Map<Integer, HeroLocationStage> heroLocationStages = new HashMap<>();
        for (Map.Entry<Integer, LocationStage> entry : LOCATION_DB.get(locationId).getLocationStages().entrySet()) {
            heroLocationStage = new HeroLocationStage(entry.getKey(), entry.getValue().getStageStatus());
            heroLocationStages.put(entry.getKey(), heroLocationStage);
        }

        return heroLocationStages;
    }

    public static List<Location> returnDiscoveredAndClearedLocations() {
        List<Location> discoveredLocations = new ArrayList<>();
        for (Location location : LOCATION_DB.values()) {
            if (location.isDiscovered() || location.isCompleted()) {
                discoveredLocations.add(location);
            }
        }

        return discoveredLocations;
    }
}
