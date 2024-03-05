package kuchtastefan.region.event;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.ItemsLists;
import kuchtastefan.item.consumeableItem.ConsumableItem;
import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.utility.PrintUtil;
import kuchtastefan.utility.RandomNumberGenerator;

import java.util.List;

public class FindTreasureEvent extends Event {
    public FindTreasureEvent(int eventLevel) {
        super(eventLevel);
    }

    @Override
    public boolean eventOccurs(Hero hero) {
        List<WearableItem> tempWearableItemList = ItemsLists.returnWearableItemListByItemLevel(this.eventLevel, null, true);
        List<ConsumableItem> tempConsumableItemList = ItemsLists.returnConsumableItemListByItemLevel(this.eventLevel, null);

        System.out.println("\tYou find a treasure! You got: ");
        WearableItem wearableItem = tempWearableItemList.get(RandomNumberGenerator.getRandomNumber(0, tempWearableItemList.size() - 1));
        System.out.print("\t");

        hero.getHeroInventory().addItemWithNewCopyToItemList(wearableItem);

        PrintUtil.printItemDescription(wearableItem, true, hero);

        int numberOfItems = RandomNumberGenerator.getRandomNumber(2, 4);
        for (int i = 0; i < numberOfItems; i++) {
            ConsumableItem consumableItem = tempConsumableItemList.get(
                    RandomNumberGenerator.getRandomNumber(0, tempConsumableItemList.size() - 1));

            hero.getHeroInventory().addItemWithNewCopyToItemList(consumableItem);
            System.out.print("\t");
            PrintUtil.printConsumableItemInfo(consumableItem, true);
        }

        return true;
    }
}
