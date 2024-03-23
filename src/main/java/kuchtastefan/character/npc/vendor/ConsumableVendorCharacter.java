package kuchtastefan.character.npc.vendor;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.consumeableItem.ConsumableItem;
import kuchtastefan.item.consumeableItem.ConsumableItemType;
import kuchtastefan.utility.PrintUtil;

import java.util.List;

public class ConsumableVendorCharacter extends VendorCharacter {

    public ConsumableVendorCharacter(String name, int level, List<? extends Item> itemsForSale, Class<? extends Item> className) {
        super(name, level, itemsForSale, className);
    }

    @Override
    public void vendorOffer(Hero hero) {
        this.vendorOffer.sort((item1, item2) -> {
            ConsumableItemType consumableItemType = ((ConsumableItem) item1).getConsumableItemType();
            ConsumableItemType consumableItemType1 = ((ConsumableItem) item2).getConsumableItemType();
            return consumableItemType.compareTo(consumableItemType1);
        });

        PrintUtil.printShopHeader(hero, "Consumable");
        this.printVendorItemsOffer(hero);
        super.buyItem(hero);
    }

    @Override
    protected void printVendorItemsOffer(Hero hero) {
        int index = 1;
        PrintUtil.printIndexAndText("0", "Go Back");
        System.out.println();
        for (Item consumableItem : this.vendorOffer) {
            if (consumableItem instanceof ConsumableItem) {
                PrintUtil.printIndexAndText(String.valueOf(index), "");
                PrintUtil.printConsumableItemInfo((ConsumableItem) consumableItem, false);

                index++;
                System.out.println();
            }
        }
    }
}
