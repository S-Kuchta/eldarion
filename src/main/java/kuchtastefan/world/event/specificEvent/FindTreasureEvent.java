package kuchtastefan.world.event.specificEvent;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.itemFilter.ItemClassFilter;
import kuchtastefan.item.itemFilter.ItemFilter;
import kuchtastefan.item.ItemDB;
import kuchtastefan.item.itemFilter.ItemLevelFilter;
import kuchtastefan.item.itemFilter.ItemTypeFilter;
import kuchtastefan.item.specificItems.consumeableItem.ConsumableItem;
import kuchtastefan.item.specificItems.wearableItem.WearableItem;
import kuchtastefan.utility.RandomNumberGenerator;
import kuchtastefan.world.event.Event;

import java.util.List;

public class FindTreasureEvent extends Event {
    public FindTreasureEvent(int eventLevel) {
        super(eventLevel);
    }

    @Override
    public boolean eventOccurs(Hero hero) {
        List<Item> tempWearableItemList = ItemDB.returnFilteredItemList(new ItemFilter(
                new ItemClassFilter(WearableItem.class),
                new ItemTypeFilter(),
                new ItemLevelFilter(this.eventLevel)));


        List<Item> tempConsumableItemList = ItemDB.returnFilteredItemList(new ItemFilter(
                new ItemClassFilter(ConsumableItem.class),
                new ItemTypeFilter(),
                new ItemLevelFilter(this.eventLevel)));

        System.out.println("\tYou find a treasure! You got: ");
        Item wearableItem = tempWearableItemList.get(RandomNumberGenerator.getRandomNumber(0, tempWearableItemList.size() - 1));
        System.out.print("\t");

        hero.getHeroInventoryManager().addItem(wearableItem, 1);
        wearableItem.printItemDescription(hero);

        int numberOfItems = RandomNumberGenerator.getRandomNumber(2, 4);
        for (int i = 0; i < numberOfItems; i++) {
            Item consumableItem = tempConsumableItemList.get(
                    RandomNumberGenerator.getRandomNumber(0, tempConsumableItemList.size() - 1));

            hero.getHeroInventoryManager().addItem(consumableItem, 1);
            System.out.print("\t");
            consumableItem.printItemDescription(hero);
        }

        return true;
    }
}
