package kuchtastefan.service;

import kuchtastefan.domain.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.item.wearableItem.WearableItemType;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InventoryService {

    public void inventoryMenu(Hero hero) {

        PrintUtil.printInventoryHeader("Equip");

        System.out.println("\t0. Go back");
        System.out.println("\t1. Weapons (" + PrintUtil.printItemCountByType(hero, WearableItemType.WEAPON) + ")");
        System.out.println("\t2. Body (" + PrintUtil.printItemCountByType(hero, WearableItemType.BODY) + ")");
        System.out.println("\t3. Head (" + PrintUtil.printItemCountByType(hero, WearableItemType.HEAD) + ")");
        System.out.println("\t4. Hands (" + PrintUtil.printItemCountByType(hero, WearableItemType.HANDS) + ")");
        System.out.println("\t5. Boots (" + PrintUtil.printItemCountByType(hero, WearableItemType.BOOTS) + ")");
        System.out.println("\t6. Wear off all equip");
        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
            }
            case 1 -> printInventoryMenuByItemType(hero, WearableItemType.WEAPON);
            case 2 -> printInventoryMenuByItemType(hero, WearableItemType.BODY);
            case 3 -> printInventoryMenuByItemType(hero, WearableItemType.HEAD);
            case 4 -> printInventoryMenuByItemType(hero, WearableItemType.HANDS);
            case 5 -> printInventoryMenuByItemType(hero, WearableItemType.BOOTS);
            case 6 -> hero.wearDownAllEquippedItems();
            default -> System.out.println("Enter valid number");
        }
    }

    private void printInventoryMenuByItemType(Hero hero, WearableItemType wearableItemType) {
        PrintUtil.printInventoryHeader(wearableItemType);
        int index = 1;
        List<WearableItem> tempList = new ArrayList<>();

        System.out.println("\t0. Go back");
        for (Map.Entry<Item, Integer> itemMap : hero.getHeroInventory().entrySet()) {
            WearableItem wearableItem = (WearableItem) itemMap.getKey();
            if (wearableItem.getType() == wearableItemType) {
                System.out.print("\t" + index + ". (" + itemMap.getValue() + "x) ");
                PrintUtil.printItemAbilityStats(wearableItem);
                tempList.add(wearableItem);
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

}
