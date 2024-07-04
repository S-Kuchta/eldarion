package kuchtastefan.service;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.hero.inventory.UsingHeroInventory;
import kuchtastefan.character.npc.vendor.VendorCharacter;
import kuchtastefan.character.npc.vendor.vendorOffer.SortVendorOffer;
import kuchtastefan.item.Item;
import kuchtastefan.item.itemFilter.ItemClassFilter;
import kuchtastefan.item.itemFilter.ItemFilter;
import kuchtastefan.item.itemFilter.ItemLevelFilter;
import kuchtastefan.item.itemFilter.ItemTypeFilter;
import kuchtastefan.item.specificItems.wearableItem.WearableItem;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.printUtil.PrintUtil;
import kuchtastefan.utility.printUtil.ShopPrint;

public class ShopService implements UsingHeroInventory {

    private final VendorCharacter vendorCharacter;

    public ShopService(VendorCharacter vendorCharacter) {
        this.vendorCharacter = vendorCharacter;
    }


    @Override
    public void mainMenu(Hero hero) {
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
            case 1 -> vendorOffer(hero);
            case 2 -> hero.getHeroInventoryManager().selectItem(hero, this,
                    new ItemFilter(
                            new ItemClassFilter(this.vendorCharacter.returnItemClass()),
                            new ItemTypeFilter(),
                            new ItemLevelFilter()));
            default -> PrintUtil.printEnterValidInput();
        }
    }

    @Override
    public boolean itemOptions(Hero hero, Item item) {
        PrintUtil.printMenuHeader(item.getName());
        PrintUtil.printMenuOptions("Go back", "Sell item");

        int choice = InputUtil.intScanner();
        if (choice == 0) {
            return false;
        } else if (choice == 1) {
            hero.addGolds(item.returnSellItemPrice());
            if (item instanceof WearableItem wearableItem && hero.isItemEquipped(wearableItem)) {
                hero.unEquipItem(wearableItem);
            }

            hero.getHeroInventoryManager().removeItem(item, 1);
            System.out.println("\t" + item.getName() + " sold for " + item.returnSellItemPrice() + " golds");
            return true;
        } else {
            PrintUtil.printEnterValidInput();
        }

        return false;
    }

    private void vendorOffer(Hero hero) {
        if (this.vendorCharacter instanceof SortVendorOffer sortVendorOffer) {
            sortVendorOffer.sortVendorOffer();
        }

        ShopPrint.printShopHeader(hero, this.vendorCharacter.returnItemClass().getSimpleName().replaceAll("\\d+", ""));
        this.vendorCharacter.printVendorItemsOffer(hero);
        buyItem(hero);
    }

    protected void buyItem(Hero hero) {
        while (true) {
            int choice = InputUtil.intScanner();
            if (choice == 0) {
                mainMenu(hero);
                break;
            }

            if (choice < 1 || choice > vendorCharacter.returnVendorOffer().size()) {
                PrintUtil.printEnterValidInput();
            } else {
                Item item = vendorCharacter.returnVendorOffer().get(choice - 1);
                if (hero.getHeroGold() >= item.getPrice()) {
                    confirmPurchase(hero, item);
                    mainMenu(hero);
                    return;
                } else {
                    System.out.println("\tYou don't have enough golds!");
                }
            }
        }
    }

    private void confirmPurchase(Hero hero, Item item) {
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
            case 1 -> {
                hero.getHeroInventoryManager().addItem(item, 1);
                hero.checkHeroGoldsAndSubtractIfHaveEnough(item.getPrice());
                System.out.println("\t" + ConsoleColor.YELLOW + item.getName() + ConsoleColor.RESET + " bought. You can find it in your inventory\n");
            }
            default -> PrintUtil.printEnterValidInput();
        }
    }
}
