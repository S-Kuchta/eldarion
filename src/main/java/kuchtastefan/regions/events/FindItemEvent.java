package kuchtastefan.regions.events;

import kuchtastefan.domain.Hero;
import kuchtastefan.item.ItemsLists;
import kuchtastefan.item.craftingItem.CraftingReagentItem;
import kuchtastefan.item.craftingItem.CraftingReagentItemType;
import kuchtastefan.utility.RandomNumberGenerator;

import java.util.List;

public class FindItemEvent extends Event {

    private final ItemsLists itemsLists;

    public FindItemEvent(int eventLevel, ItemsLists itemsLists) {
        super(eventLevel);
        this.itemsLists = itemsLists;
    }

    @Override
    public void eventOccurs(Hero hero) {
        CraftingReagentItem item = findRandomCraftingReagentItem();
        int numberOfFindingItems = RandomNumberGenerator.getRandomNumber(0, 2);

        if (numberOfFindingItems == 0) {
            System.out.println("\tThis is unlucky, you were clumsy and ruined " + item.getName());
            return;
        }

        for (int i = 0; i < numberOfFindingItems; i++) {
            if (item.getCraftingReagentItemType().equals(CraftingReagentItemType.ALCHEMY_REAGENT)) {
                System.out.println("\tYou gather " + item.getName());
            }

            if (item.getCraftingReagentItemType().equals(CraftingReagentItemType.BLACKSMITH_REAGENT)) {
                System.out.println("\tYou mined " + item.getName());
            }

            hero.getHeroInventory().addItemWithNewCopyToItemList(item);
        }
    }

    private CraftingReagentItem findRandomCraftingReagentItem() {
        List<CraftingReagentItem> craftingReagentItemList = this.itemsLists.returnCraftingReagentItemListByItemLevel(this.eventLevel, 0);
        return craftingReagentItemList.get(RandomNumberGenerator.getRandomNumber(0, craftingReagentItemList.size() - 1));
    }
}
