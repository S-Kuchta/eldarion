package kuchtastefan.domain.vendor;

import kuchtastefan.ability.Ability;
import kuchtastefan.constant.Constant;
import kuchtastefan.domain.GameCharacter;
import kuchtastefan.domain.Hero;
import kuchtastefan.item.Item;

import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.RandomNumberGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Vendor extends GameCharacter {

    protected final List<Item> itemsForSale;

    public Vendor(String name, Map<Ability, Integer> abilities, List<? extends Item> itemList) {
        super(name, abilities);
        this.itemsForSale = randomItemGeneratorForVendor(itemList);
    }

    public abstract void vendorOffer(Hero hero);
    public abstract void successfullyItemBought(Hero hero, Item item);

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

    void sellItem(Hero hero) {
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
