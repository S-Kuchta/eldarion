package kuchtastefan.items.wearableItem;

import lombok.Getter;

@Getter
public enum WearableItemQuality {
    BASIC("Basic item quality"),
    IMPROVED("Improved item quality - item ability stats are increased by 1"),
    SUPERIOR("Superior item quality - item ability stats are increased by 2"),
    SPECIAL("Special item quality - items can be obtained in special treasure or as boss drop"),
    QUEST_REWARD("Item from completing the quest");

    private final String description;

    WearableItemQuality(String description) {
        this.description = description;
    }

}
