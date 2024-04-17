package kuchtastefan.service;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.UsableItem;
import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.item.wearableItem.WearableItemType;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

import java.util.List;
import java.util.Map;

public class InventoryMenuService {

    public void inventoryMenu(Hero hero) {
        PrintUtil.printDivider();
        System.out.println("\t\t------ " + hero.getName() + " Inventory ------");
        PrintUtil.printDivider();

        PrintUtil.printIndexAndText("0", "Go back");
        System.out.println();
        PrintUtil.printIndexAndText("1", "Wearable Items");
        System.out.println();
        PrintUtil.printIndexAndText("2", "Crafting reagents");
        System.out.println();
        PrintUtil.printIndexAndText("3", "Consumable Items");
        System.out.println();
        PrintUtil.printIndexAndText("4", "Quest Items");
        System.out.println();
        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
            }
            case 1 -> this.wearableItemsMenu(hero);
            case 2 -> this.itemInventoryMenu(hero, hero.getHeroInventory().returnInventoryCraftingReagentItemMap());
            case 3 -> this.itemInventoryMenu(hero, hero.getHeroInventory().returnInventoryConsumableItemMap());
            case 4 -> this.itemInventoryMenu(hero, hero.getHeroInventory().returnInventoryQuestItemMap());
            default -> PrintUtil.printEnterValidInput();
        }
    }

    public void wearableItemsMenu(Hero hero) {
        PrintUtil.printInventoryHeader("Wearable");
        PrintUtil.printIndexAndText("0", "Go back");
        System.out.println();
        PrintUtil.printIndexAndText("1", "Weapons (" + PrintUtil.printWearableItemCountByType(hero, WearableItemType.WEAPON) + ")");
        System.out.println();
        PrintUtil.printIndexAndText("2", "Body (" + PrintUtil.printWearableItemCountByType(hero, WearableItemType.BODY) + ")");
        System.out.println();
        PrintUtil.printIndexAndText("3", "Head (" + PrintUtil.printWearableItemCountByType(hero, WearableItemType.HEAD) + ")");
        System.out.println();
        PrintUtil.printIndexAndText("4", "Hands (" + PrintUtil.printWearableItemCountByType(hero, WearableItemType.HANDS) + ")");
        System.out.println();
        PrintUtil.printIndexAndText("5", "Boots (" + PrintUtil.printWearableItemCountByType(hero, WearableItemType.BOOTS) + ")");
        System.out.println();
        PrintUtil.printIndexAndText("6", "Wear off all equip");
        System.out.println();

        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> this.inventoryMenu(hero);
            case 1 -> printWearableItemInventoryMenuByItemType(WearableItemType.WEAPON, hero);
            case 2 -> printWearableItemInventoryMenuByItemType(WearableItemType.BODY, hero);
            case 3 -> printWearableItemInventoryMenuByItemType(WearableItemType.HEAD, hero);
            case 4 -> printWearableItemInventoryMenuByItemType(WearableItemType.HANDS, hero);
            case 5 -> printWearableItemInventoryMenuByItemType(WearableItemType.BOOTS, hero);
            case 6 -> hero.wearDownAllEquippedItems();
            default -> PrintUtil.printEnterValidInput();
        }
    }

    private void printWearableItemInventoryMenuByItemType(WearableItemType wearableItemType, Hero hero) {
        Map<WearableItem, Integer> tempMap = hero.getHeroInventory().returnInventoryWearableItemMap();

        tempMap.entrySet().removeIf(entry -> !entry.getKey().getWearableItemType().equals(wearableItemType));
        itemInventoryMenu(hero, tempMap);
    }

    public void printItemInventory(Hero hero, Map<? extends Item, Integer> items) {
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

    public boolean itemInventoryMenu(Hero hero, Map<? extends Item, Integer> items) {
        List<? extends Item> tempList = items.keySet().stream().toList();
        if (!tempList.isEmpty()) {
            PrintUtil.printInventoryHeader(tempList.getFirst().getClass().getSimpleName() + "Inventory");
        } else {
            PrintUtil.printInventoryHeader("Empty Inventory");
        }

        this.printItemInventory(hero, items);
        int choice = InputUtil.intScanner();
        try {
            if (choice == 0) {
                if (hero.isInCombat()) {
                    return false;
                } else {
                    this.inventoryMenu(hero);
                }
            } else {
                Item item = tempList.get(choice - 1);
                if (item instanceof UsableItem usableItem) {
                    return usableItem.useItem(hero);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            PrintUtil.printEnterValidInput();
        }

        return false;
    }
}
