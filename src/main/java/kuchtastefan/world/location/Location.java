package kuchtastefan.world.location;

import kuchtastefan.world.location.locationStage.LocationStage;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class Location {

    private final int locationId;
    protected final String locationName;
    protected final int locationLevel;
    protected int stageTotal;
    protected int stageCompleted;
    protected boolean cleared;
    protected boolean canLocationBeExplored;
    protected Map<Integer, LocationStage> locationStages;


    public Location(int locationId, String locationName, int locationLevel, int stageTotal, boolean canLocationBeExplored) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.locationLevel = locationLevel;
        this.stageTotal = stageTotal;
        this.stageCompleted = 0;
        this.cleared = false;
        this.canLocationBeExplored = canLocationBeExplored;
        this.locationStages = new HashMap<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return locationLevel == location.locationLevel
                && cleared == location.cleared
                && stageTotal == location.stageTotal
                && Objects.equals(locationName, location.locationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationName, locationLevel, stageTotal, cleared);
    }
}
