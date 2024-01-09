package kuchtastefan.regions.locations;

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

    public String getDescription() {
        return description;
    }
}
