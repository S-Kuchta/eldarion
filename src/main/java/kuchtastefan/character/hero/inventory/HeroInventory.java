package kuchtastefan.character.hero.inventory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.workshop.Workshop;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import kuchtastefan.utility.RuntimeTypeAdapterFactoryUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class HeroInventory {

    private final Map<Item, Integer> heroInventory;


    public HeroInventory() {
        this.heroInventory = new HashMap<>();
    }

    public void addItemWithNewCopyToItemList(Item item) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.actionsRuntimeTypeAdapterFactory).create();
        Item itemCopy = gson.fromJson(gson.toJson(item), item.getClass());
        addItemToInventory(itemCopy);
    }

    public void addItemToInventory(Item item) {
        this.heroInventory.merge(item, 1, Integer::sum);
    }

//    public void addItemToInventory(Item item) {
//        if (this.getHeroInventory().isEmpty()) {
//            this.getHeroInventory().put(item, 1);
//        } else {
//            boolean found = false;
//            for (Map.Entry<Item, Integer> itemMap : this.getHeroInventory().entrySet()) {
//                if (itemMap.getKey().equals(item)) {
//                    itemMap.setValue(itemMap.getValue() + 1);
//                    found = true;
//                    break;
//                }
//            }
//
//            if (!found) {
//                this.getHeroInventory().put(item, 1);
//            }
//        }
//    }

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
     * @param removeItem  if true item will be removed from hero inventory
     * @return return true or false
     */
    public boolean checkAndRemoveItemsIfRequired(Map<? extends Item, Integer> neededItems, boolean removeItem) {
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

    public boolean hasRequiredItems(Item item, int count) {
        if (this.heroInventory.containsKey(item)) {
            return this.heroInventory.get(item) >= count;
        }

        return false;
    }

    public void removeItemFromHeroInventory(Item item, int count) {
        if (this.heroInventory.isEmpty()) {
            System.out.println("\tYou don't have anything to remove");
            return;
        }

        if (hasRequiredItems(item, count)) {
            if (this.heroInventory.get(item) == count) {
                this.heroInventory.remove(item);
                System.out.println("\t" + ConsoleColor.YELLOW + item.getName() + ConsoleColor.RESET + " removed from inventory");
            } else {
                this.heroInventory.put(item, this.heroInventory.get(item) - count);
                System.out.println("\tYou now have " + this.heroInventory.get(item) + "x "
                        + ConsoleColor.YELLOW + item.getName() + ConsoleColor.RESET + " in inventory");
            }
        }
    }

    public <T extends Item> Map<T, Integer> returnHeroInventoryByClass(Class<T> itemClass) {
        Map<T, Integer> itemMap = new HashMap<>();

        for (Map.Entry<? extends Item, Integer> entry : this.getHeroInventory().entrySet()) {
            if (itemClass.isInstance(entry.getKey())) {
                itemMap.put(itemClass.cast(entry.getKey()), entry.getValue());
            }
        }

        return itemMap;
    }

    public <T extends Item> void selectItem(Hero hero, Class<T> itemClass, Workshop workshop) {
        hero.getHeroInventory().printHeroInventoryByClass(itemClass, 1, hero);

        final int choice = InputUtil.intScanner();
        if (choice == 0) {
            workshop.workshopMenu(hero);
        } else {
            List<T> items = new ArrayList<>(hero.getHeroInventory().returnHeroInventoryByClass(itemClass).keySet());
            if (choice - 1 < items.size()) {
                workshop.itemMenu(hero, items.get(choice - 1));
            } else {
                PrintUtil.printEnterValidInput();
            }
        }
    }

    public <T extends Item> void printHeroInventoryByClass(Class<T> itemClass, int indexStart, Hero hero) {
        Map<T, Integer> inventory = returnHeroInventoryByClass(itemClass);
        int index = indexStart;
        for (Map.Entry<T, Integer> entry : inventory.entrySet()) {
            PrintUtil.printIndexAndText(String.valueOf(index), "(" + entry.getValue() + "x) ");
            entry.getKey().printItemDescription(hero);
            index++;
        }
    }
}
