package kuchtastefan.item.specificItems.consumeableItem;


import kuchtastefan.item.itemFilter.ItemFilter;
import kuchtastefan.item.itemType.ItemType;
import kuchtastefan.utility.LetterToNumber;
import kuchtastefan.utility.printUtil.PrintUtil;
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


    @Override
    public void printTypeSelection() {
        int index = 1;
        for (ConsumableItemType consumableItemType : ConsumableItemType.values()) {
            PrintUtil.printIndexAndText(LetterToNumber.getStringFromValue(index), consumableItemType.toString());
            index++;
        }
    }

    @Override
    public ItemFilter returnItemType(int ordinal) {
        return null;
    }
}
