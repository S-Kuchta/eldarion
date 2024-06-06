package kuchtastefan.world;

import lombok.Getter;

@Getter
public enum Biome {
    FOREST("Region"),
    DARK_FOREST("Region"),
    HIGHLAND("Region"),
    MINE("Location"),
    CAVE("Location"),
    TOWER("Location"),
    CASTLE("Location"),
    LAKE("Location"),
    CEMETERY("Location"),
    VILLAGE("Location");

    private final String description;

    Biome(String description) {
        this.description = description;
    }
}
