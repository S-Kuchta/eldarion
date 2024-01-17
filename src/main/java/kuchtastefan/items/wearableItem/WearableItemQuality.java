package kuchtastefan.items.wearableItem;

import lombok.Getter;

@Getter
public enum WearableItemQuality {
    BASIC("Basic item quality"),
    IMPROVED("Improved item quality - item ability stats are increased by 1"),
    SUPERIOR("Superior item quality - item ability stats are increased by 2");

    private final String description;

    WearableItemQuality(String description) {
        this.description = description;
    }

}
