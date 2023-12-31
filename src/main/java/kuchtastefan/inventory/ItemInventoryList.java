package kuchtastefan.inventory;

import kuchtastefan.item.wearableItem.wearableItem;

import java.util.List;

public class ItemInventoryList {

    private final List<wearableItem> wearableItemInventoryList;

    public ItemInventoryList(List<wearableItem> equipInventoryList) {
        this.wearableItemInventoryList = equipInventoryList;
    }

    public List<wearableItem> getEquipInventoryList() {
        return wearableItemInventoryList;
    }
}
