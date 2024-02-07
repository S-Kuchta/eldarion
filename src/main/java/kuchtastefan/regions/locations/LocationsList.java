package kuchtastefan.regions.locations;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationsList {
    @Getter
    private static final Map<Integer, Location> locationList = new HashMap<>();
}
