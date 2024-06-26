package kuchtastefan.world.event.specificEvent;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import kuchtastefan.item.itemFilter.ItemClassFilter;
import kuchtastefan.item.itemFilter.ItemFilter;
import kuchtastefan.item.itemFilter.ItemLevelFilter;
import kuchtastefan.item.specificItems.craftingItem.CraftingReagentItem;
import kuchtastefan.utility.RandomNumberGenerator;
import kuchtastefan.world.event.Event;

import java.util.List;

public class GatherCraftingReagentItemEvent extends Event {


    public GatherCraftingReagentItemEvent(int eventLevel) {
        super(eventLevel);
    }

    @Override
    public boolean eventOccurs(Hero hero) {
        Item item = findRandomCraftingReagentItem();
        int numberOfFindingItems = RandomNumberGenerator.getRandomNumber(0, 2);

        if (numberOfFindingItems == 0) {
            System.out.println("\t--> This is unlucky, you were clumsy and ruined " + item.getName() + " <--");
            return true;
        }

        System.out.println("\t--> During travelling You find and gather <--");
        hero.getHeroInventoryManager().addItem(item, numberOfFindingItems);

        return true;

    }

    private Item findRandomCraftingReagentItem() {
        List<Item> craftingReagentItemList = ItemDB.returnFilteredItemList(new ItemFilter(
                new ItemClassFilter(CraftingReagentItem.class),
                new ItemLevelFilter(1, this.eventLevel)));

        return craftingReagentItemList.get(RandomNumberGenerator.getRandomNumber(0, craftingReagentItemList.size() - 1));
    }
}
