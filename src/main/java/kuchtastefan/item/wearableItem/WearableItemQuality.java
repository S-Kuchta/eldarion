package kuchtastefan.item.wearableItem;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum WearableItemQuality {
    BASIC("Basic item quality"),
    IMPROVED("Improved item quality - item ability stats are doubled"),
    SPECIAL("Special item quality - items can be obtained in special treasure or as boss drop"),
    QUEST_REWARD("Item from completing the quest");

    private final String description;

    WearableItemQuality(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return StringUtils.capitalize(name().toLowerCase().replace("_", " "));
    }
}
