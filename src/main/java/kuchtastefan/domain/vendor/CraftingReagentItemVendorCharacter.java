package kuchtastefan.domain.vendor;

import kuchtastefan.domain.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.craftingItem.CraftingReagentItem;
import kuchtastefan.item.craftingItem.CraftingReagentItemType;
import kuchtastefan.utility.PrintUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CraftingReagentItemVendorCharacter extends VendorCharacter {

    public CraftingReagentItemVendorCharacter(String name, int level, List<? extends Item> itemsForSale) {
        super(name, level, itemsForSale);
    }

    @Override
    public void vendorOffer(Hero hero) {
        this.itemsForSale.sort((item1, item2) -> {
            CraftingReagentItemType craftingReagentItemType = ((CraftingReagentItem) item1).getCraftingReagentItemType();
            CraftingReagentItemType craftingReagentItemType1 = ((CraftingReagentItem) item2).getCraftingReagentItemType();
            return craftingReagentItemType.compareTo(craftingReagentItemType1);
        });

        PrintUtil.printShopHeader(hero, "Crafting reagents");
        this.printItems();
        super.buyItem(hero);
    }

    @Override
    protected void printItems() {
        int index = 1;
        System.out.println("\t0. Go back");
        for (Item craftingReagentItem : this.itemsForSale) {
            if (craftingReagentItem instanceof CraftingReagentItem) {
                System.out.print("\t" + index + ". " + craftingReagentItem.getName()
                        + ", Item Type: " + ((CraftingReagentItem) craftingReagentItem).getCraftingReagentItemType()
                        + ", Item price: " + craftingReagentItem.getPrice() + " golds");
                index++;
                System.out.println();
            }
        }
    }

    @Override
    public void printGreeting() {

    }

    @Override
    public void printItemsForSale(Hero hero) {
        List<CraftingReagentItem> craftingReagentItems = new ArrayList<>();
        PrintUtil.printShopHeader(hero, "Crafting reagents");
        int index = 1;
        System.out.println("\t0. Go back");
        if (hero.getHeroInventory().returnInventoryCraftingReagentItemMap().isEmpty()) {
            System.out.println("\tItem list is empty");
        } else {
            for (Map.Entry<CraftingReagentItem, Integer> item : hero.getHeroInventory().returnInventoryCraftingReagentItemMap().entrySet()) {
                craftingReagentItems.add(item.getKey());
                System.out.print("\t" + index + ". (" + item.getValue() + "x) ");
                PrintUtil.printCraftingReagentItemInfo(item.getKey());
                System.out.println("\n\t\tSell price: " + super.returnSellItemPrice(item.getKey()));
            }
        }
        super.sellItem(hero, craftingReagentItems);
    }
}
