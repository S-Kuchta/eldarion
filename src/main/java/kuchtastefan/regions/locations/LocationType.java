package kuchtastefan.regions.locations;

import lombok.Getter;

@Getter
public enum LocationType {
    CAVE(""),
    TOWER(""),
    MINE(""),
    CASTLE(""),
    FOREST(""),
    HIGHLAND(""),
    CEMETERY(""),
    VILLAGE("");

    private final String description;

    LocationType(String description) {
        this.description = description;
    }
}
