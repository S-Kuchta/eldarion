package kuchtastefan.domain.vendor;

import kuchtastefan.domain.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.item.wearableItem.WearableItemType;
import kuchtastefan.utility.PrintUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        PrintUtil.printShopHeader(hero, "Blacksmith");
        this.printItems();
        super.buyItem(hero);
    }

    @Override
    public void printItemsForSale(Hero hero) {
        List<WearableItem> wearableItemList = new ArrayList<>();
        PrintUtil.printShopHeader(hero, "Blacksmith");
        int index = 1;
        System.out.println("\t0. Go back");
        if (hero.getHeroInventory().returnInventoryWearableItemMap().isEmpty()) {
            System.out.println("\tItem list is empty");
        } else {
            for (Map.Entry<WearableItem, Integer> item : hero.getHeroInventory().returnInventoryWearableItemMap().entrySet()) {
                wearableItemList.add(item.getKey());
                System.out.print("\t" + index + ". (" + item.getValue() + "x) ");
                PrintUtil.printItemAbilityPoints(item.getKey());
                System.out.println("\t\tSell price: " + super.returnSellItemPrice(item.getKey()));
                index++;
            }
        }

        super.sellItem(hero, wearableItemList);
    }

    @Override
    protected void printItems() {
        int index = 1;
        System.out.println("\t0. Go back");
        for (Item wearableItem : this.itemsForSale) {
            if (wearableItem instanceof WearableItem) {
                System.out.print("\t" + index + ". ");
                PrintUtil.printFullItemDescription((WearableItem) wearableItem);
                index++;
            }
        }
    }

    @Override
    public void printGreeting() {
        PrintUtil.printLongDivider();
        System.out.println("\tHello warrior, my name is " + this.name + " this is my blacksmith shop. \n\t" +
                "Let's look what I have here for you.");
        PrintUtil.printLongDivider();
    }
}
