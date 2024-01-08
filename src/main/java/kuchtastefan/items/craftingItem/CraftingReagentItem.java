package kuchtastefan.items.craftingItem;

import kuchtastefan.items.Item;

public class CraftingReagentItem extends Item {


    private CraftingReagentItemType craftingReagentItemType;

    public CraftingReagentItem(String name, double price, CraftingReagentItemType craftingReagentItemType, int itemLevel) {
        super(name, price, itemLevel);
        this.craftingReagentItemType = craftingReagentItemType;
    }

    public CraftingReagentItemType getCraftingReagentItemType() {
        return craftingReagentItemType;
    }

    public void setCraftingReagentItemType(CraftingReagentItemType craftingReagentItemType) {
        this.craftingReagentItemType = craftingReagentItemType;
    }
}
