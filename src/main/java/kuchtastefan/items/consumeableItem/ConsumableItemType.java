package kuchtastefan.items.consumeableItem;

import lombok.Getter;

@Getter
public enum ConsumableItemType {
    POTION("Potions are used for restore health, increase ability etc. Can be use during combat"),
    FOOD("Food is used for restore health. Can be use only out of combat");

    private final String description;

    ConsumableItemType(String description) {
        this.description = description;
    }
}
