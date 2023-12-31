package kuchtastefan.service;

import kuchtastefan.domain.Hero;
import kuchtastefan.item.wearableItem.wearableItem;
import kuchtastefan.item.wearableItem.wearableItemType;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

import java.util.ArrayList;
import java.util.List;

public class ShopService {
    public void shopMenu(Hero hero, List<wearableItem> wearableItemList) {
        PrintUtil.printMarketHeader(wearableItem.class.getSimpleName());
        System.out.println("\t0. Go back");
        System.out.println("\t1. Weapons");
        System.out.println("\t2. Body");
        System.out.println("\t3. Head");
        System.out.println("\t4. Hands");
        System.out.println("\t5. Boots");
        System.out.println("\t6. Sell item");
        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
            }
            case 1 -> printInventoryMenuByItemType(wearableItemList, hero, wearableItemType.WEAPON);
            case 2 -> printInventoryMenuByItemType(wearableItemList, hero, wearableItemType.BODY);
            case 3 -> printInventoryMenuByItemType(wearableItemList, hero, wearableItemType.HEAD);
            case 4 -> printInventoryMenuByItemType(wearableItemList, hero, wearableItemType.HANDS);
            case 5 -> printInventoryMenuByItemType(wearableItemList, hero, wearableItemType.BOOTS);
            case 6 -> sellItem(hero);
            default -> System.out.println("Enter valid number");
        }
    }

    private void printInventoryMenuByItemType(List<wearableItem> wearableItemList, Hero hero, wearableItemType wearableItemType) {
        PrintUtil.printShopHeader(hero, wearableItemType);
        int index = 1;
        List<wearableItem> tempList = new ArrayList<>();
        System.out.println("\t0. Go back");
        for (wearableItem wearableItem : wearableItemList) {
            if (wearableItem.getType() == wearableItemType) {
                System.out.print("\t" + index + ". ");
                PrintUtil.printItemAbilityStats(wearableItem);
                System.out.println("\t\t\tPrice: "
                        + wearableItem.getPrice()
                        + " golds, item level: "
                        + wearableItem.getItemLevel() + ", item quality: "
                        + wearableItem.getItemQuality());
                tempList.add(wearableItem);
                index++;
            }
        }

        while (true) {
            try {
                int choice = InputUtil.intScanner();
                if (choice == 0) {
                    shopMenu(hero, wearableItemList);
                    break;
                }

                wearableItem choosenWearableItem = tempList.get(choice - 1);

                if (hero.getHeroGold() >= choosenWearableItem.getPrice()) {
                    System.out.println("Are you sure you want to buy " + choosenWearableItem.getName());
                    System.out.println("0. no");
                    System.out.println("1. yes");
                    int confirmInput = InputUtil.intScanner();
                    switch (confirmInput) {
                        case 0 -> printInventoryMenuByItemType(wearableItemList, hero, wearableItemType);
                        case 1 -> {
                            hero.addItemWithNewCopyToItemList(choosenWearableItem);
                            hero.setHeroGold(hero.getHeroGold() - choosenWearableItem.getPrice());
                            System.out.println(choosenWearableItem.getName() + " bought. You can find it in your inventory.");

                            System.out.println("Do you want to equip your new item?");
                            System.out.println("0. no");
                            System.out.println("1. yes");
                            int wearChoice = InputUtil.intScanner();
                            switch (wearChoice) {
                                case 0 -> {
                                }
                                case 1 -> hero.equipItem(choosenWearableItem);
                            }
                        }
                    }

                    shopMenu(hero, wearableItemList);
                    break;
                } else {
                    System.out.println("You don't have enough golds!");
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Enter valid number");
            }
        }
    }

    private void sellItem(Hero hero) {
        int index = 1;
        System.out.println("0. Go back");
        for (wearableItem wearableItem : hero.getHeroInventory()) {
            System.out.println(index + ". " + wearableItem.getName());
            index++;
        }

        while (true) {
            try {
                int choice = InputUtil.intScanner();
                if (choice == 0) {
                    break;
                } else {
                    wearableItem tempWearableItem = hero.getHeroInventory().get(choice - 1);
                    double itemPrice = tempWearableItem.getPrice() * 0.7;
                    hero.setHeroGold((int) (hero.getHeroGold() + itemPrice));
                    hero.removeItemFromItemList(tempWearableItem);
                    System.out.println(tempWearableItem + " sold for " + itemPrice + " golds");
                }
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Enter valid number");
            }
        }

    }
}
