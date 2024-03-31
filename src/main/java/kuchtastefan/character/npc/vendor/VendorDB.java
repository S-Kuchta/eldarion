package kuchtastefan.character.npc.vendor;

import kuchtastefan.utility.RandomNumberGenerator;

import java.util.HashMap;
import java.util.Map;

public class VendorDB {

    private static final Map<Integer, VendorCharacter> VENDOR_DB = new HashMap<>();
    private static final Map<Integer, VendorCharacter> MERCHANT_DB = new HashMap<>();

    public static void addVendorToDb(VendorCharacter vendorCharacter) {
        if (vendorCharacter.getVendorId() >= 100) {
            MERCHANT_DB.put(vendorCharacter.getVendorId(), vendorCharacter);
        } else {
            VENDOR_DB.put(vendorCharacter.getVendorId(), vendorCharacter);
        }
    }

    public static void setRandomCurrentVendorCharacterItemListId(int level) {
        for (VendorCharacter vendorCharacter : VENDOR_DB.values()) {
            vendorCharacter.setRandomCurrentItemListId(level);
        }
    }

    public static VendorCharacter returnVendorCharacterFromDb(int vendorId) {
        return VENDOR_DB.get(vendorId);
    }

    public static VendorCharacter returnRandomMerchant() {
        return MERCHANT_DB.get(RandomNumberGenerator.getRandomNumber(100, (MERCHANT_DB.size() + 100) - 1));
    }
}
