package kuchtastefan.character.npc.vendor;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.items.Item;
import kuchtastefan.items.consumeableItem.ConsumableItem;
import kuchtastefan.items.consumeableItem.ConsumableItemType;
import kuchtastefan.utility.PrintUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConsumableVendorCharacter extends VendorCharacter {

    public ConsumableVendorCharacter(String name, int level, List<? extends Item> itemsForSale) {
        super(name, level, itemsForSale);
    }

    @Override
    public void vendorOffer(Hero hero) {
        this.itemsForSale.sort((item1, item2) -> {
            ConsumableItemType consumableItemType = ((ConsumableItem) item1).getConsumableItemType();
            ConsumableItemType consumableItemType1 = ((ConsumableItem) item2).getConsumableItemType();
            return consumableItemType.compareTo(consumableItemType1);
        });

        PrintUtil.printShopHeader(hero, "Consumable");
        this.printVendorItemsForSale(hero);
        super.buyItem(hero);
    }

    @Override
    protected void printVendorItemsForSale(Hero hero) {
        int index = 1;
        PrintUtil.printIndexAndText("0", "Go Back");
        System.out.println();
        for (Item consumableItem : this.itemsForSale) {
            if (consumableItem instanceof ConsumableItem) {
                PrintUtil.printIndexAndText(String.valueOf(index), "");
                PrintUtil.printConsumableItemInfo((ConsumableItem) consumableItem, false);

                index++;
                System.out.println();
            }
        }
    }

    @Override
    public void printHeroItemsForSale(Hero hero) {
        List<ConsumableItem> consumableItemList = new ArrayList<>();
        PrintUtil.printShopHeader(hero, "Consumable");

        int index = 1;
        PrintUtil.printIndexAndText("0", "Go Back");
        if (hero.getHeroInventory().returnInventoryConsumableItemMap().isEmpty()) {
            System.out.println("\tItem list is empty");
        } else {
            for (Map.Entry<ConsumableItem, Integer> item : hero.getHeroInventory().returnInventoryConsumableItemMap().entrySet()) {
                consumableItemList.add(item.getKey());
                PrintUtil.printIndexAndText(String.valueOf(index), " (" + item.getValue() + "x) ");
                PrintUtil.printConsumableItemInfo(item.getKey(), true);
            }
        }
        super.sellItem(hero, consumableItemList);
    }
}
