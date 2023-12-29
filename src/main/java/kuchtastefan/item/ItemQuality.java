package kuchtastefan.item;

public enum ItemQuality {
    BASIC("Basic item quality"),
    IMPROVED("Improved item quality - item ability stats are increased by 1"),
    SUPERIOR("Superior item quality - item ability stats are increased by 2");

    private final String description;

    ItemQuality(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
