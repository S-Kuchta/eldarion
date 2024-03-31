package kuchtastefan.character.npc.vendor;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShopService {

    public void vendorMenu(Hero hero, VendorCharacter vendorCharacter) {
        PrintUtil.printIndexAndText("0", "Go back");
        System.out.println();
        PrintUtil.printIndexAndText("1", "Buy items");
        System.out.println();
        PrintUtil.printIndexAndText("2", "Sell items");
        System.out.println();

        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
            }
            case 1 -> vendorOffer(hero, vendorCharacter);
            case 2 -> printHeroItemsForSale(hero, vendorCharacter);
            default -> PrintUtil.printEnterValidInput();
        }
    }

    private void vendorOffer(Hero hero, VendorCharacter vendorCharacter) {
        if (vendorCharacter instanceof SortVendorOffer sortVendorOffer) {
            sortVendorOffer.sortVendorOffer();
        }

        PrintUtil.printShopHeader(hero, vendorCharacter.returnItemClass().getSimpleName().replaceAll("\\d+", ""));
        vendorCharacter.printVendorItemsOffer(hero);
        buyItem(hero, vendorCharacter);
    }

    protected void buyItem(Hero hero, VendorCharacter vendorCharacter) {
        while (true) {
            int choice = InputUtil.intScanner();
            if (choice == 0) {
                vendorMenu(hero, vendorCharacter);
                break;
            }

            if (choice < 1 || choice > vendorCharacter.returnVendorOffer().size()) {
                PrintUtil.printEnterValidInput();
            } else {
                Item item = vendorCharacter.returnVendorOffer().get(choice - 1);
                if (hero.getHeroGold() >= item.getPrice()) {
                    PrintUtil.printLongDivider();
                    System.out.println("\tAre you sure you want to buy " + item.getName());
                    PrintUtil.printIndexAndText("0", "No");
                    System.out.println();
                    PrintUtil.printIndexAndText("1", "Yes");
                    System.out.println();

                    int confirmInput = InputUtil.intScanner();
                    switch (confirmInput) {
                        case 0 -> {
                        }
                        case 1 -> successfullyItemBought(hero, item);
                        default -> PrintUtil.printEnterValidInput();
                    }
                    vendorMenu(hero, vendorCharacter);
                    return;
                } else {
                    System.out.println("\tYou don't have enough golds!");
                }
            }
        }
    }

    public void successfullyItemBought(Hero hero, Item item) {
        hero.getHeroInventory().addItemWithNewCopyToItemList(item);
        hero.checkHeroGoldsAndSubtractIfHaveEnough(item.getPrice());
        System.out.println("\t" + ConsoleColor.YELLOW + item.getName() + ConsoleColor.RESET + " bought. You can find it in your inventory");
    }

    public void printHeroItemsForSale(Hero hero, VendorCharacter vendorCharacter) {
        List<Item> itemList = new ArrayList<>();

        PrintUtil.printShopHeader(hero, StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(
                vendorCharacter.returnItemClass().getSimpleName().replaceAll("\\d+", "")), " "));

        PrintUtil.printIndexAndText("0", "Go back");
        System.out.println();

        int index = 1;
        for (Map.Entry<Item, Integer> item : hero.getHeroInventory().getHeroInventory().entrySet()) {
            if (vendorCharacter.returnItemClass().isInstance(item.getKey())) {
                itemList.add(item.getKey());
                PrintUtil.printIndexAndText(String.valueOf(index), " (" + item.getValue() + "x) ");
                item.getKey().printItemDescription(hero);
                index++;
            }
        }

        if (itemList.isEmpty()) {
            System.out.println("\tItem list is empty\n");
        }

        sellItem(hero, itemList, vendorCharacter);
    }

    protected void sellItem(Hero hero, List<? extends Item> itemList, VendorCharacter vendorCharacter) {

        while (true) {
            try {
                int choice = InputUtil.intScanner();
                if (choice == 0) {
                    vendorMenu(hero, vendorCharacter);
                } else {
                    Item item = itemList.get(choice - 1);
                    hero.addGolds(item.returnSellItemPrice());
                    if (item instanceof WearableItem wearableItem) {
                        hero.unEquipItem(wearableItem);
                    }

                    hero.getHeroInventory().removeItemFromHeroInventory(item);
                    System.out.println("\t" + item.getName() + " sold for " + item.returnSellItemPrice() + " golds");
                    vendorMenu(hero, vendorCharacter);
                }

                return;
            } catch (IndexOutOfBoundsException e) {
                PrintUtil.printEnterValidInput();
            }
        }
    }
}
