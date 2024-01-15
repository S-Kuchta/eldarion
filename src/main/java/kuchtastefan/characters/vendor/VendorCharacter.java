package kuchtastefan.characters.vendor;

import kuchtastefan.characters.GameCharacter;
import kuchtastefan.characters.hero.Hero;
import kuchtastefan.constant.Constant;
import kuchtastefan.items.Item;
import kuchtastefan.items.wearableItem.WearableItem;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import kuchtastefan.utility.RandomNumberGenerator;

import java.util.ArrayList;
import java.util.List;

public abstract class VendorCharacter extends GameCharacter {

    protected final List<? extends Item> itemsForSale;

    public VendorCharacter(String name, int level, List<? extends Item> itemsForSale) {
        super(name, level);
        this.itemsForSale = randomItemGeneratorForVendor(itemsForSale);
    }

    protected abstract void vendorOffer(Hero hero);

    public abstract void printItemsForSale(Hero hero);

    public abstract void printGreeting();

    protected abstract void printItems(Hero hero);

    public void vendorMenu(Hero hero) {
        printGreeting();
        System.out.println("\t0. Go back");
        System.out.println("\t1. Buy items");
        System.out.println("\t2. Sell items");


        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
            }
            case 1 -> vendorOffer(hero);
            case 2 -> printItemsForSale(hero);
            default -> System.out.println("Enter valid input");
        }
    }

    public List<Item> randomItemGeneratorForVendor(List<? extends Item> itemList) {
        List<Item> tempItemList = new ArrayList<>();
        List<Item> availableItems = new ArrayList<>(itemList);

        if (itemList instanceof WearableItem) {
            System.out.println(itemList.getClass().getSimpleName());
        }


        for (int i = 0; i < Constant.MAX_VENDOR_ITEMS_FOR_SELL && !availableItems.isEmpty(); i++) {
            int randomNum = RandomNumberGenerator.getRandomNumber(0, availableItems.size() - 1);
            tempItemList.add(availableItems.get(randomNum));
            availableItems.remove(randomNum);
        }

        return tempItemList;
    }

    protected void buyItem(Hero hero) {
        while (true) {
            int choice = InputUtil.intScanner();

            if (choice == 0) {
                vendorMenu(hero);
                break;
            }

            if (choice < 1 || choice > itemsForSale.size()) {
                System.out.println("\tEnter valid input");
            } else {
                Item item = itemsForSale.get(choice - 1);
                if (hero.getHeroGold() >= item.getPrice()) {
                    PrintUtil.printLongDivider();
                    System.out.println("\tAre you sure you want to buy " + item.getName());
                    System.out.println("\t0. no");
                    System.out.println("\t1. yes");
                    int confirmInput = InputUtil.intScanner();
                    switch (confirmInput) {
                        case 0 -> {
                        }
                        case 1 -> {
                            PrintUtil.printLongDivider();
                            successfullyItemBought(hero, item);
                            PrintUtil.printLongDivider();
                        }
                        default -> System.out.println("\tEnter valid input");
                    }
                    vendorMenu(hero);
                    return;
                } else {
                    System.out.println("\tYou don't have enough golds!");
                }
            }
        }
    }

    public void successfullyItemBought(Hero hero, Item item) {
        hero.getHeroInventory().addItemWithNewCopyToItemList(item);
        hero.checkHeroGoldsAndSubstractIfTrue(item.getPrice());
        System.out.println("\t" + item.getName() + " bought. You can find it in your inventory");
    }

    protected void sellItem(Hero hero, List<? extends Item> itemList) {

        while (true) {
            try {
                int choice = InputUtil.intScanner();
                if (choice == 0) {
                    vendorMenu(hero);
                } else {
                    Item item = itemList.get(choice - 1);
                    hero.addGolds(item.returnSellItemPrice());
                    if (item instanceof WearableItem) {
                        hero.unEquipItem((WearableItem) item);
                    }
                    hero.getHeroInventory().removeItemFromItemList(item);
                    System.out.println("\t" + item.getName() + " sold for " + item.returnSellItemPrice() + " golds");
                    vendorMenu(hero);
                }

                return;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("\tEnter valid input");
            }
        }
    }
}
