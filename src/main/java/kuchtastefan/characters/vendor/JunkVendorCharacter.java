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
        this.printItems(hero);
        super.buyItem(hero);
    }

    @Override
    public void printItemsForSale(Hero hero) {
        List<JunkItem> junkItems = new ArrayList<>();
        PrintUtil.printShopHeader(hero, "Junk");
        int index = 1;
        System.out.println("\t0. Go back");
        if (hero.getHeroInventory().returnInventoryJunkItemMap().isEmpty()) {
            System.out.println("\tItem list is empty");
        } else {
            for (Map.Entry<JunkItem, Integer> item : hero.getHeroInventory().returnInventoryJunkItemMap().entrySet()) {
                junkItems.add(item.getKey());
                System.out.print("\t" + index + ". (" + item.getValue() + "x) " + item.getKey().getName());
                System.out.println("\n\t\tSell price: " + item.getKey().returnSellItemPrice());
            }
        }
        super.sellItem(hero, junkItems);
    }

    @Override
    public void printGreeting() {

    }

    @Override
    protected void printItems(Hero hero) {
        int index = 1;
        System.out.println("\t0. Go back");
        for (Item junkItem : this.itemsForSale) {
            if (junkItem instanceof JunkItem) {
                System.out.print("\t" + index + ". " + junkItem.getName()
                        + ", Item price: " + junkItem.getPrice() + " golds");
                index++;
                System.out.println();
            }
        }
    }
}
