package kuchtastefan.domain.vendor;

import kuchtastefan.domain.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.craftingItem.CraftingReagentItem;
import kuchtastefan.item.craftingItem.CraftingReagentItemType;
import kuchtastefan.utility.PrintUtil;

import java.util.List;

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

        this.printGreeting();
        PrintUtil.printShopHeader(hero, "Crafting reagents");
        int index = 1;
        System.out.println("\t0. Go back");
        for (Item craftingReagentItem : this.itemsForSale) {
            System.out.print("\t" + index + ". ");
            System.out.println(craftingReagentItem.toString());
            index++;
        }
        super.buyItem(hero);
    }

    @Override
    public void printGreeting() {

    }

    @Override
    public void printItemsForSale(Hero hero) {

    }
}
