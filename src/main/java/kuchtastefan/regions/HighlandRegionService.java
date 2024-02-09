package kuchtastefan.regions;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.regions.locations.Location;

import java.util.List;

public class HighlandRegionService extends Region {

    public HighlandRegionService(String regionName, String regionDescription, Hero hero, int minimumRegionLevel, int maximumRegionLevel) {
        super(regionName, regionDescription, hero, minimumRegionLevel, maximumRegionLevel);
    }



    @Override
    protected List<Location> initializeLocationForRegion() {
        return null;
    }
}
