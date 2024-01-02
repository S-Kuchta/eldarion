package kuchtastefan.item;

import kuchtastefan.item.craftingItem.CraftingReagentItem;
import kuchtastefan.item.wearableItem.WearableItem;

import java.util.List;

public class ItemsLists {
    private final List<WearableItem> wearableItemList;
    private final List<CraftingReagentItem> craftingReagentItems;

    public ItemsLists(List<WearableItem> wearableItemList, List<CraftingReagentItem> craftingReagentItems) {
        this.wearableItemList = wearableItemList;
        this.craftingReagentItems = craftingReagentItems;
    }

    public List<WearableItem> getItemList() {
        return wearableItemList;
    }

    public List<CraftingReagentItem> getCraftingReagentItems() {
        return craftingReagentItems;
    }
}
