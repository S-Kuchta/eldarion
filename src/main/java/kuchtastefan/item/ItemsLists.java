package kuchtastefan.item;

import kuchtastefan.item.craftingItem.CraftingReagentItem;
import kuchtastefan.item.craftingItem.CraftingReagentItemType;
import kuchtastefan.item.wearableItem.WearableItem;

import java.util.ArrayList;
import java.util.List;

public class ItemsLists {
    private final List<WearableItem> wearableItemList;
    private final List<CraftingReagentItem> craftingReagentItems;

    public ItemsLists() {
        this.wearableItemList = new ArrayList<>();
        this.craftingReagentItems = new ArrayList<>();
    }

    public List<WearableItem> getWearableItemList() {
        return wearableItemList;
    }

    public List<CraftingReagentItem> getCraftingReagentItems() {
        return craftingReagentItems;
    }

    public List<CraftingReagentItem> returnCraftingReagentItemListByType(CraftingReagentItemType craftingReagentItemType) {
        List<CraftingReagentItem> tempCraftingReagentItemList = new ArrayList<>();
        for (CraftingReagentItem craftingReagentItem : this.craftingReagentItems) {
            if (craftingReagentItem.getCraftingReagentItemType().equals(craftingReagentItemType)) {
                tempCraftingReagentItemList.add(craftingReagentItem);
            }
        }
        return tempCraftingReagentItemList;
    }

    public List<CraftingReagentItem> returnCraftingReagentItemListByTypeAndLevel(CraftingReagentItemType craftingReagentItemType, int itemLevel) {
        List<CraftingReagentItem> tempList = new ArrayList<>();
        for (CraftingReagentItem craftingReagentItem : returnCraftingReagentItemListByType(craftingReagentItemType)) {
            if (itemLevel + 1 > craftingReagentItem.getItemLevel() || itemLevel - 1 < craftingReagentItem.getItemLevel()) {
                tempList.add(craftingReagentItem);
            }
        }
        return tempList;
    }
}
