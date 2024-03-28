package kuchtastefan.character.npc.vendor.specificVendorCharacter;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.npc.vendor.SortVendorOffer;
import kuchtastefan.character.npc.vendor.VendorCharacter;
import kuchtastefan.item.Item;
import kuchtastefan.item.craftingItem.CraftingReagentItem;
import kuchtastefan.item.craftingItem.CraftingReagentItemType;
import kuchtastefan.utility.PrintUtil;

import java.util.List;

public class CraftingReagentItemVendorCharacter extends VendorCharacter implements SortVendorOffer {

    public CraftingReagentItemVendorCharacter(int vendorId, String name) {
        super(vendorId, name);
    }


    @Override
    public Class<? extends Item> returnItemClass() {
        return CraftingReagentItem.class;
    }

    @Override
    public void sortVendorOffer() {
        this.returnVendorOffer().sort((item1, item2) -> {
            CraftingReagentItemType craftingReagentItemType = ((CraftingReagentItem) item1).getCraftingReagentItemType();
            CraftingReagentItemType craftingReagentItemType1 = ((CraftingReagentItem) item2).getCraftingReagentItemType();
            return craftingReagentItemType.compareTo(craftingReagentItemType1);
        });
    }
}
