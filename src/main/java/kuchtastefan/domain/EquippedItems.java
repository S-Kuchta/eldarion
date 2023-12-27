package kuchtastefan.domain;

import kuchtastefan.item.Item;
import kuchtastefan.item.ItemType;

import java.util.HashMap;
import java.util.Map;

public class EquippedItems {
    private Map<ItemType, Item> equippedItem;

    public EquippedItems() {
        this.equippedItem = new HashMap<>();
    }

    public Map<ItemType, Item> getEquippedItem() {
        return equippedItem;
    }

    public void setEquippedItem(Map<ItemType, Item> equippedItem) {
        this.equippedItem = equippedItem;
    }
}
