package kuchtastefan.domain.vendor;

import kuchtastefan.ability.Ability;
import kuchtastefan.domain.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.item.wearableItem.WearableItemType;
import kuchtastefan.utility.PrintUtil;

import java.util.*;

public class WearableItemVendorCharacter extends VendorCharacter {

    public WearableItemVendorCharacter(String name, int level, List<? extends Item> itemsForSale) {
        super(name, level, itemsForSale);
    }

    @Override
    public void vendorOffer(Hero hero) {

        // Item sort in array by WearableItemType
        this.itemsForSale.sort((item1, item2) -> {
            WearableItemType wearableItemType1 = ((WearableItem) item1).getWearableItemType();
            WearableItemType wearableItemType2 = ((WearableItem) item2).getWearableItemType();
            return wearableItemType1.compareTo(wearableItemType2);
        });

        this.printGreeting();
        PrintUtil.printShopHeader(hero, "Blacksmith");
        int index = 1;
        System.out.println("\t0. Go back");
        for (Item wearableItem : this.itemsForSale) {
            System.out.print("\t" + index + ". ");
            PrintUtil.printFullItemDescription((WearableItem) wearableItem);
            index++;
        }
        super.buyItem(hero);
    }

    @Override
    public void printItemsForSale(Hero hero) {
        List<WearableItem> tempItemList = new ArrayList<>();
        int index = 1;
        if (hero.getItemInventoryList().getHeroInventory().isEmpty()) {
            System.out.println("\tItem list is empty");
        } else {
            System.out.println("\t0. Go back");
            for (Map.Entry<WearableItem, Integer> item : hero.getItemInventoryList().returnInventoryWearableItemMap().entrySet()) {
                tempItemList.add(item.getKey());
                System.out.print("\t" + index + ". (" + item.getValue() + "x) ");
                PrintUtil.printItemAbilityStats(item.getKey());
                System.out.println("\t\tsell price: " + super.returnSellItemPrice(item.getKey()));
                index++;
            }
        }

        super.sellItem(hero, tempItemList);
    }

//    @Override
//    public void successfullyItemBought(Hero hero, Item item) {
//        hero.getItemInventoryList().addItemWithNewCopyToItemList(item);
//        hero.setHeroGold(hero.getHeroGold() - item.getPrice());
//        System.out.println(item.getName() + " bought. You can find it in your inventory");
//    }

    @Override
    public void printGreeting() {
        PrintUtil.printLongDivider();
        System.out.println("\tHello warrior, my name is " + this.name + " this is my blacksmith shop. \n\t" +
                "Let's look what I have here for you.");
        PrintUtil.printLongDivider();
    }
}
