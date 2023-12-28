package kuchtastefan.service;

import kuchtastefan.domain.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemType;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

import java.util.ArrayList;
import java.util.List;

public class ShopService {
    public void shopMenu(Hero hero, List<Item> itemList) {
        PrintUtil.printHeroGold(hero);
        System.out.println("0. Go back");
        System.out.println("1. Weapons");
        System.out.println("2. Body");
        System.out.println("3. Head");
        System.out.println("4. Hands");
        System.out.println("5. Boots");
        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
            }
            case 1 -> printInventoryMenuByItemType(itemList, hero, ItemType.WEAPON);
            case 2 -> printInventoryMenuByItemType(itemList, hero, ItemType.BODY);
            case 3 -> printInventoryMenuByItemType(itemList, hero, ItemType.HEAD);
            case 4 -> printInventoryMenuByItemType(itemList, hero, ItemType.HANDS);
            case 5 -> printInventoryMenuByItemType(itemList, hero, ItemType.BOOTS);
            default -> System.out.println("Enter valid number");
        }
    }

    private void printInventoryMenuByItemType(List<Item> itemList, Hero hero, ItemType itemType) {
        int index = 1;
        List<Item> tempList = new ArrayList<>();
        System.out.println("0. Go back");
        for (Item item : itemList) {
            if (item.getType() == itemType) {
                System.out.print(index + ". " + "Price: " + item.getPrice() + " golds, item level: " + item.getItemLevel() + ", ");
                PrintUtil.printItemAbilityStats(item);
                tempList.add(item);
                index++;
            }
        }

        while (true) {
            try {
                int choice = InputUtil.intScanner();
                if (choice == 0) {
                    shopMenu(hero, itemList);
                    break;
                }

                if (hero.getHeroGold() >= tempList.get(choice - 1).getPrice()) {
                    hero.getHeroInventory().add(tempList.get(choice - 1));
                    hero.setHeroGold(hero.getHeroGold() - tempList.get(choice - 1).getPrice());

                    System.out.println(tempList.get(choice - 1).getName() + " bought. You can find your item in inventory.");
                    System.out.println("Do you want to equip your new item?");
                    System.out.println("0. no");
                    System.out.println("1. yes");
                    int wearChoice = InputUtil.intScanner();
                    switch (wearChoice) {
                        case 0 -> {
                        }
                        case 1 -> hero.equipItem(tempList.get(choice - 1));
                    }

                    shopMenu(hero, itemList);
                    break;
                } else {
                    System.out.println("You don't have enough golds!");
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Enter valid number");
            }
        }
    }
}
