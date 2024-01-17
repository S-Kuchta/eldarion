package kuchtastefan.regions.locations;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Location {

    protected final String locationName;
    protected final int locationLevel;
    protected int stageTotal;
    protected int stageCompleted;
    protected boolean cleared;
    protected final LocationType locationType;
    protected boolean canLocationBeExplored;

    public Location(String locationName, int locationLevel, int stageTotal, LocationType locationType, boolean canLocationBeExplored) {
        this.locationName = locationName;
        this.locationLevel = locationLevel;
        this.stageTotal = stageTotal;
        this.stageCompleted = 0;
        this.cleared = false;
        this.locationType = locationType;
        this.canLocationBeExplored = canLocationBeExplored;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return locationLevel == location.locationLevel && cleared == location.cleared && stageTotal == location.stageTotal && Objects.equals(locationName, location.locationName) && locationType == location.locationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationName, locationLevel, stageTotal, locationType, cleared);
    }
}
