package kuchtastefan.character.npc.vendor;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.craftingItem.CraftingReagentItem;
import kuchtastefan.item.craftingItem.CraftingReagentItemType;
import kuchtastefan.utility.PrintUtil;

import java.util.List;

public class CraftingReagentItemVendorCharacter extends VendorCharacter {

    public CraftingReagentItemVendorCharacter(String name, int level, List<? extends Item> itemsForSale, Class<? extends Item> className) {
        super(name, level, itemsForSale, className);
    }

    @Override
    protected void vendorOffer(Hero hero) {
        this.vendorOffer.sort((item1, item2) -> {
            CraftingReagentItemType craftingReagentItemType = ((CraftingReagentItem) item1).getCraftingReagentItemType();
            CraftingReagentItemType craftingReagentItemType1 = ((CraftingReagentItem) item2).getCraftingReagentItemType();
            return craftingReagentItemType.compareTo(craftingReagentItemType1);
        });

        PrintUtil.printShopHeader(hero, "Crafting reagents");
        this.printVendorItemsOffer(hero);
        super.buyItem(hero);
    }

    @Override
    protected void printVendorItemsOffer(Hero hero) {
        int index = 1;
        PrintUtil.printIndexAndText("0", "Go back");
        System.out.println();
        for (Item craftingReagentItem : this.vendorOffer) {
            if (craftingReagentItem instanceof CraftingReagentItem) {
                PrintUtil.printIndexAndText(String.valueOf(index), "");
                PrintUtil.printCraftingReagentItemInfo((CraftingReagentItem) craftingReagentItem, false);
                System.out.println();
                index++;
            }
        }
    }
}
