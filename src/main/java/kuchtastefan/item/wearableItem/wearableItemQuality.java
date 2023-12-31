package kuchtastefan.item.wearableItem;

public enum wearableItemQuality {
    BASIC("Basic item quality"),
    IMPROVED("Improved item quality - item ability stats are increased by 1"),
    SUPERIOR("Superior item quality - item ability stats are increased by 2");

    private final String description;

    wearableItemQuality(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
