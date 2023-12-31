package kuchtastefan.service;

import kuchtastefan.domain.Hero;
import kuchtastefan.item.wearableItem.wearableItem;
import kuchtastefan.item.wearableItem.wearableItemType;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

import java.util.ArrayList;
import java.util.List;

public class InventoryService {

    public void inventoryMenu(Hero hero) {

        PrintUtil.printInventoryHeader(wearableItem.class.getSimpleName());
        System.out.println("\t0. Go back");
        System.out.println("\t1. Weapons (" + PrintUtil.printItemCountByType(hero, wearableItemType.WEAPON) + ")");
        System.out.println("\t2. Body (" + PrintUtil.printItemCountByType(hero, wearableItemType.BODY) + ")");
        System.out.println("\t3. Head (" + PrintUtil.printItemCountByType(hero, wearableItemType.HEAD) + ")");
        System.out.println("\t4. Hands (" + PrintUtil.printItemCountByType(hero, wearableItemType.HANDS) + ")");
        System.out.println("\t5. Boots (" + PrintUtil.printItemCountByType(hero, wearableItemType.BOOTS) + ")");
        System.out.println("\t6. Wear off all equip");
        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
                break;
            }
            case 1 -> printInventoryMenuByItemType(hero, wearableItemType.WEAPON);
            case 2 -> printInventoryMenuByItemType(hero, wearableItemType.BODY);
            case 3 -> printInventoryMenuByItemType(hero, wearableItemType.HEAD);
            case 4 -> printInventoryMenuByItemType(hero, wearableItemType.HANDS);
            case 5 -> printInventoryMenuByItemType(hero, wearableItemType.BOOTS);
            case 6 -> hero.wearDownAllEquippedItems();
            default -> System.out.println("Enter valid number");
        }
    }

    private void printInventoryMenuByItemType(Hero hero, wearableItemType wearableItemType) {
        PrintUtil.printInventoryHeader(wearableItemType);
        int index = 1;
        List<wearableItem> tempList = new ArrayList<>();
        System.out.println("\t0. Go back");
        for (wearableItem wearableItem : hero.getHeroInventory()) {
            if (wearableItem.getType() == wearableItemType) {
                System.out.print("\t" + index + ". ");
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
