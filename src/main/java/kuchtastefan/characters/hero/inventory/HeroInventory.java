package kuchtastefan.characters.hero.inventory;

import com.google.gson.Gson;
import kuchtastefan.items.Item;
import kuchtastefan.items.consumeableItem.ConsumableItem;
import kuchtastefan.items.craftingItem.CraftingReagentItem;
import kuchtastefan.items.junkItem.JunkItem;
import kuchtastefan.items.questItem.QuestItem;
import kuchtastefan.items.wearableItem.WearableItem;

import java.util.*;

public class HeroInventory {

    private final Map<Item, Integer> heroInventory;
    private final Map<WearableItem, Integer> wearableItemInventory;
    private final Map<CraftingReagentItem, Integer> craftingReagentItemInventory;
    private final Map<ConsumableItem, Integer> consumableItemInventory;
    private final Map<QuestItem, Integer> questItemInventory;
    private final Map<JunkItem, Integer> junkItemInventory;


    public HeroInventory() {
        this.heroInventory = new HashMap<>();
        this.wearableItemInventory = new HashMap<>();
        this.craftingReagentItemInventory = new HashMap<>();
        this.consumableItemInventory = new HashMap<>();
        this.questItemInventory = new HashMap<>();
        this.junkItemInventory = new HashMap<>();
    }

    public Map<Item, Integer> getHeroInventory() {
        return heroInventory;
    }

    public void addItemToItemList(Item item) {
        addItemToInventory(item);
    }

    public void addItemWithNewCopyToItemList(Item item) {
        Gson gson = new Gson();
        Class<? extends Item> itemClass = item.getClass();

        if (Item.class.isAssignableFrom(itemClass)) {
            Item itemCopy = gson.fromJson(gson.toJson(item), itemClass);
            addItemToInventory(itemCopy);
        }
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

    public boolean checkIfHeroInventoryContainsNeededItemsIfTrueRemoveIt(Map<? extends Item, Integer> neededItems, boolean removeItem) {
        for (Map.Entry<? extends Item, Integer> neededItem : neededItems.entrySet()) {
            if (this.heroInventory.containsKey(neededItem.getKey()) && neededItem.getValue() <= this.heroInventory.get(neededItem.getKey())) {
                if (removeItem) {
                    this.heroInventory.put(neededItem.getKey(), heroInventory.get(neededItem.getKey()) - neededItem.getValue());
                }
                System.out.println("return true");
                return true;
            }
        }

        return false;
    }

    public void changeList() {
        Gson gson = new Gson();
        for (Map.Entry<Item, Integer> item : this.heroInventory.entrySet()) {
            if (item.getKey() instanceof WearableItem) {
                WearableItem itemCopy = gson.fromJson(gson.toJson(item.getKey()), WearableItem.class);
                this.wearableItemInventory.put(itemCopy, item.getValue());
            }

            if (item.getKey() instanceof CraftingReagentItem) {
                CraftingReagentItem itemCopy = gson.fromJson(gson.toJson(item.getKey()), CraftingReagentItem.class);
                this.craftingReagentItemInventory.put(itemCopy, item.getValue());
            }

            if (item.getKey() instanceof ConsumableItem) {
                ConsumableItem itemCopy = gson.fromJson(gson.toJson(item.getKey()), ConsumableItem.class);
                this.consumableItemInventory.put(itemCopy, item.getValue());
            }

            if (item.getKey() instanceof QuestItem) {
                QuestItem itemCopy = gson.fromJson(gson.toJson(item.getKey()), QuestItem.class);
                this.questItemInventory.put(itemCopy, item.getValue());
            }

            if (item.getKey() instanceof JunkItem) {
                JunkItem itemCopy = gson.fromJson(gson.toJson(item.getKey()), JunkItem.class);
                this.junkItemInventory.put(itemCopy, item.getValue());
            }
        }

        this.heroInventory.clear();
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

    public Map<JunkItem, Integer> returnInventoryJunkItemMap() {
        Map<JunkItem, Integer> junkItems = new HashMap<>();
        for (Map.Entry<Item, Integer> item : this.heroInventory.entrySet()) {
            if (item.getKey() instanceof JunkItem) {
                junkItems.put((JunkItem) item.getKey(), item.getValue());
            }
        }
        return junkItems;
    }

    public Map<QuestItem, Integer> returnInventoryQuestItemMap() {
        Map<QuestItem, Integer> questItems = new HashMap<>();
        for (Map.Entry<Item, Integer> item : this.heroInventory.entrySet()) {
            if (item.getKey() instanceof QuestItem) {
                questItems.put((QuestItem) item.getKey(), item.getValue());
            }
        }
        return questItems;
    }

    public Map<ConsumableItem, Integer> returnInventoryConsumableItemMap() {
        Map<ConsumableItem, Integer> consumableItems = new HashMap<>();
        for (Map.Entry<Item, Integer> item : this.heroInventory.entrySet()) {
            if (item.getKey() instanceof ConsumableItem) {
                consumableItems.put((ConsumableItem) item.getKey(), item.getValue());
            }
        }
        return consumableItems;
    }

    public Map<WearableItem, Integer> getWearableItemInventory() {
        return wearableItemInventory;
    }

    public Map<CraftingReagentItem, Integer> getCraftingReagentItemInventory() {
        return craftingReagentItemInventory;
    }

    public Map<ConsumableItem, Integer> getConsumableItemInventory() {
        return consumableItemInventory;
    }

    public Map<QuestItem, Integer> getQuestItemInventory() {
        return questItemInventory;
    }

    public Map<JunkItem, Integer> getJunkItemInventory() {
        return junkItemInventory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeroInventory that = (HeroInventory) o;
        return Objects.equals(heroInventory, that.heroInventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(heroInventory);
    }
}
