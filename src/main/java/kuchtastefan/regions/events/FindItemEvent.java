package kuchtastefan.regions.events;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.items.ItemsLists;
import kuchtastefan.items.craftingItem.CraftingReagentItem;
import kuchtastefan.items.craftingItem.CraftingReagentItemType;
import kuchtastefan.utility.RandomNumberGenerator;

import java.util.List;

public class FindItemEvent extends Event {


    public FindItemEvent(int eventLevel) {
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

            hero.getHeroInventory().addItemWithNewCopyToItemList(item);
//            hero.checkQuestProgress(null, item);
        }

        return true;

    }

    private CraftingReagentItem findRandomCraftingReagentItem() {
        List<CraftingReagentItem> craftingReagentItemList = ItemsLists.returnCraftingReagentItemListByItemLevel(this.eventLevel, 0);
        return craftingReagentItemList.get(RandomNumberGenerator.getRandomNumber(0, craftingReagentItemList.size() - 1));
    }
}
