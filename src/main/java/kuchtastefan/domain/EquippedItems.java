package kuchtastefan.domain;

import kuchtastefan.item.ItemType;

import java.util.HashMap;
import java.util.Map;

public class EquippedItems {
    private Map<ItemType, String> equippedItem;

    public EquippedItems() {
        this.equippedItem = new HashMap<>();
    }

    public Map<ItemType, String> getEquippedItem() {
        return equippedItem;
    }

    public void setEquippedItem(Map<ItemType, String> equippedItem) {
        this.equippedItem = equippedItem;
    }
}
