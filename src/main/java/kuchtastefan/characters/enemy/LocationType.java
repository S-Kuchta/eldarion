package kuchtastefan.characters.enemy;

public enum LocationType {
    CAVE(""),
    TOWER(""),
    MINE(""),
    CASTLE(""),
    FOREST(""),
    HIGHLAND("");

    private final String description;

    LocationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
