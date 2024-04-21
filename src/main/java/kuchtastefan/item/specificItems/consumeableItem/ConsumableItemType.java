package kuchtastefan.item.specificItems.consumeableItem;


import kuchtastefan.item.itemType.ItemType;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum ConsumableItemType implements ItemType {

    POTION("Potions are used for restore health, increase ability etc. Can be use during combat"),
    FOOD("Food is used for restore health. Can be use only out of combat");

    private final String description;

    ConsumableItemType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return StringUtils.capitalize(name().toLowerCase().replace("_", " "));
    }
}
