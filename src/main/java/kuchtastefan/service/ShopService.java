package kuchtastefan.service;

import kuchtastefan.domain.Hero;
import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.item.wearableItem.WearableItemType;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

import java.util.ArrayList;
import java.util.List;

public class ShopService {
    public void shopMenu(Hero hero, List<WearableItem> wearableItemList) {
        PrintUtil.printMarketHeader("Equip");
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
            case 1 -> printInventoryMenuByItemType(wearableItemList, hero, WearableItemType.WEAPON);
            case 2 -> printInventoryMenuByItemType(wearableItemList, hero, WearableItemType.BODY);
            case 3 -> printInventoryMenuByItemType(wearableItemList, hero, WearableItemType.HEAD);
            case 4 -> printInventoryMenuByItemType(wearableItemList, hero, WearableItemType.HANDS);
            case 5 -> printInventoryMenuByItemType(wearableItemList, hero, WearableItemType.BOOTS);
            case 6 -> sellItem(hero);
            default -> System.out.println("Enter valid number");
        }
    }

    private void printInventoryMenuByItemType(List<WearableItem> wearableItemList, Hero hero, WearableItemType wearableItemType) {
        PrintUtil.printShopHeader(hero, wearableItemType);
        int index = 1;
        List<WearableItem> tempList = new ArrayList<>();
        System.out.println("\t0. Go back");
        for (WearableItem wearableItem : wearableItemList) {
            if (wearableItem.getType() == wearableItemType) {
                System.out.print("\t" + index + ". ");
                PrintUtil.printFullItemDescription(wearableItem);
                tempList.add(wearableItem);
                index++;
            }
        }


        while (true) {
            int choice = InputUtil.intScanner();

            if (choice == 0) {
                break;
            }

            if (choice < 1 || choice > tempList.size()) {
                System.out.println("Enter valid input");
            } else {
                WearableItem choosenWearableItem = tempList.get(choice - 1);
                if (hero.getHeroGold() >= choosenWearableItem.getPrice()) {
                    System.out.println("Are you sure you want to buy " + choosenWearableItem.getName());
                    System.out.println("0. no");
                    System.out.println("1. yes");
                    int confirmInput = InputUtil.intScanner();
                    switch (confirmInput) {
                        case 0 -> shopMenu(hero, wearableItemList);
                        case 1 -> {
                            hero.addItemWithNewCopyToItemList(choosenWearableItem);
                            hero.setHeroGold(hero.getHeroGold() - choosenWearableItem.getPrice());
                            System.out.println(choosenWearableItem.getName() + " bought. You can find it in your inventory");
                        }
                        default -> System.out.println("Enter valid input");
                    }
                    break;
                } else {
                    System.out.println("You don't have enough golds!");
                }
            }
        }
    }

    private void sellItem(Hero hero) {
        int index = 1;
        System.out.println("0. Go back");
        for (WearableItem wearableItem : hero.getHeroInventory()) {
            System.out.println(index + ". " + wearableItem.getName());
            index++;
        }

        while (true) {
            try {
                int choice = InputUtil.intScanner();
                if (choice == 0) {
                    break;
                } else {
                    WearableItem tempWearableItem = hero.getHeroInventory().get(choice - 1);
                    double itemPrice = tempWearableItem.getPrice() * 0.7;
                    hero.setHeroGold((hero.getHeroGold() + itemPrice));
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
