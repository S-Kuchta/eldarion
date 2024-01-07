package kuchtastefan.domain.vendor;

import kuchtastefan.domain.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.consumeableItem.ConsumableItem;
import kuchtastefan.item.consumeableItem.ConsumableItemType;
import kuchtastefan.utility.PrintUtil;
import kuchtastefan.utility.RandomNumberGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConsumableVendorCharacter extends VendorCharacter {
    private final ConsumableItemType vendorSellingConsumableItemType;

    public ConsumableVendorCharacter(String name, int level, List<? extends Item> itemsForSale, ConsumableItemType vendorSellingConsumableItemType) {
        super(name, level, itemsForSale);
        this.vendorSellingConsumableItemType = vendorSellingConsumableItemType;
    }

    @Override
    public void vendorOffer(Hero hero) {
        this.itemsForSale.sort((item1, item2) -> {
            ConsumableItemType consumableItemType = ((ConsumableItem) item1).getConsumableItemType();
            ConsumableItemType consumableItemType1 = ((ConsumableItem) item2).getConsumableItemType();
            return consumableItemType.compareTo(consumableItemType1);
        });

        PrintUtil.printShopHeader(hero, "Consumable");
        this.printItems();
        super.buyItem(hero);
    }

    @Override
    protected void printItems() {
        int index = 1;
        System.out.println("\t0. Go back");
        for (Item consumableItem : this.itemsForSale) {
            if (consumableItem instanceof ConsumableItem) {
                System.out.print("\t" + index + ". " + consumableItem.getName()
                        + ", Item Type: " + ((ConsumableItem) consumableItem).getConsumableItemType()
                        + ", Item Price: " + consumableItem.getPrice() + " golds");

                index++;
                System.out.println();
            }

        }
    }

    @Override
    public void printItemsForSale(Hero hero) {
        List<ConsumableItem> consumableItemList = new ArrayList<>();
        PrintUtil.printShopHeader(hero, "Consumable");
        int index = 1;
        System.out.println("\t0. Go back");
        if (hero.getItemInventoryList().returnInventoryConsumableItemMap().isEmpty()) {
            System.out.println("\tItem list is empty");
        } else {
            for (Map.Entry<ConsumableItem, Integer> item : hero.getItemInventoryList().returnInventoryConsumableItemMap().entrySet()) {
                consumableItemList.add(item.getKey());
                System.out.print("\t" + index + ". (" + item.getValue() + "x) ");
                PrintUtil.printConsumableItemInfo(item.getKey());
                System.out.println("\n\t\tsell price: " + super.returnSellItemPrice(item.getKey()));
            }
        }
        super.sellItem(hero, consumableItemList);
    }

    @Override
    public void printGreeting() {
        int randomChoice = RandomNumberGenerator.getRandomNumber(0, 1);
        PrintUtil.printLongDivider();
        if (vendorSellingConsumableItemType.equals(ConsumableItemType.FOOD)) {
            switch (randomChoice) {
                case 0 ->
                        PrintUtil.printStringSlowly("\tStep into the enchanting world of culinary wonders at the Gastronome's Gourmet Emporium." +
                                "\n\tHelmed by the renowned chef," + getName() + ",\n\t" +
                                " this opulent marketplace showcases a vast array of delectable delights.");
                case 1 ->
                        PrintUtil.printStringSlowly("\tn the heart of a bustling town sits The Drunken Dragon Tavern,\n\t" +
                                "overseen by the enigmatic barkeep known only as " + getName() + "." +
                                "\n\tThe tavern's walls echo with tales of mythical creatures and epic adventures." +
                                "\n\t" + getName() + "'s collection of spirits and ales promises an experience like no other,");
            }
        }

        if (vendorSellingConsumableItemType.equals(ConsumableItemType.POTION)) {
            switch (randomChoice) {
                case 0 ->
                        PrintUtil.printStringSlowly("\tEnter " + getName() + "'s arcane shop, nestled in an ancient city." +
                                "\n\tGlistening vials of elixirs, each with a tale of far-off lands.");
                case 1 ->
                        PrintUtil.printStringSlowly("\t" + getName() + "'s whimsical shop, hidden in an enchanted glade," +
                                "\n\tboasts elixirs bearing tales of dragons and faeries." +
                                "\n\tThe shelves hold vials of invisibility and heightened senses," +
                                "\n\teach enchanted with ancient magic and mischievous whimsy.");
            }
        }
        PrintUtil.printLongDivider();
    }
}
