package kuchtastefan.character.hero.inventory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kuchtastefan.item.Item;
import kuchtastefan.item.consumeableItem.ConsumableItem;
import kuchtastefan.item.craftingItem.CraftingReagentItem;
import kuchtastefan.item.junkItem.JunkItem;
import kuchtastefan.item.questItem.QuestItem;
import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.utility.RuntimeTypeAdapterFactoryUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public Item returnItemFromInventory(int itemId) {
        for (Item item : this.heroInventory.keySet()) {
            if (item.getItemId() == itemId) {
                return item;
            }
        }

        return null;
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

        heroInventory.entrySet().removeIf(entry ->
                checkIfHeroInventoryContainsNeededItemsIfTrueRemoveIt(Map.of(item, 1), false)
                && entry.getKey().equals(item) && entry.getValue() == 1);

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

    public <T extends Item> Map<T, Integer> returnHeroInventory(Class<T> itemClass) {
        Map<T, Integer> itemMap = new HashMap<>();
        Map<? extends Item, Integer> originalMap = new HashMap<>(this.getHeroInventory());

        for (Map.Entry<? extends Item, Integer> entry : originalMap.entrySet()) {
            if (itemClass.isInstance(entry.getKey())) {
                itemMap.put(itemClass.cast(entry.getKey()), entry.getValue());
            }
        }

        return itemMap;
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
