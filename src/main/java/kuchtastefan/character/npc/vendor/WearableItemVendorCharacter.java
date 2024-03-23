package kuchtastefan.character.npc.vendor;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.item.wearableItem.WearableItemType;
import kuchtastefan.utility.PrintUtil;

import java.util.List;

public class WearableItemVendorCharacter extends VendorCharacter {

    public WearableItemVendorCharacter(String name, int level, List<? extends Item> itemsForSale, Class<? extends Item> className) {
        super(name, level, itemsForSale, className);
    }

    @Override
    protected void vendorOffer(Hero hero) {

        this.vendorOffer.sort((item1, item2) -> {
            WearableItemType wearableItemType1 = ((WearableItem) item1).getWearableItemType();
            WearableItemType wearableItemType2 = ((WearableItem) item2).getWearableItemType();
            return wearableItemType1.compareTo(wearableItemType2);
        });

        PrintUtil.printShopHeader(hero, "Blacksmith");
        this.printVendorItemsOffer(hero);
        super.buyItem(hero);
    }

    @Override
    protected void printVendorItemsOffer(Hero hero) {
        int index = 1;
        System.out.println("\t0. Go back");
        for (Item wearableItem : this.vendorOffer) {
            if (wearableItem instanceof WearableItem) {
                PrintUtil.printIndexAndText(String.valueOf(index), "");
                PrintUtil.printItemDescription((WearableItem) wearableItem, false, hero);
                index++;
            }
        }
    }
}
