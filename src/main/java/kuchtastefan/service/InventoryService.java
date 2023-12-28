package kuchtastefan.service;

import kuchtastefan.domain.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemType;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

import java.util.ArrayList;
import java.util.List;

public class InventoryService {

    public void inventoryMenu(Hero hero) {
        System.out.println("0. Go back");
        System.out.println("1. Weapons (" + PrintUtil.printItemCountByType(hero, ItemType.WEAPON) + ")");
        System.out.println("2. Body (" + PrintUtil.printItemCountByType(hero, ItemType.BODY) + ")");
        System.out.println("3. Head (" + PrintUtil.printItemCountByType(hero, ItemType.HEAD) + ")");
        System.out.println("4. Hands (" + PrintUtil.printItemCountByType(hero, ItemType.HANDS) + ")");
        System.out.println("5. Boots (" + PrintUtil.printItemCountByType(hero, ItemType.BOOTS) + ")");
        System.out.println("6. Wear off all equip");
        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
                break;
            }
            case 1 -> printInventoryMenuByItemType(hero, ItemType.WEAPON);
            case 2 -> printInventoryMenuByItemType(hero, ItemType.BODY);
            case 3 -> printInventoryMenuByItemType(hero, ItemType.HEAD);
            case 4 -> printInventoryMenuByItemType(hero, ItemType.HANDS);
            case 5 -> printInventoryMenuByItemType(hero, ItemType.BOOTS);
            case 6 -> hero.wearDownAllItems();
            default -> System.out.println("Enter valid number");
        }
    }

    private void printInventoryMenuByItemType(Hero hero, ItemType itemType) {
        int index = 1;
        List<Item> tempList = new ArrayList<>();
        System.out.println("0. Go back");
        for (Item item : hero.getHeroInventory()) {
            if (item.getType() == itemType) {
                System.out.print(index + ". ");
                PrintUtil.printItemAbilityStats(item);
                tempList.add(item);
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
