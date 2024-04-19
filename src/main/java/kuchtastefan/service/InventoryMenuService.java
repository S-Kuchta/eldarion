package kuchtastefan.service;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.UsableItem;
import kuchtastefan.item.consumeableItem.ConsumableItem;
import kuchtastefan.item.craftingItem.CraftingReagentItem;
import kuchtastefan.item.questItem.QuestItem;
import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.item.wearableItem.WearableItemType;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryMenuService {

    public void inventoryMenu(Hero hero) {
        PrintUtil.printMenuHeader(hero.getName() + " Inventory");
        PrintUtil.printMenuOptions("Go back", "Wearable Items", "Crafting reagents", "Consumable Items", "Quest Items");

        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
            }
            case 1 -> wearableItemsMenu(hero);
            case 2 -> itemInventoryMenu(hero, hero.getHeroInventory().returnHeroInventory(CraftingReagentItem.class));
            case 3 -> itemInventoryMenu(hero, hero.getHeroInventory().returnHeroInventory(ConsumableItem.class));
            case 4 -> itemInventoryMenu(hero, hero.getHeroInventory().returnHeroInventory(QuestItem.class));
            default -> PrintUtil.printEnterValidInput();
        }
    }

    public void wearableItemsMenu(Hero hero) {
        PrintUtil.printMenuHeader("Wearable");
        PrintUtil.printMenuOptions("Go back",
                "Weapons (" + PrintUtil.printWearableItemCountByType(hero, WearableItemType.WEAPON) + ")",
                "Head (" + PrintUtil.printWearableItemCountByType(hero, WearableItemType.HEAD) + ")",
                "Body (" + PrintUtil.printWearableItemCountByType(hero, WearableItemType.BODY) + ")",
                "Hands (" + PrintUtil.printWearableItemCountByType(hero, WearableItemType.HANDS) + ")",
                "Boots (" + PrintUtil.printWearableItemCountByType(hero, WearableItemType.BOOTS) + ")",
                "Wear off all equip");

        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> inventoryMenu(hero);
            case 1, 2, 3, 4, 5 -> printWearableItemInventoryMenuByItemType(WearableItemType.values()[choice - 1], hero);
            case 6 -> hero.wearDownAllEquippedItems();
            default -> PrintUtil.printEnterValidInput();
        }
    }

    /**
     * This method is used to print the inventory menu for wearable items of a specific type.
     * It first creates a temporary map of the hero's wearable items inventory.
     * Then it removes all entries from the map that do not match the specified wearable item type.
     * Finally, it calls the itemInventoryMenu method with the hero and the filtered map as arguments.
     *
     * @param wearableItemType The type of wearable item for which the inventory menu should be printed.
     * @param hero             The hero whose inventory is to be printed.
     */
    private void printWearableItemInventoryMenuByItemType(WearableItemType wearableItemType, Hero hero) {
        Map<WearableItem, Integer> itemMap = new HashMap<>(hero.getHeroInventory().returnHeroInventory(WearableItem.class));
        itemMap.entrySet().removeIf(entry -> !entry.getKey().getItemType().equals(wearableItemType));

        itemInventoryMenu(hero, itemMap);
    }

    /**
     * This method is used to print the inventory of items for a hero.
     * It first prints a "Go back" option, then checks if the items map is empty.
     * If the items map is empty, it prints "Item list is empty".
     * If the items map is not empty, it iterates over the entries in the map.
     * For each entry, it prints the index, the quantity of the item (from the map value), and the item description.
     * The index is incremented after each item.
     *
     * @param hero  The hero whose inventory is to be printed.
     * @param items The map of items to be printed. The map's keys are items, and the map's values are the quantities of each item.
     */
    public void printInventoryItems(Hero hero, Map<? extends Item, Integer> items) {
        int index = 1;
        PrintUtil.printIndexAndText("0", "Go back");
        System.out.println();
        if (items.isEmpty()) {
            System.out.println("\tItem list is empty");
        } else {
            for (Map.Entry<? extends Item, Integer> item : items.entrySet()) {
                PrintUtil.printIndexAndText(String.valueOf(index), "(" + item.getValue() + "x) ");
                item.getKey().printItemDescription(hero);
                index++;
            }
        }
    }

    /**
     * This method is used to display the inventory menu for a hero.
     * It first creates a list of items from the map's keys.
     * Then it creates a header for the inventory. If the list is empty, the header is "Empty Inventory".
     * Otherwise, the header is the name of the first item's class plus "Inventory".
     * If the input is 0, it checks if the hero is in combat. If the hero is not in combat, it calls the inventoryMenu method.
     * If the item is an instance of UsableItem, it calls the useItem method with the hero as an argument and returns its result.
     * If an IndexOutOfBoundsException is thrown, it prints "Enter valid input".
     * Finally, it returns false, indicating that the hero did not use an item.
     *
     * @param hero  The hero for whom the inventory menu is to be displayed.
     * @param items The map of items to be displayed. The map's keys are items, and the map's values are the quantities of each item.
     * @return false if the hero is in combat or the user's input is not a valid index, true if the useItem method of a UsableItem returns true.
     */
    public boolean itemInventoryMenu(Hero hero, Map<? extends Item, Integer> items) {
        List<? extends Item> itemList = new ArrayList<>(items.keySet());
        String header = itemList.isEmpty() ? "Empty Inventory" : itemList.getFirst().getClass().getSimpleName() + "Inventory";
        PrintUtil.printInventoryHeader(header);

        this.printInventoryItems(hero, items);
        int choice = InputUtil.intScanner();

        if (choice == 0) {
            if (!hero.isInCombat()) {
                inventoryMenu(hero);
            }

            return false;
        }

        try {
            Item chosenItem = itemList.get(choice - 1);
            if (chosenItem instanceof UsableItem usableItem) {
                return usableItem.useItem(hero);
            }
        } catch (IndexOutOfBoundsException e) {
            PrintUtil.printEnterValidInput();
        }

        return false;
    }
}
