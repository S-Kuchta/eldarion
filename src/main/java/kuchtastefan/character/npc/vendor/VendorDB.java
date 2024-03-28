package kuchtastefan.character.npc.vendor;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.utility.RandomNameGenerator;
import kuchtastefan.utility.RandomNumberGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendorDB {

    private static final Map<Integer, VendorCharacter> VENDOR_DB = new HashMap<>();

    public static void addVendorToDb(VendorCharacter vendorCharacter) {
        VENDOR_DB.put(vendorCharacter.getVendorId(), vendorCharacter);
    }

    public static void setCurrentVendorCharacterItemListId(int level) {
        for (VendorCharacter vendorCharacter : VENDOR_DB.values()) {
            vendorCharacter.setCurrentItemListId(level);
        }
    }

    public static VendorCharacter returnVendorCharacterFromDb(int vendorId) {
        return VENDOR_DB.get(vendorId);
    }
}
