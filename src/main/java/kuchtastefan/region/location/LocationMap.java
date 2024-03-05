package kuchtastefan.region.location;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class LocationMap {
    @Getter
    private static final Map<Integer, Location> mapIdLocation = new HashMap<>();
}
