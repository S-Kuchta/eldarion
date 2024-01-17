package kuchtastefan.regions.locations;

import lombok.Getter;

@Getter
public enum LocationType {
    FOREST("Region"),
    HIGHLAND("Region"),
    MINE("Location"),
    CAVE("Location"),
    TOWER("Location"),
    CASTLE("Location"),
    CEMETERY("Location"),
    VILLAGE("Location");

    private final String description;

    LocationType(String description) {
        this.description = description;
    }
}
