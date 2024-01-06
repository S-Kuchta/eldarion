package kuchtastefan.inventory;

import kuchtastefan.item.Item;
import kuchtastefan.item.craftingItem.CraftingReagentItem;
import kuchtastefan.item.wearableItem.WearableItem;

import java.util.*;

public class ItemInventoryList {

    private Map<Item, Integer> heroInventory;

    public ItemInventoryList(Map<Item, Integer> newItemList) {
        this.heroInventory = newItemList;
    }



    public Map<Item, Integer> getHeroInventory() {
        return heroInventory;
    }

    public void setHeroInventory(Map<Item, Integer> heroInventory) {
        this.heroInventory = heroInventory;
    }

    public List<WearableItem> returnInventoryWearableItemList() {
        List<WearableItem> wearableItemList = new ArrayList<>();
        for (Map.Entry<Item, Integer> item : this.heroInventory.entrySet()) {
            if (item.getKey().getClass().equals(WearableItem.class)) {
                wearableItemList.add((WearableItem) item.getKey());
            }
        }
        return wearableItemList;
    }

    public Map<WearableItem, Integer> returnInventoryWearableItemMap() {
        Map<WearableItem, Integer> wearableItemMap = new HashMap<>();
        for (Map.Entry<Item, Integer> item : this.heroInventory.entrySet()) {
            if (item.getKey().getClass().equals(WearableItem.class)) {
                wearableItemMap.put((WearableItem) item.getKey(), item.getValue());
            }
        }
        return wearableItemMap;
    }

    public List<CraftingReagentItem> returnInventoryCraftingReagentItemList() {
        List<CraftingReagentItem> craftingReagentItems = new ArrayList<>();
        for (Map.Entry<Item, Integer> item : this.heroInventory.entrySet()) {
            if (item.getKey().getClass().equals(CraftingReagentItem.class)) {
                craftingReagentItems.add((CraftingReagentItem) item.getKey());
            }
        }
        return craftingReagentItems;
    }

    public Map<CraftingReagentItem, Integer> returnInventoryCraftingReagentItemMap() {
        Map<CraftingReagentItem, Integer> wearableItemMap = new HashMap<>();
        for (Map.Entry<Item, Integer> item : this.heroInventory.entrySet()) {
            if (item.getKey().getClass().equals(CraftingReagentItem.class)) {
                wearableItemMap.put((CraftingReagentItem) item.getKey(), item.getValue());
            }
        }
        return wearableItemMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemInventoryList that = (ItemInventoryList) o;
        return Objects.equals(heroInventory, that.heroInventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(heroInventory);
    }
}
