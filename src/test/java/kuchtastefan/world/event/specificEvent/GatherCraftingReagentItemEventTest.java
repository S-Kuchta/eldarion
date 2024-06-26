package kuchtastefan.world.event.specificEvent;

import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import kuchtastefan.item.itemFilter.ItemClassFilter;
import kuchtastefan.item.itemFilter.ItemFilter;
import kuchtastefan.item.itemFilter.ItemLevelFilter;
import kuchtastefan.item.itemFilter.ItemTypeFilter;
import kuchtastefan.item.specificItems.craftingItem.CraftingReagentItem;
import kuchtastefan.service.FileService;
import kuchtastefan.utility.RandomNumberGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;


class GatherCraftingReagentItemEventTest {

    @BeforeAll
    static void setUp() {
        FileService fileService = new FileService();
        fileService.importCraftingReagentItemsFromFile();
    }

    @Test
    void findRandomCraftingReagentItemTest() {
        int eventLevel = 1;
        List<Item> craftingReagentItemList = ItemDB.returnFilteredItemList(new ItemFilter(
                new ItemClassFilter(CraftingReagentItem.class),
                new ItemTypeFilter(),
                new ItemLevelFilter(1, eventLevel)));

        Item item = craftingReagentItemList.get(RandomNumberGenerator.getRandomNumber(0, craftingReagentItemList.size() - 1));
        assertNotNull(item);
    }

}