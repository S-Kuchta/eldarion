package kuchtastefan.character.npc.vendor;

import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.constant.Constant;
import kuchtastefan.item.Item;
import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import kuchtastefan.utility.RandomNumberGenerator;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public abstract class VendorCharacter extends GameCharacter {

    protected final List<? extends Item> vendorOffer;
    protected final Class<? extends Item> className;

    public VendorCharacter(String name, int level, List<? extends Item> vendorOffer, Class<? extends Item> className) {
        super(name, level);
        this.vendorOffer = randomItemGeneratorForVendor(vendorOffer);
        this.className = className;
    }

    protected abstract void vendorOffer(Hero hero);

    protected abstract void printVendorItemsOffer(Hero hero);

    public void vendorMenu(Hero hero) {
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
            case 2 -> printHeroItemsForSale(hero);
            default -> PrintUtil.printEnterValidInput();
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

            if (choice < 1 || choice > vendorOffer.size()) {
                PrintUtil.printEnterValidInput();
            } else {
                Item item = vendorOffer.get(choice - 1);
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
                        case 1 -> {
                            PrintUtil.printLongDivider();
                            successfullyItemBought(hero, item);
                            PrintUtil.printLongDivider();
                        }
                        default -> PrintUtil.printEnterValidInput();
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
        hero.checkHeroGoldsAndSubtractIfIsEnough(item.getPrice());
        System.out.println("\t" + ConsoleColor.YELLOW + item.getName() + ConsoleColor.RESET + " bought. You can find it in your inventory");
    }

    public void printHeroItemsForSale(Hero hero) {
        List<Item> itemList = new ArrayList<>();

        PrintUtil.printShopHeader(hero, StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(
                this.className.getSimpleName().replaceAll("\\d+", "")), " "));

        PrintUtil.printIndexAndText("0", "Go back");
        System.out.println();

        int index = 1;
        for (Map.Entry<Item, Integer> item : hero.getHeroInventory().getHeroInventory().entrySet()) {
            if (this.className.isInstance(item.getKey())) {
                itemList.add(item.getKey());
                PrintUtil.printIndexAndText(String.valueOf(index), " (" + item.getValue() + "x) ");
                item.getKey().printItemDescription(hero);
                index++;
            }
        }

        if (itemList.isEmpty()) {
            System.out.println("\tItem list is empty\n");
        }

        sellItem(hero, itemList);
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

                    hero.getHeroInventory().removeItemFromHeroInventory(item);
                    System.out.println("\t" + item.getName() + " sold for " + item.returnSellItemPrice() + " golds");
                    vendorMenu(hero);
                }

                return;
            } catch (IndexOutOfBoundsException e) {
                PrintUtil.printEnterValidInput();
            }
        }
    }
}
