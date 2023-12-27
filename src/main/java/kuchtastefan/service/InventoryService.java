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
        System.out.println("1. Weapons");
        System.out.println("2. Body");
        System.out.println("3. Head");
        System.out.println("4. Hands");
        System.out.println("5. Boots");
        System.out.println("6. Wear off all equip");
        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {}
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

        int choice = InputUtil.intScanner();
        if (choice == 0) {
            inventoryMenu(hero);
        }
        if (choice < 0 || choice > tempList.size()) {
            System.out.println("Enter valid number");
        } else {
            hero.equipItem(tempList.get(choice - 1));
            inventoryMenu(hero);
        }
    }

}
