package kuchtastefan.characters.hero.inventory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kuchtastefan.items.Item;
import kuchtastefan.items.consumeableItem.ConsumableItem;
import kuchtastefan.items.craftingItem.CraftingReagentItem;
import kuchtastefan.items.junkItem.JunkItem;
import kuchtastefan.items.questItem.QuestItem;
import kuchtastefan.items.wearableItem.WearableItem;
import kuchtastefan.utility.RuntimeTypeAdapterFactoryUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class HeroInventory {

    private final Map<Item, Integer> heroInventory;


    public HeroInventory() {
        this.heroInventory = new HashMap<>();
    }

    public void addItemWithNewCopyToItemList(Item item) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.actionsRuntimeTypeAdapterFactory).create();
        Class<? extends Item> itemClass = item.getClass();

        if (Item.class.isAssignableFrom(itemClass)) {
            Item itemCopy = gson.fromJson(gson.toJson(item), itemClass);
            addItemToInventory(itemCopy);
        }
    }

    public void addItemToInventory(Item item) {
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

    /**
     * This method is used for check hero inventory for needed items and item count in inventory.
     * If hero inventory has enough items, method return true, otherwise return false.
     *
     * @param neededItems Map of needed items - as a key use needed Item and as Integer count of items needed
     * @param removeItem if true item will be removed from hero inventory
     * @return return true or false
     */
    public boolean checkIfHeroInventoryContainsNeededItemsIfTrueRemoveIt(Map<? extends Item, Integer> neededItems, boolean removeItem) {
        for (Map.Entry<? extends Item, Integer> neededItem : neededItems.entrySet()) {
            if (this.heroInventory.containsKey(neededItem.getKey())
                    && neededItem.getValue() <= this.heroInventory.get(neededItem.getKey())) {

                if (removeItem) {
                    this.heroInventory.put(neededItem.getKey(), this.heroInventory.get(neededItem.getKey()) - neededItem.getValue());
                }
                return true;
            }
        }

        return false;
    }

    /**
     * Remove one item from hero inventory, if in inventory are more than one item, change count value.
     *
     * @param item to remove
     */
    public void removeItemFromHeroInventory(Item item) {
        Map<Item, Integer> heroInventory = this.getHeroInventory();

        if (heroInventory == null) {
            System.out.println("\tYou don't have anything to remove");
            return;
        }

        Iterator<Map.Entry<Item, Integer>> iterator = heroInventory.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Item, Integer> entry = iterator.next();
            if (entry.getKey().equals(item) && entry.getValue() > 1) {
                heroInventory.put(item, entry.getValue() - 1);
                break;
            } else if (entry.getKey().equals(item)) {
                iterator.remove();
            }
        }
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
