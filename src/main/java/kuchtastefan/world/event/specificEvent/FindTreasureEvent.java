package kuchtastefan.world.event.specificEvent;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.ItemFilter;
import kuchtastefan.item.ItemDB;
import kuchtastefan.item.consumeableItem.ConsumableItem;
import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.utility.RandomNumberGenerator;
import kuchtastefan.world.event.Event;

import java.util.List;

public class FindTreasureEvent extends Event {
    public FindTreasureEvent(int eventLevel) {
        super(eventLevel);
    }

    @Override
    public boolean eventOccurs(Hero hero) {
        List<WearableItem> tempWearableItemList = ItemDB.returnItemListByLevelAndType(WearableItem.class,
                new ItemFilter(this.eventLevel));

        List<ConsumableItem> tempConsumableItemList = ItemDB.returnItemListByLevelAndType(ConsumableItem.class,
                new ItemFilter(this.eventLevel));

        System.out.println("\tYou find a treasure! You got: ");
        WearableItem wearableItem = tempWearableItemList.get(RandomNumberGenerator.getRandomNumber(0, tempWearableItemList.size() - 1));
        System.out.print("\t");

        hero.getHeroInventory().addItemWithNewCopyToItemList(wearableItem);
        wearableItem.printItemDescription(hero);

        int numberOfItems = RandomNumberGenerator.getRandomNumber(2, 4);
        for (int i = 0; i < numberOfItems; i++) {
            ConsumableItem consumableItem = tempConsumableItemList.get(
                    RandomNumberGenerator.getRandomNumber(0, tempConsumableItemList.size() - 1));

            hero.getHeroInventory().addItemWithNewCopyToItemList(consumableItem);
            System.out.print("\t");
            consumableItem.printItemDescription(hero);
        }

        return true;
    }
}
