package kuchtastefan.inventory;

import kuchtastefan.item.wearableItem.WearableItem;

import java.util.List;

public class ItemInventoryList {

    private final List<WearableItem> wearableItemInventoryList;

    public ItemInventoryList(List<WearableItem> equipInventoryList) {
        this.wearableItemInventoryList = equipInventoryList;
    }

    public List<WearableItem> getEquipInventoryList() {
        return wearableItemInventoryList;
    }
}
