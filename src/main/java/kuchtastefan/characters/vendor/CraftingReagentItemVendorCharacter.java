package kuchtastefan.characters.vendor;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.items.Item;
import kuchtastefan.items.craftingItem.CraftingReagentItem;
import kuchtastefan.items.craftingItem.CraftingReagentItemType;
import kuchtastefan.utility.PrintUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CraftingReagentItemVendorCharacter extends VendorCharacter {

    public CraftingReagentItemVendorCharacter(String name, int level, List<? extends Item> itemsForSale) {
        super(name, level, itemsForSale);
    }

    @Override
    protected void vendorOffer(Hero hero) {
        this.itemsForSale.sort((item1, item2) -> {
            CraftingReagentItemType craftingReagentItemType = ((CraftingReagentItem) item1).getCraftingReagentItemType();
            CraftingReagentItemType craftingReagentItemType1 = ((CraftingReagentItem) item2).getCraftingReagentItemType();
            return craftingReagentItemType.compareTo(craftingReagentItemType1);
        });

        PrintUtil.printShopHeader(hero, "Crafting reagents");
        this.printVendorItemsForSale(hero);
        super.buyItem(hero);
    }

    @Override
    protected void printVendorItemsForSale(Hero hero) {
        int index = 1;
        PrintUtil.printIndexAndText("0", "Go back");
        System.out.println();
        for (Item craftingReagentItem : this.itemsForSale) {
            if (craftingReagentItem instanceof CraftingReagentItem) {
                PrintUtil.printIndexAndText(String.valueOf(index), "");
                PrintUtil.printCraftingReagentItemInfo((CraftingReagentItem) craftingReagentItem, false);
                System.out.println();
                index++;
            }
        }
    }

    @Override
    public void printGreeting() {

    }

    @Override
    public void printHeroItemsForSale(Hero hero) {
        List<CraftingReagentItem> craftingReagentItems = new ArrayList<>();

        PrintUtil.printShopHeader(hero, "Crafting reagents");
        int index = 1;

        PrintUtil.printIndexAndText("0", "Go back");
        System.out.println();
        if (hero.getHeroInventory().returnInventoryCraftingReagentItemMap().isEmpty()) {
            System.out.println("\tItem list is empty");
        } else {
            for (Map.Entry<CraftingReagentItem, Integer> item : hero.getHeroInventory().returnInventoryCraftingReagentItemMap().entrySet()) {
                craftingReagentItems.add(item.getKey());
                PrintUtil.printIndexAndText(String.valueOf(index), " (" + item.getValue() + "x) ");
                PrintUtil.printCraftingReagentItemInfo(item.getKey(), true);
                System.out.println();
            }
        }
        super.sellItem(hero, craftingReagentItems);
    }
}
