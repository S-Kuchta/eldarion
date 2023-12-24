package kuchtastefan.inventory;

import kuchtastefan.item.Item;

import java.util.List;

public class ItemInventoryList {

    private final List<Item> itemInventoryList;

    public ItemInventoryList(List<Item> equipInventoryList) {
        this.itemInventoryList = equipInventoryList;
    }

    public List<Item> getEquipInventoryList() {
        return itemInventoryList;
    }
}
