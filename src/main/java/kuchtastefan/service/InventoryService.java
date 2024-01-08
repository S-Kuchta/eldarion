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

    private final Hero hero;

    public InventoryService(Hero hero) {
        this.hero = hero;
    }

    public void inventoryMenu(Hero hero) {
        PrintUtil.printLongDivider();
        System.out.println("\t\t" + hero.getName() + " Inventory");
        PrintUtil.printLongDivider();

        System.out.println("\t0. Go back");
        System.out.println("\t1. Wearable Items");
        System.out.println("\t2. Crafting reagents");
        System.out.println("\t3. Consumable Items");
        System.out.println("\t4. Quest Items");
        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
            }
            case 1 -> this.wearableItemsMenu(hero);
            case 2 -> this.craftingReagentsItemMenu(hero);
            case 3 -> this.consumableItemsMenu(hero);
            case 4 -> this.questItemsMenu(hero);
            default -> System.out.println("Enter valid input");
        }
    }

    public void wearableItemsMenu(Hero hero) {

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
                this.inventoryMenu(hero);
            }
            case 1 -> printWearableItemInventoryMenuByItemType(WearableItemType.WEAPON, hero);
            case 2 -> printWearableItemInventoryMenuByItemType(WearableItemType.BODY, hero);
            case 3 -> printWearableItemInventoryMenuByItemType(WearableItemType.HEAD, hero);
            case 4 -> printWearableItemInventoryMenuByItemType(WearableItemType.HANDS, hero);
            case 5 -> printWearableItemInventoryMenuByItemType(WearableItemType.BOOTS, hero);
            case 6 -> hero.wearDownAllEquippedItems();
            default -> System.out.println("Enter valid number");
        }
    }

    private void printWearableItemInventoryMenuByItemType(WearableItemType wearableItemType, Hero hero) {
        PrintUtil.printInventoryHeader(wearableItemType);
        int index = 1;
        List<WearableItem> tempList = new ArrayList<>();

        System.out.println("\t0. Go back");
        for (Map.Entry<WearableItem, Integer> item : hero.getHeroInventory().returnInventoryWearableItemMap().entrySet()) {
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
                    wearableItemsMenu(hero);
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

    public void craftingReagentsItemMenu(Hero hero) {
        int index = 1;
        PrintUtil.printInventoryHeader("Crafting reagents");
        System.out.println("0. Go back");
        for (Map.Entry<CraftingReagentItem, Integer> item : hero.getHeroInventory().returnInventoryCraftingReagentItemMap().entrySet()) {
            System.out.println(index + ". (" + item.getValue() + "x) " + item.getKey().getName());
        }

        int choice = InputUtil.intScanner();
        if (choice == 0) {
            this.inventoryMenu(hero);
        } else {
            System.out.println("Enter valid number");
        }
    }

    public void consumableItemsMenu(Hero hero) {
//        int index = 1;
        PrintUtil.printInventoryHeader("Consumable");
        System.out.println("0. Go back");
        PrintUtil.printConsumableItemFromList(hero.getHeroInventory().returnInventoryConsumableItemMap());

        int choice = InputUtil.intScanner();
        if (choice == 0) {
            this.inventoryMenu(hero);
        } else {
            System.out.println("Enter valid number");
        }
    }

    public void questItemsMenu(Hero hero) {
        int index = 1;
        PrintUtil.printInventoryHeader("Quest");
        System.out.println("0. Go back");
        for (Map.Entry<QuestItem, Integer> item : hero.getHeroInventory().returnInventoryQuestItemMap().entrySet()) {
            System.out.println(index + ". (" + item.getValue() + "x) " + item.getKey().getName());
        }

        int choice = InputUtil.intScanner();
        if (choice == 0) {
            this.inventoryMenu(hero);
        } else {
            System.out.println("Enter valid number");
        }
    }

}
