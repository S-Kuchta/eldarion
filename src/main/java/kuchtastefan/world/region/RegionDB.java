package kuchtastefan.world.region;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class RegionDB {
    @Getter
    private static final Map<Integer, Region> REGION_DB = new HashMap<>();

    public static void addRegionToDB(Region region) {
        REGION_DB.put(region.getRegionId(), region);
    }

    public static Region returnRegion(int regionId) {
        return REGION_DB.get(regionId);
    }

    public static String returnRegionName(int regionId) {
        return REGION_DB.get(regionId).getRegionName();
    }
}
