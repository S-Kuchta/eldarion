package kuchtastefan.character.npc.vendor.specificVendorCharacter;

import kuchtastefan.character.npc.vendor.SortVendorOffer;
import kuchtastefan.character.npc.vendor.VendorCharacter;
import kuchtastefan.item.Item;
import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.item.wearableItem.WearableItemType;

public class WearableItemVendorCharacter extends VendorCharacter implements SortVendorOffer {

    public WearableItemVendorCharacter(int vendorId, String name) {
        super(vendorId, name);
    }

    public WearableItemVendorCharacter(String name, Integer currentItemListId) {
        super(name, currentItemListId);
    }


    @Override
    public Class<? extends Item> returnItemClass() {
        return WearableItem.class;
    }

    @Override
    public void sortVendorOffer() {
        if (this.returnVendorOffer().size() > 2) {
            this.returnVendorOffer().sort((item1, item2) -> {
                WearableItemType wearableItemType1 = ((WearableItem) item1).getWearableItemType();
                WearableItemType wearableItemType2 = ((WearableItem) item2).getWearableItemType();
                return wearableItemType1.compareTo(wearableItemType2);
            });
        }
    }
}
