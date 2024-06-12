package kuchtastefan.item.specificItems.craftingItem;

import kuchtastefan.item.itemFilter.ItemFilter;
import kuchtastefan.item.itemType.ItemType;
import kuchtastefan.item.specificItems.consumeableItem.ConsumableItemType;
import kuchtastefan.utility.LetterToNumber;
import kuchtastefan.utility.printUtil.PrintUtil;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum CraftingReagentItemType implements ItemType {
    BLACKSMITH_REAGENT("Reagent used for blacksmith. For smith or refinement items."),
    ALCHEMY_REAGENT("Reagent used for alchemy. For create new potions,");

    private final String description;

    CraftingReagentItemType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return StringUtils.capitalize(name().toLowerCase().replace("_", " "));
    }


    @Override
    public void printTypeSelection() {
        int index = 1;
        for (CraftingReagentItemType craftingReagentItemType : CraftingReagentItemType.values()) {
            PrintUtil.printIndexAndText(LetterToNumber.getStringFromValue(index), craftingReagentItemType.toString());
            index++;
        }
    }

    @Override
    public ItemFilter returnItemType() {
        return null;
    }
}
