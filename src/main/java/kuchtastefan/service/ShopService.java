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
        PrintUtil.printMarketHeader(Item.class.getSimpleName());
        System.out.println("\t0. Go back");
        System.out.println("\t1. Weapons");
        System.out.println("\t2. Body");
        System.out.println("\t3. Head");
        System.out.println("\t4. Hands");
        System.out.println("\t5. Boots");
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
        PrintUtil.printShopHeader(hero, itemType);
        int index = 1;
        List<Item> tempList = new ArrayList<>();
        System.out.println("\t0. Go back");
        for (Item item : itemList) {
            if (item.getType() == itemType) {
                System.out.print("\t" + index + ". ");
                PrintUtil.printItemAbilityStats(item);
                System.out.println("\t\t\tPrice: "
                        + item.getPrice()
                        + " golds, item level: "
                        + item.getItemLevel() + ", item quality: "
                        + item.getItemQuality());
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

                Item choosenItem = tempList.get(choice - 1);

                if (hero.getHeroGold() >= choosenItem.getPrice()) {
                    System.out.println("Are you sure you want to buy " + choosenItem.getName());
                    System.out.println("0. no");
                    System.out.println("1. yes");
                    int confirmInput = InputUtil.intScanner();
                    switch (confirmInput) {
                        case 0 -> printInventoryMenuByItemType(itemList, hero, itemType);
                        case 1 -> {
                            hero.getHeroInventory().add(choosenItem);
                            hero.setHeroGold(hero.getHeroGold() - choosenItem.getPrice());
                            System.out.println(choosenItem.getName() + " bought. You can find it in your inventory.");

                            System.out.println("Do you want to equip your new item?");
                            System.out.println("0. no");
                            System.out.println("1. yes");
                            int wearChoice = InputUtil.intScanner();
                            switch (wearChoice) {
                                case 0 -> {
                                }
                                case 1 -> hero.equipItem(choosenItem);
                            }
                        }
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
