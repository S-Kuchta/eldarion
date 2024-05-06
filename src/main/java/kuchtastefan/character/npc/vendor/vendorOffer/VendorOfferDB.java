package kuchtastefan.character.npc.vendor.vendorOffer;

import kuchtastefan.character.npc.vendor.VendorCharacter;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import kuchtastefan.utility.RandomNumberGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendorOfferDB {

    private static final Map<Integer, VendorItemList> VENDOR_ITEMS_DB = new HashMap<>();

    public static void addVendorItemsToDb(VendorItemList vendorItemList) {
        VENDOR_ITEMS_DB.put(vendorItemList.itemListId(), vendorItemList);
    }

    public static List<Item> convertIdArrayToItemList(int vendorItemListId) {
        List<Item> itemList = new ArrayList<>();
        for (int itemId : VENDOR_ITEMS_DB.get(vendorItemListId).itemsId()) {
            itemList.add(ItemDB.returnItemFromDB(itemId));
        }

        return itemList;
    }

    public static VendorItemList returnRandomVendorItemListFromDb(VendorCharacter vendorCharacter, int level) {
        List<VendorItemList> tempVendorItemList = new ArrayList<>();

        for (int vendorItemListId : vendorCharacter.getItemListsId()) {
            VendorItemList vendorItemList = VENDOR_ITEMS_DB.get(vendorItemListId);
            if (vendorItemList.listLevel() == level) {
                tempVendorItemList.add(vendorItemList);
            }
        }

        try {
            return tempVendorItemList.get(RandomNumberGenerator.getRandomNumber(0, tempVendorItemList.size() - 1));
        } catch (Exception e) {
            return VENDOR_ITEMS_DB.get(0);
        }
    }
}
