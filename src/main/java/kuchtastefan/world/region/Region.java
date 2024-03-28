package kuchtastefan.world.region;

import kuchtastefan.world.location.Location;
import kuchtastefan.world.Biome;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Region {
    private final int regionId;
    protected String regionName;
    protected String regionDescription;
    protected int minimumRegionLevel;
    protected int maximumRegionLevel;
    protected Biome biome;
    private int[] locationsId;
    protected List<Location> allLocations;


    public Region(int regionId, String regionName, String regionDescription, int minimumRegionLevel, int maximumRegionLevel) {
        this.regionId = regionId;
        this.regionName = regionName;
        this.regionDescription = regionDescription;
        this.minimumRegionLevel = minimumRegionLevel;
        this.maximumRegionLevel = maximumRegionLevel;
    }
}
