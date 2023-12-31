package kuchtastefan.item;

import kuchtastefan.item.wearableItem.WearableItem;

import java.util.List;

public class ItemList {
    private final List<WearableItem> wearableItemList;

    public ItemList(List<WearableItem> wearableItemList) {
        this.wearableItemList = wearableItemList;
    }

    public List<WearableItem> getItemList() {
        return wearableItemList;
    }
}
