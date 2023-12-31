package kuchtastefan.item;

import kuchtastefan.item.wearableItem.wearableItem;

import java.util.List;

public class ItemList {
    private final List<wearableItem> wearableItemList;

    public ItemList(List<wearableItem> wearableItemList) {
        this.wearableItemList = wearableItemList;
    }

    public List<wearableItem> getItemList() {
        return wearableItemList;
    }
}
