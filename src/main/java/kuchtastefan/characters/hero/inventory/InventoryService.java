package kuchtastefan.characters.hero.inventory;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.items.consumeableItem.ConsumableItem;
import kuchtastefan.items.consumeableItem.ConsumableItemType;
import kuchtastefan.items.craftingItem.CraftingReagentItem;
import kuchtastefan.items.questItem.QuestItem;
import kuchtastefan.items.wearableItem.WearableItem;
import kuchtastefan.items.wearableItem.WearableItemType;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InventoryService {

    public void inventoryMenu(Hero hero) {
        PrintUtil.printDivider();
        System.out.println("\t\t------ " + hero.getName() + " Inventory ------");
        PrintUtil.printDivider();

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
            case 3 -> this.consumableItemsMenu(hero, false);
            case 4 -> this.questItemsMenu(hero);
            default -> System.out.println("\tEnter valid input");
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
            default -> System.out.println("\tEnter valid number");
        }
    }

    private void printWearableItemInventoryMenuByItemType(WearableItemType wearableItemType, Hero hero) {
        PrintUtil.printInventoryWearableItemTypeHeader(wearableItemType);
        int index = 1;
        List<WearableItem> tempList = new ArrayList<>();

        System.out.println("\t0. Go back");
        for (Map.Entry<WearableItem, Integer> item : hero.getHeroInventory().returnInventoryWearableItemMap().entrySet()) {
            if (item.getKey().getWearableItemType() == wearableItemType) {
                System.out.print("\t" + index + ". (" + item.getValue() + "x) ");
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
                System.out.println("\tEnter valid number");
            }
        }
    }

    public void craftingReagentsItemMenu(Hero hero) {
        int index = 1;
        PrintUtil.printInventoryHeader("\tCrafting reagents");
        System.out.println("\t0. Go back");
        for (Map.Entry<CraftingReagentItem, Integer> item : hero.getHeroInventory().returnInventoryCraftingReagentItemMap().entrySet()) {
            System.out.println("\t" + index + ". (" + item.getValue() + "x) " + item.getKey().getName());
        }

        int choice = InputUtil.intScanner();
        if (choice == 0) {
            this.inventoryMenu(hero);
        } else {
            System.out.println("\tEnter valid number");
        }
    }

    public boolean consumableItemsMenu(Hero hero, boolean isHeroInCombat) {
        PrintUtil.printInventoryHeader("Consumable");
        System.out.println("\t0. Go back");

        List<ConsumableItem> consumableItems = new ArrayList<>();
        int index = 1;
        for (Map.Entry<ConsumableItem, Integer> item : hero.getHeroInventory().returnInventoryConsumableItemMap().entrySet()) {
            if (isHeroInCombat) {
                if (item.getKey().getConsumableItemType().equals(ConsumableItemType.POTION)) {
                    System.out.print("\t" + index + ". (" + item.getValue() + "x) ");
                    PrintUtil.printConsumableItemInfo(item.getKey(), true);
                    consumableItems.add(item.getKey());
                    index++;
                }
            } else {
                System.out.print("\t" + index + ". (" + item.getValue() + "x) ");
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
                    consumableItems.get(choice - 1).performActions(hero);
                    hero.updateCurrentAbilitiesDependsOnActiveActionsAndIncreaseTurn(null);
                    if (!isHeroInCombat) {
                        consumableItemsMenu(hero, false);
                    } else {
                        return true;
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("\tEnter valid input");
                    this.consumableItemsMenu(hero, isHeroInCombat);
                }
            }
        }
    }

    public void questItemsMenu(Hero hero) {
        int index = 1;
        PrintUtil.printInventoryHeader("Quest");
        System.out.println("\t0. Go back");
        for (Map.Entry<QuestItem, Integer> item : hero.getHeroInventory().returnInventoryQuestItemMap().entrySet()) {
            System.out.println("\t" + index + ". (" + item.getValue() + "x) " + item.getKey().getName());
        }

        int choice = InputUtil.intScanner();
        if (choice == 0) {
            this.inventoryMenu(hero);
        } else {
            System.out.println("\tEnter valid number");
        }
    }

}
