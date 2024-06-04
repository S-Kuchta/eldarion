package kuchtastefan.world.location;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.hero.save.location.HeroLocation;
import kuchtastefan.service.FileService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationDBTest {

    @BeforeAll
    static void setUp() {
        FileService fileService = new FileService();
        fileService.importLocationsFromFile();
    }


    @Test
    void addLocationTest() {
        int newLocationId = 2000;
        Location location = new Location(newLocationId, "Test Location", 1);
        LocationDB.addLocationToDB(location);

        assertEquals(location, LocationDB.getLocationById(newLocationId));
    }

    @Test
    void syncWithSaveGameDiscoveredLocationTest() {
        Hero hero = new Hero("Test");
        Location location = LocationDB.getLocationById(1);

        hero.getSaveGameEntities().getHeroLocations().addEntity(new HeroLocation(location.getLocationId(),
                LocationStatus.COMPLETED, LocationDB.returnHeroLocationStages(location.getLocationId())));

        LocationDB.syncWithSaveGame(hero.getSaveGameEntities().getHeroLocations());

        assertEquals(location.getLocationStatus(), hero.getSaveGameEntities().getHeroLocations().getEntity(location.getLocationId()).getLocationStatus());
    }
}