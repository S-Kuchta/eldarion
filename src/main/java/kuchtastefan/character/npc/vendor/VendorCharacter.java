package kuchtastefan.character.npc.vendor;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.npc.vendor.vendorOffer.VendorOfferDB;
import kuchtastefan.item.Item;
import kuchtastefan.utility.printUtil.PrintUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class VendorCharacter {

    protected int vendorId;
    protected String name;
    private int[] itemListsId;
    private Integer currentItemListId;

    public VendorCharacter(int vendorId, String name) {
        this.vendorId = vendorId;
        this.name = name;
    }

    public VendorCharacter(String name, Integer currentItemListId) {
        this.name = name;
        this.currentItemListId = currentItemListId;
    }

    public abstract Class<? extends Item> returnItemClass();

    public void printVendorItemsOffer(Hero hero) {
        int index = 1;
        PrintUtil.printIndexAndText("0", "Go Back");
        System.out.println();
        for (Item item : returnVendorOffer()) {
            PrintUtil.printIndexAndText(String.valueOf(index), "");
            item.printItemDescription(hero);
            index++;
        }
    }

    public void setRandomCurrentItemListId(int level) {
        this.currentItemListId = VendorOfferDB.returnRandomVendorItemListFromDb(this, level).itemListId();
    }

    public List<? extends Item> returnVendorOffer() {
        return VendorOfferDB.convertIdArrayToItemList(this.currentItemListId);
    }

}
