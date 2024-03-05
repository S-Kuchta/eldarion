package kuchtastefan.character.hero.inventory;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.consumeableItem.ConsumableItem;
import kuchtastefan.item.consumeableItem.ConsumableItemType;
import kuchtastefan.item.craftingItem.CraftingReagentItem;
import kuchtastefan.item.questItem.QuestItem;
import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.item.wearableItem.WearableItemType;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

import java.util.ArrayList;
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
            case 2 -> this.craftingReagentsItemMenu(hero);
            case 3 -> this.consumableItemsMenu(hero, false);
            case 4 -> this.questItemsMenu(hero);
            default -> PrintUtil.printEnterValidInput();
        }
    }

    public void wearableItemsMenu(Hero hero) {

        PrintUtil.printInventoryHeader("Wearable");
        PrintUtil.printIndexAndText("0", "Go back");
        System.out.println();

        PrintUtil.printIndexAndText("1", "Weapons ("
                + PrintUtil.printWearableItemCountByType(hero, WearableItemType.WEAPON) + ")");
        System.out.println();

        PrintUtil.printIndexAndText("2", "Body ("
                + PrintUtil.printWearableItemCountByType(hero, WearableItemType.BODY) + ")");
        System.out.println();

        PrintUtil.printIndexAndText("3", "Head ("
                + PrintUtil.printWearableItemCountByType(hero, WearableItemType.HEAD) + ")");
        System.out.println();

        PrintUtil.printIndexAndText("4", "Hands ("
                + PrintUtil.printWearableItemCountByType(hero, WearableItemType.HANDS) + ")");
        System.out.println();

        PrintUtil.printIndexAndText("5", "Boots ("
                + PrintUtil.printWearableItemCountByType(hero, WearableItemType.BOOTS) + ")");
        System.out.println();

        PrintUtil.printIndexAndText("6", "Wear off all equip");
        System.out.println();

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
            default -> PrintUtil.printEnterValidInput();
        }
    }

    private void printWearableItemInventoryMenuByItemType(WearableItemType wearableItemType, Hero hero) {
        PrintUtil.printInventoryWearableItemTypeHeader(wearableItemType);
        int index = 1;
        List<WearableItem> tempList = new ArrayList<>();

        PrintUtil.printIndexAndText("0", "Go back");
        System.out.println();

        for (Map.Entry<WearableItem, Integer> item : hero.getHeroInventory().returnInventoryWearableItemMap().entrySet()) {
            if (item.getKey().getWearableItemType() == wearableItemType) {
                PrintUtil.printIndexAndText(String.valueOf(index), "(" + item.getValue() + "x) ");
                PrintUtil.printItemDescription(item.getKey(), true, hero);

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
                wearableItemsMenu(hero);
                break;
            } catch (IndexOutOfBoundsException e) {
                PrintUtil.printEnterValidInput();
            }
        }
    }

    public void craftingReagentsItemMenu(Hero hero) {
        int index = 1;
        PrintUtil.printInventoryHeader("\tCrafting reagents");
        PrintUtil.printIndexAndText("0", "Go back");
        System.out.println();
        for (Map.Entry<CraftingReagentItem, Integer> item : hero.getHeroInventory().returnInventoryCraftingReagentItemMap().entrySet()) {
            PrintUtil.printIndexAndText(String.valueOf(index), "(" + item.getValue() + "x) ");
            PrintUtil.printCraftingReagentItemInfo(item.getKey(), true);
        }

        int choice = InputUtil.intScanner();
        if (choice == 0) {
            this.inventoryMenu(hero);
        } else {
            PrintUtil.printEnterValidInput();
        }
    }

    public boolean consumableItemsMenu(Hero hero, boolean isHeroInCombat) {
        PrintUtil.printInventoryHeader("Consumable");
        PrintUtil.printIndexAndText("0", "Go back");
        System.out.println();

        List<ConsumableItem> consumableItems = new ArrayList<>();
        int index = 1;
        for (Map.Entry<ConsumableItem, Integer> item : hero.getHeroInventory().returnInventoryConsumableItemMap().entrySet()) {
            if (isHeroInCombat) {
                if (item.getKey().getConsumableItemType().equals(ConsumableItemType.POTION)) {
                    PrintUtil.printIndexAndText(String.valueOf(index), "(" + item.getValue() + "x) ");
                    PrintUtil.printConsumableItemInfo(item.getKey(), true);
                    consumableItems.add(item.getKey());
                    index++;
                }
            } else {
                PrintUtil.printIndexAndText(String.valueOf(index), "(" + item.getValue() + "x) ");
                PrintUtil.printConsumableItemInfo(item.getKey(), true);
                consumableItems.add(item.getKey());
                System.out.println();
                index++;
            }
        }

        while (true) {
            int choice = InputUtil.intScanner();
            if (choice == 0) {
                return false;
            } else {
                try {
                    consumableItems.get(choice - 1).useItem(hero);
                    if (!isHeroInCombat) {
                        consumableItemsMenu(hero, false);
                    } else {
                        return true;
                    }
                } catch (IndexOutOfBoundsException e) {
                    PrintUtil.printEnterValidInput();
                    this.consumableItemsMenu(hero, isHeroInCombat);
                }
            }
        }
    }

    public void questItemsMenu(Hero hero) {
        int index = 1;
        PrintUtil.printInventoryHeader("Quest");
        PrintUtil.printIndexAndText("0", "Go back");
        System.out.println();

        for (Map.Entry<QuestItem, Integer> item : hero.getHeroInventory().returnInventoryQuestItemMap().entrySet()) {
            PrintUtil.printIndexAndText(item.toString(), "(" + item.getValue() + "x) " + item.getKey().getName());
        }

        int choice = InputUtil.intScanner();
        if (choice == 0) {
            this.inventoryMenu(hero);
        } else {
            PrintUtil.printEnterValidInput();
        }
    }

}
