package kuchtastefan.character.npc.vendor.specificVendorCharacter;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.npc.vendor.SortVendorOffer;
import kuchtastefan.character.npc.vendor.VendorCharacter;
import kuchtastefan.item.Item;
import kuchtastefan.item.consumeableItem.ConsumableItem;
import kuchtastefan.item.consumeableItem.ConsumableItemType;
import kuchtastefan.item.craftingItem.CraftingReagentItem;
import kuchtastefan.utility.PrintUtil;

import java.util.List;

public class ConsumableVendorCharacter extends VendorCharacter implements SortVendorOffer {

    public ConsumableVendorCharacter(int vendorId, String name) {
        super(vendorId, name);
    }


    @Override
    public Class<? extends Item> returnItemClass() {
        return ConsumableItem.class;
    }

    @Override
    public void sortVendorOffer() {
        this.returnVendorOffer().sort((item1, item2) -> {
            ConsumableItemType consumableItemType = ((ConsumableItem) item1).getConsumableItemType();
            ConsumableItemType consumableItemType1 = ((ConsumableItem) item2).getConsumableItemType();
            return consumableItemType.compareTo(consumableItemType1);
        });
    }
}
