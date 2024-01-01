package kuchtastefan.domain.vendor;

import kuchtastefan.ability.Ability;
import kuchtastefan.domain.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.utility.PrintUtil;

import java.util.List;
import java.util.Map;

public class WearableVendorCharacter extends Vendor {

    public WearableVendorCharacter(String name, Map<Ability, Integer> abilities, List<? extends Item> itemList) {
        super(name, abilities, itemList);
    }

    @Override
    public void vendorOffer(Hero hero) {
        int index = 1;
        System.out.println("\t0. Go back");
        for (Item wearableItem : this.itemsForSale) {
            System.out.print("\t" + index + ". ");
            PrintUtil.printFullItemDescription((WearableItem) wearableItem);
            index++;
        }
        super.buyItem(hero);
    }

    @Override
    public void successfullyItemBought(Hero hero, Item item) {
        hero.addItemWithNewCopyToItemList((WearableItem) item);
        hero.setHeroGold(hero.getHeroGold() - item.getPrice());
        System.out.println(item.getName() + " bought. You can find it in your inventory");
    }
}
