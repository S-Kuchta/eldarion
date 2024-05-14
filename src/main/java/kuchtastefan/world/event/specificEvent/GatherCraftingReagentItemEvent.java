package kuchtastefan.world.event.specificEvent;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.ItemDB;
import kuchtastefan.item.itemFilter.ItemFilter;
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
        CraftingReagentItem item = findRandomCraftingReagentItem();
        int numberOfFindingItems = RandomNumberGenerator.getRandomNumber(0, 2);

        if (numberOfFindingItems == 0) {
            System.out.println("\t--> This is unlucky, you were clumsy and ruined " + item.getName() + " <--");
            return true;
        }

        System.out.println("--> During travelling You find and gather <--");
        hero.getHeroInventory().addItemToInventory(item, numberOfFindingItems);
//        hero.checkIfQuestObjectivesAndQuestIsCompleted();

        return true;

    }

    private CraftingReagentItem findRandomCraftingReagentItem() {
        List<CraftingReagentItem> craftingReagentItemList = ItemDB.returnFilteredItemList(CraftingReagentItem.class,
                new ItemFilter(this.eventLevel, 1));

        return craftingReagentItemList.get(RandomNumberGenerator.getRandomNumber(0, craftingReagentItemList.size() - 1));
    }
}
