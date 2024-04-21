package kuchtastefan.world.event.specificEvent;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.ItemDB;
import kuchtastefan.character.hero.inventory.itemFilter.ItemFilter;
import kuchtastefan.item.specificItems.craftingItem.CraftingReagentItem;
import kuchtastefan.item.specificItems.craftingItem.CraftingReagentItemType;
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

        if (item.getItemType().equals(CraftingReagentItemType.ALCHEMY_REAGENT)) {
            System.out.println("\t--> You gather " + numberOfFindingItems + "x " + item.getName() + " <--");
        }

        if (item.getItemType().equals(CraftingReagentItemType.BLACKSMITH_REAGENT)) {
            System.out.println("\t--> You mined " + numberOfFindingItems + "x " + item.getName() + " <--");
        }

        hero.getHeroInventory().addItemWithNewCopyToItemList(item, numberOfFindingItems);
        hero.checkIfQuestObjectivesAndQuestIsCompleted();

        return true;

    }

    private CraftingReagentItem findRandomCraftingReagentItem() {
        List<CraftingReagentItem> craftingReagentItemList = ItemDB.returnItemListByLevelAndType(CraftingReagentItem.class,
                new ItemFilter(this.eventLevel, 0));

        return craftingReagentItemList.get(RandomNumberGenerator.getRandomNumber(0, craftingReagentItemList.size() - 1));
    }
}
