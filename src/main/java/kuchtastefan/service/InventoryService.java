package kuchtastefan.service;

import kuchtastefan.domain.Hero;
import kuchtastefan.item.craftingItem.CraftingReagentItem;
import kuchtastefan.item.questItem.QuestItem;
import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.item.wearableItem.WearableItemType;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InventoryService {

    public void inventoryMenu(Hero hero) {

        PrintUtil.printInventoryHeader("Wearable");

        System.out.println("\t0. Go back");
        System.out.println("\t1. Weapons (" + PrintUtil.printWearableItemCountByType(hero, WearableItemType.WEAPON) + ")");
        System.out.println("\t2. Body (" + PrintUtil.printWearableItemCountByType(hero, WearableItemType.BODY) + ")");
        System.out.println("\t3. Head (" + PrintUtil.printWearableItemCountByType(hero, WearableItemType.HEAD) + ")");
        System.out.println("\t4. Hands (" + PrintUtil.printWearableItemCountByType(hero, WearableItemType.HANDS) + ")");
        System.out.println("\t5. Boots (" + PrintUtil.printWearableItemCountByType(hero, WearableItemType.BOOTS) + ")");
        System.out.println("\t6. Wear off all equip");
        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
            }
            case 1 -> printWearableItemInventoryMenuByItemType(hero, WearableItemType.WEAPON);
            case 2 -> printWearableItemInventoryMenuByItemType(hero, WearableItemType.BODY);
            case 3 -> printWearableItemInventoryMenuByItemType(hero, WearableItemType.HEAD);
            case 4 -> printWearableItemInventoryMenuByItemType(hero, WearableItemType.HANDS);
            case 5 -> printWearableItemInventoryMenuByItemType(hero, WearableItemType.BOOTS);
            case 6 -> hero.wearDownAllEquippedItems();
            default -> System.out.println("Enter valid number");
        }
    }

    private void printWearableItemInventoryMenuByItemType(Hero hero, WearableItemType wearableItemType) {
        PrintUtil.printInventoryHeader(wearableItemType);
        int index = 1;
        List<WearableItem> tempList = new ArrayList<>();

        System.out.println("\t0. Go back");
        for (Map.Entry<WearableItem, Integer> item : hero.getItemInventoryList().returnInventoryWearableItemMap().entrySet()) {
            if (item.getKey().getWearableItemType() == wearableItemType) {
                System.out.print("\t" + index + ". (" + item.getValue() + "x) ");
                PrintUtil.printItemAbilityPoints(item.getKey());
                tempList.add(item.getKey());
                index++;
            }
        }

        while (true) {
            try {
                int choice = InputUtil.intScanner();
                if (choice == 0) {
                    inventoryMenu(hero);
                    break;
                }

                hero.equipItem(tempList.get(choice - 1));
                inventoryMenu(hero);
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Enter valid number");
            }
        }
    }

    public void craftingReagentsMenu(Hero hero) {
        int index = 1;
        PrintUtil.printInventoryHeader("Crafting reagents");
        System.out.println("0. Go back");
        for (Map.Entry<CraftingReagentItem, Integer> item : hero.getItemInventoryList().returnInventoryCraftingReagentItemMap().entrySet()) {
            System.out.println(index + ". (" + item.getValue() + "x) " + item.getKey().getName());
        }

        int choice = InputUtil.intScanner();
        if (choice == 0) {
            return;
        } else {
            System.out.println("Enter valid number");
        }
    }

    public void consumableItemsMenu(Hero hero) {
//        int index = 1;
        PrintUtil.printInventoryHeader("Consumable");
        System.out.println("0. Go back");
        PrintUtil.printConsumableItemFromList(hero.getItemInventoryList().returnInventoryConsumableItemMap());

        int choice = InputUtil.intScanner();
        if (choice == 0) {
            return;
        } else {
            System.out.println("Enter valid number");
        }
    }

    public void questItemsMenu(Hero hero) {
        int index = 1;
        PrintUtil.printInventoryHeader("Quest");
        System.out.println("0. Go back");
        for (Map.Entry<QuestItem, Integer> item : hero.getItemInventoryList().returnInventoryQuestItemMap().entrySet()) {
            System.out.println(index + ". (" + item.getValue() + "x) " + item.getKey().getName());
        }

        int choice = InputUtil.intScanner();
        if (choice == 0) {
            return;
        } else {
            System.out.println("Enter valid number");
        }
    }

}
