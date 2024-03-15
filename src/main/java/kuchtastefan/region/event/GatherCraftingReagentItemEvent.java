package kuchtastefan.region.event;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.ItemDB;
import kuchtastefan.item.craftingItem.CraftingReagentItem;
import kuchtastefan.item.craftingItem.CraftingReagentItemType;
import kuchtastefan.utility.RandomNumberGenerator;

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

        for (int i = 0; i < numberOfFindingItems; i++) {
            if (item.getCraftingReagentItemType().equals(CraftingReagentItemType.ALCHEMY_REAGENT)) {
                System.out.println("\t--> You gather " + item.getName() + " <--");
            }

            if (item.getCraftingReagentItemType().equals(CraftingReagentItemType.BLACKSMITH_REAGENT)) {
                System.out.println("\t--> You mined " + item.getName() + " <--");
            }
        }

        hero.getHeroInventory().addItemWithNewCopyToItemList(item);
        hero.checkQuestProgress(null);
        hero.checkIfQuestObjectivesAndQuestIsCompleted();

        return true;

    }

    private CraftingReagentItem findRandomCraftingReagentItem() {
        List<CraftingReagentItem> craftingReagentItemList = ItemDB.returnCraftingReagentItemListByItemLevel(this.eventLevel, 0);
        return craftingReagentItemList.get(RandomNumberGenerator.getRandomNumber(0, craftingReagentItemList.size() - 1));
    }
}
