package kuchtastefan.domain.vendor;

import kuchtastefan.ability.Ability;
import kuchtastefan.constant.Constant;
import kuchtastefan.domain.GameCharacter;
import kuchtastefan.domain.Hero;
import kuchtastefan.item.Item;

import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.RandomNumberGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class VendorCharacter extends GameCharacter {

    protected final List<? extends Item> itemsForSale;

    public VendorCharacter(String name, Map<Ability, Integer> abilities, List<? extends Item> itemList) {
        super(name, abilities);
        this.itemsForSale = randomItemGeneratorForVendor(itemList);
    }

    public abstract void vendorOffer(Hero hero);

    public abstract void successfullyItemBought(Hero hero, Item item);

    public abstract void printItemsForSale(Hero hero);

    public abstract void printGreeting();

    public List<Item> randomItemGeneratorForVendor(List<? extends Item> itemList) {
        List<Item> tempItemList = new ArrayList<>();
        System.out.println("Item list size: " + itemList.size());
        for (int i = 0; i < Constant.MAX_VENDOR_ITEMS_FOR_SELL; i++) {
            int randomNum = RandomNumberGenerator.getRandomNumber(0, itemList.size() - 1);
            System.out.println(randomNum);
            tempItemList.add(itemList.get(randomNum));
        }
        return tempItemList;
    }

    protected void buyItem(Hero hero) {
        while (true) {
            int choice = InputUtil.intScanner();

            if (choice == 0) {
                break;
            }

            if (choice < 1 || choice > itemsForSale.size()) {
                System.out.println("Enter valid input");
            } else {
                Item item = itemsForSale.get(choice - 1);
                if (hero.getHeroGold() >= item.getPrice()) {
                    System.out.println("Are you sure you want to buy " + item.getName());
                    System.out.println("0. no");
                    System.out.println("1. yes");
                    int confirmInput = InputUtil.intScanner();
                    switch (confirmInput) {
                        case 0 -> {
                            return;
                        }
                        case 1 -> successfullyItemBought(hero, item);
                        default -> System.out.println("Enter valid input");
                    }
                    break;
                } else {
                    System.out.println("You don't have enough golds!");
                }
            }
        }
    }

    protected double returnSellItemPrice(Item item) {
        return item.getPrice() * 0.7;
    }

    protected void sellItem(Hero hero, List<? extends Item> itemList) {
        while (true) {
            try {
                int choice = InputUtil.intScanner();
                if (choice == 0) {
                    break;
                } else {
                    Item item = itemList.get(choice - 1);
                    double itemPrice = returnSellItemPrice(item);
                    hero.setHeroGold((hero.getHeroGold() + itemPrice));
                    hero.removeItemFromItemList(item);
                    System.out.println(item + " sold for " + itemPrice + " golds");
                }
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Enter valid number");
            }
        }
    }
}
