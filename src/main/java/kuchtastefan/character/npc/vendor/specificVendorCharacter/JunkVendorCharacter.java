package kuchtastefan.character.npc.vendor.specificVendorCharacter;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.npc.vendor.VendorCharacter;
import kuchtastefan.item.Item;
import kuchtastefan.item.junkItem.JunkItem;
import kuchtastefan.utility.PrintUtil;

import java.util.List;

public class JunkVendorCharacter extends VendorCharacter {

    public JunkVendorCharacter(int vendorId, String name) {
        super(vendorId, name);
    }


    @Override
    public Class<? extends Item> returnItemClass() {
        return JunkItem.class;
    }
}
