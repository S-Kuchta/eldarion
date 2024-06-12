package kuchtastefan.item.specificItems.wearableItem;

import kuchtastefan.item.itemFilter.ItemFilter;
import kuchtastefan.item.itemType.ItemType;
import kuchtastefan.utility.LetterToNumber;
import kuchtastefan.utility.printUtil.PrintUtil;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum WearableItemType implements ItemType {

    WEAPON("Weapon"),
    HEAD("Head armor"),
    BODY("Body armor"),
    HANDS("Hands armor"),
    BOOTS("Boots armor");

    private final String description;

    WearableItemType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return StringUtils.capitalize(name().toLowerCase().replace("_", " "));
    }


    @Override
    public void printTypeSelection() {
        int index = 1;
        for (WearableItemType wearableItemType : WearableItemType.values()) {
            PrintUtil.printIndexAndText(LetterToNumber.getStringFromValue(index), wearableItemType.toString());
            index++;
        }
    }

    @Override
    public ItemFilter returnItemType() {
        return null;
    }
}
