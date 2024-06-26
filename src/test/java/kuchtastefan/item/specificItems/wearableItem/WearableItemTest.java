package kuchtastefan.item.specificItems.wearableItem;

import kuchtastefan.item.Item;
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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WearableItemTest {

    @BeforeAll
    static void setUp() {
        FileService fileService = new FileService();
        fileService.importWearableItemsFromFile();
        fileService.importCraftingReagentItemsFromFile();
    }

    @Test
    void reagentNeededToRefine() {
        Item wearableItem = ItemDB.returnItemFromDB(200);
        Item reagent = ItemDB.getRandomItem(new ItemFilter(
                new ItemClassFilter(),
                new ItemTypeFilter(CraftingReagentItemType.BLACKSMITH_REAGENT),
                new ItemLevelFilter(wearableItem.getItemLevel())));

        assertNotNull(reagent);
    }

    @RepeatedTest(30)
    void reagentNeededToRefineRepeated() {
        Item wearableItem = ItemDB.getRandomItem(new ItemFilter(
                new ItemClassFilter(WearableItem.class),
                new ItemTypeFilter(),
                new ItemLevelFilter()));

        Item reagent = ItemDB.getRandomItem(new ItemFilter(
                new ItemClassFilter(),
                new ItemTypeFilter(CraftingReagentItemType.BLACKSMITH_REAGENT),
                new ItemLevelFilter(wearableItem.getItemLevel())));

        assertEquals(((CraftingReagentItem) reagent).getItemType(), CraftingReagentItemType.BLACKSMITH_REAGENT);
    }
}