package kuchtastefan.characters.vendor;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.items.Item;
import kuchtastefan.items.junkItem.JunkItem;
import kuchtastefan.utility.PrintUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JunkVendorCharacter extends VendorCharacter {

    public JunkVendorCharacter(String name, int level, List<? extends Item> itemsForSale) {
        super(name, level, itemsForSale);
    }

    @Override
    protected void vendorOffer(Hero hero) {
        PrintUtil.printShopHeader(hero, "Junk");
        this.printVendorItemsForSale(hero);
        super.buyItem(hero);
    }

    @Override
    protected void printVendorItemsForSale(Hero hero) {
        int index = 1;
        PrintUtil.printIndexAndText("0", "Go back");
        for (Item junkItem : this.itemsForSale) {
            if (junkItem instanceof JunkItem) {
                PrintUtil.printIndexAndText(String.valueOf(index), "");
                PrintUtil.printJunkItemInfo((JunkItem) junkItem, false);
                index++;
                System.out.println();
            }
        }
    }

    @Override
    public void printHeroItemsForSale(Hero hero) {
        List<JunkItem> junkItems = new ArrayList<>();
        PrintUtil.printShopHeader(hero, "Junk");
        int index = 1;
//        System.out.println("\t0. Go back");
        PrintUtil.printIndexAndText("0.", "Go back");
        System.out.println();
        if (hero.getHeroInventory().returnInventoryJunkItemMap().isEmpty()) {
            System.out.println("\tItem list is empty");
        } else {
            for (Map.Entry<JunkItem, Integer> item : hero.getHeroInventory().returnInventoryJunkItemMap().entrySet()) {
                junkItems.add(item.getKey());
                PrintUtil.printIndexAndText(String.valueOf(index), "");
                PrintUtil.printJunkItemInfo(item.getKey(), true);
                System.out.println();
                index++;
            }
        }
        super.sellItem(hero, junkItems);
    }
}
