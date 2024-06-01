package kuchtastefan.character.hero.save;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.npc.vendor.VendorCharacter;
import kuchtastefan.character.npc.vendor.VendorCharacterDB;
import kuchtastefan.hint.Hint;
import kuchtastefan.hint.HintName;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class GameLoaded {

    private final Hero hero;
    private final Map<HintName, Hint> hintUtil;
    private final Map<Integer, Integer> vendorIdAndItemListId;


    public GameLoaded(Hero hero, Map<HintName, Hint> hintUtil/*, Map<Item, Integer> itemList*/) {
        this.hero = hero;
        this.hintUtil = hintUtil;
        this.vendorIdAndItemListId = new HashMap<>();
    }

    public void setVendorIdAndItemListId() {
        for (VendorCharacter vendorCharacter : VendorCharacterDB.getVENDOR_DB().values()) {
            vendorIdAndItemListId.put(vendorCharacter.getVendorId(), vendorCharacter.getCurrentItemListId());
        }
    }
}
