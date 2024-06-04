package kuchtastefan.world.location;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.hero.save.SaveGameEntityList;
import kuchtastefan.character.hero.save.location.HeroLocation;
import kuchtastefan.character.hero.save.location.HeroLocationStage;
import kuchtastefan.world.location.locationStage.LocationStage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationDB {

    private static final Map<Integer, Location> LOCATION_DB = new HashMap<>();

    public static Location getLocationById(int locationId) {
        return LOCATION_DB.get(locationId);
    }

    public static void addLocationToDB(Location location) {
        LOCATION_DB.put(location.getLocationId(), location);
    }

    public static Map<Integer, HeroLocationStage> returnHeroLocationStages(int locationId) {
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

    public static List<Location> getLocationListByIds(int[] locationIds) {
        List<Location> locations = new ArrayList<>();
        for (int locationId : locationIds) {
            locations.add(getLocationById(locationId));
        }

        return locations;
    }

    public static List<Location> getClearedAndDiscoveredLocationListByIds(int[] locationIds) {
        List<Location> locations = new ArrayList<>();
        for (int locationId : locationIds) {
            if (getLocationById(locationId).isDiscovered() || getLocationById(locationId).isCompleted()) {
                locations.add(getLocationById(locationId));
            }
        }

        return locations;
    }

    public static void syncWithSaveGame(SaveGameEntityList<HeroLocation> heroLocations) {
        for (HeroLocation heroLocation : heroLocations.getSaveEntities().values()) {
            Location location = getLocationById(heroLocation.getId());
            location.setLocationStatus(heroLocation.getLocationStatus());
            for (HeroLocationStage heroLocationStage : heroLocation.getStages().values()) {
                location.getLocationStage(heroLocationStage.getOrder()).setStageStatus(heroLocationStage.getStageStatus());
            }
        }
    }

    public static void saveDatabase(Hero hero) {
        for (Location location : getLocationListByIds(hero.getSaveGameEntities().getHeroLocations().getEntitiesIds())) {
            hero.getSaveGameEntities().getHeroLocations().addEntity(new HeroLocation(location.getLocationId(),
                    location.getLocationStatus(), returnHeroLocationStages(location.getLocationId())));
        }
    }
}
