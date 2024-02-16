package kuchtastefan.characters.vendor;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.items.Item;
import kuchtastefan.items.wearableItem.WearableItem;
import kuchtastefan.items.wearableItem.WearableItemType;
import kuchtastefan.utility.PrintUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WearableItemVendorCharacter extends VendorCharacter {

    public WearableItemVendorCharacter(String name, int level, List<? extends Item> itemsForSale) {
        super(name, level, itemsForSale);
    }

    @Override
    protected void vendorOffer(Hero hero) {

        this.itemsForSale.sort((item1, item2) -> {
            WearableItemType wearableItemType1 = ((WearableItem) item1).getWearableItemType();
            WearableItemType wearableItemType2 = ((WearableItem) item2).getWearableItemType();
            return wearableItemType1.compareTo(wearableItemType2);
        });

        PrintUtil.printShopHeader(hero, "Blacksmith");
        this.printVendorItemsForSale(hero);
        super.buyItem(hero);
    }

    @Override
    protected void printVendorItemsForSale(Hero hero) {
        int index = 1;
        System.out.println("\t0. Go back");
        for (Item wearableItem : this.itemsForSale) {
            if (wearableItem instanceof WearableItem) {
                PrintUtil.printIndexAndText(String.valueOf(index), "");
                PrintUtil.printItemDescription((WearableItem) wearableItem, false, hero);
                index++;
            }
        }
    }

    @Override
    public void printHeroItemsForSale(Hero hero) {
        List<WearableItem> wearableItemList = new ArrayList<>();
        PrintUtil.printShopHeader(hero, "Blacksmith");
        int index = 1;
        System.out.println("\t0. Go back");
        PrintUtil.printIndexAndText("0", "Go back");
        if (hero.getHeroInventory().returnInventoryWearableItemMap().isEmpty()) {
            System.out.println("\tItem list is empty");
        } else {
            for (Map.Entry<WearableItem, Integer> item : hero.getHeroInventory().returnInventoryWearableItemMap().entrySet()) {
                wearableItemList.add(item.getKey());
                PrintUtil.printIndexAndText(String.valueOf(index), " (" + item.getValue() + "x) ");
                PrintUtil.printItemDescription(item.getKey(), true, hero);
                index++;
            }
        }

        super.sellItem(hero, wearableItemList);
    }
}
