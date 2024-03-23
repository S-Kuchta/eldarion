package kuchtastefan.character.npc.vendor;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.junkItem.JunkItem;
import kuchtastefan.utility.PrintUtil;

import java.util.List;

public class JunkVendorCharacter extends VendorCharacter {

    public JunkVendorCharacter(String name, int level, List<? extends Item> itemsForSale, Class<? extends Item> className) {
        super(name, level, itemsForSale, className);
    }

    @Override
    protected void vendorOffer(Hero hero) {
        PrintUtil.printShopHeader(hero, "Junk");
        this.printVendorItemsOffer(hero);
        super.buyItem(hero);
    }

    @Override
    protected void printVendorItemsOffer(Hero hero) {
        int index = 1;
        PrintUtil.printIndexAndText("0", "Go back");
        for (Item junkItem : this.vendorOffer) {
            if (junkItem instanceof JunkItem) {
                PrintUtil.printIndexAndText(String.valueOf(index), "");
                PrintUtil.printJunkItemInfo((JunkItem) junkItem, false);
                index++;
                System.out.println();
            }
        }
    }
}
