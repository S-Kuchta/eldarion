package kuchtastefan.regions.placesInLocations;

public enum CemeteryPlaces {
    CRYPT(""),
    TOWER(""),
    MINE(""),
    CASTLE(""),
    FOREST(""),
    HIGHLAND(""),
    CEMETERY(""),
    VILLAGE("");

    private final String description;

    CemeteryPlaces(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
