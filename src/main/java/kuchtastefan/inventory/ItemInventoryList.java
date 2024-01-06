package kuchtastefan.inventory;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.gson.Gson;
import kuchtastefan.item.Item;
import kuchtastefan.item.craftingItem.CraftingReagentItem;
import kuchtastefan.item.wearableItem.WearableItem;

import java.util.*;



public class ItemInventoryList {

    private final Map<Item, Integer> heroInventory;

    private Map<WearableItem, Integer> wearableItemInventory;
    private Map<CraftingReagentItem, Integer> craftingReagentItemInventory;


    public ItemInventoryList() {
        this.heroInventory = new HashMap<>();
    }

    public Map<Item, Integer> getHeroInventory() {
        return heroInventory;
    }

    public void addItemToItemList(Item item) {
        addItemToInventory(item);
    }

    public void addItemWithNewCopyToItemList(Item item) {
        Gson gson = new Gson();
        WearableItem itemCopy = gson.fromJson(gson.toJson(item), WearableItem.class);
        addItemToInventory(itemCopy);
    }

    private void addItemToInventory(Item item) {
        if (this.getHeroInventory().isEmpty()) {
            this.getHeroInventory().put(item, 1);
        } else {
            boolean found = false;
            for (Map.Entry<Item, Integer> itemMap : this.getHeroInventory().entrySet()) {
                if (itemMap.getKey().equals(item)) {
                    itemMap.setValue(itemMap.getValue() + 1);
                    found = true;
                    break;
                }
            }

            if (!found) {
                this.getHeroInventory().put(item, 1);
            }
        }
    }

    public void removeItemFromItemList(Item item) {
        Map<Item, Integer> heroInventory = this.getHeroInventory();

        if (heroInventory == null) {
            System.out.println("You don't have anything to remove");
            return;
        }

        Iterator<Map.Entry<Item, Integer>> iterator = heroInventory.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Item, Integer> entry = iterator.next();
            if (entry.getKey().equals(item) && entry.getValue() > 1) {
                heroInventory.put(item, entry.getValue() - 1);
                return;
            } else if (entry.getKey().equals(item)) {
                iterator.remove();
            }
        }
    }

    public List<WearableItem> returnInventoryWearableItemList() {
        List<WearableItem> wearableItemList = new ArrayList<>();
        for (Map.Entry<Item, Integer> item : this.heroInventory.entrySet()) {
            if (item.getKey() instanceof WearableItem) {
                wearableItemList.add((WearableItem) item.getKey());
            }
        }
        return wearableItemList;
    }

    public Map<WearableItem, Integer> returnInventoryWearableItemMap() {
        Map<WearableItem, Integer> wearableItemMap = new HashMap<>();
        for (Map.Entry<Item, Integer> item : this.heroInventory.entrySet()) {
            if (item.getKey() instanceof WearableItem) {
                wearableItemMap.put((WearableItem) item.getKey(), item.getValue());
            }
        }
        return wearableItemMap;
    }

    public List<CraftingReagentItem> returnInventoryCraftingReagentItemList() {
        List<CraftingReagentItem> craftingReagentItems = new ArrayList<>();
        for (Map.Entry<Item, Integer> item : this.heroInventory.entrySet()) {
            if (item.getKey() instanceof CraftingReagentItem) {
                craftingReagentItems.add((CraftingReagentItem) item.getKey());
            }
        }
        return craftingReagentItems;
    }

    public Map<CraftingReagentItem, Integer> returnInventoryCraftingReagentItemMap() {
        Map<CraftingReagentItem, Integer> craftingReagentItems = new HashMap<>();
        for (Map.Entry<Item, Integer> item : this.heroInventory.entrySet()) {
            if (item.getKey() instanceof CraftingReagentItem) {
                craftingReagentItems.put((CraftingReagentItem) item.getKey(), item.getValue());
            }
        }
        return craftingReagentItems;
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
