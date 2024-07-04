package kuchtastefan.item.specificItems.wearableItem;

import kuchtastefan.item.Item;
import kuchtastefan.item.ItemAndCount;
import kuchtastefan.item.ItemDB;
import kuchtastefan.item.itemFilter.ItemClassFilter;
import kuchtastefan.item.itemFilter.ItemFilter;
import kuchtastefan.item.itemFilter.ItemLevelFilter;
import kuchtastefan.item.itemFilter.ItemTypeFilter;
import kuchtastefan.item.specificItems.craftingItem.CraftingReagentItem;
import kuchtastefan.item.specificItems.craftingItem.CraftingReagentItemType;
import kuchtastefan.service.FileService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WearableItemTest {

    @BeforeAll
    static void setUp() {
        FileService fileService = new FileService();
        fileService.importWearableItemsFromFile();
        fileService.importCraftingReagentItemsFromFile();
    }

    @RepeatedTest(30)
    void reagentNeededToRefineRepeated() {
        Item wearableItem = ItemDB.getRandomItem(new ItemFilter(
                new ItemClassFilter(WearableItem.class),
                new ItemTypeFilter(),
                new ItemLevelFilter()));

        ItemAndCount reagentAndCount = ((WearableItem) wearableItem).reagentNeededToRefine();
        assertEquals(((CraftingReagentItem) reagentAndCount.item()).getItemType(), CraftingReagentItemType.BLACKSMITH_REAGENT);
    }

    @RepeatedTest(30)
    void dismantleTest() {
        Item wearableItem = ItemDB.getRandomItem(new ItemFilter(
                new ItemClassFilter(WearableItem.class),
                new ItemTypeFilter(),
                new ItemLevelFilter()));

        ItemAndCount reagentAndCount = ((WearableItem) wearableItem).dismantle();
        assertEquals(((CraftingReagentItem) reagentAndCount.item()).getItemType(), CraftingReagentItemType.BLACKSMITH_REAGENT);
    }
}