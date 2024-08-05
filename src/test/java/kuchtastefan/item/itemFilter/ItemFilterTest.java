package kuchtastefan.item.itemFilter;

import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import kuchtastefan.item.itemType.ItemType;
import kuchtastefan.item.specificItems.consumeableItem.ConsumableItem;
import kuchtastefan.item.specificItems.wearableItem.WearableItem;
import kuchtastefan.item.specificItems.wearableItem.WearableItemQuality;
import kuchtastefan.item.specificItems.wearableItem.WearableItemType;
import kuchtastefan.service.FileService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemFilterTest {


    @BeforeAll
    static void setUp() {
        FileService fileService = new FileService();
        fileService.importQuestItemsFromFile();
        fileService.importWearableItemsFromFile();
        fileService.importCraftingReagentItemsFromFile();
        fileService.importConsumableItemsFromFile();
    }

    @RepeatedTest(20)
    void testFilterItemByClassWearableItem() {
        Class<? extends Item> itemClass = WearableItem.class;
        Item item = ItemDB.getRandomItem(new ItemFilter(
                new ItemClassFilter(itemClass),
                new ItemTypeFilter(),
                new ItemLevelFilter()));

        assertEquals(itemClass, item.getClass());
    }

    @RepeatedTest(20)
    void testFilterItemByClassConsumableItem() {
        Class<? extends Item> itemClass = ConsumableItem.class;
        Item item = ItemDB.getRandomItem(new ItemFilter(
                new ItemClassFilter(itemClass),
                new ItemTypeFilter(),
                new ItemLevelFilter()));

        assertEquals(itemClass, item.getClass());
    }

    @RepeatedTest(20)
    void testFilterItemByType() {
        ItemType itemType = WearableItemType.HEAD;
        Item item = ItemDB.getRandomItem(new ItemFilter(
                new ItemClassFilter(),
                new ItemTypeFilter(itemType),
                new ItemLevelFilter()));

        assertEquals(itemType, ((WearableItem) item).getItemType());
    }

    @RepeatedTest(20)
    void testFilterItemByLevel() {
        int level = 2;
        Item item = ItemDB.getRandomItem(new ItemFilter(
                new ItemClassFilter(),
                new ItemTypeFilter(),
                new ItemLevelFilter(level)));

        assertEquals(level, item.getItemLevel());
    }

    @RepeatedTest(20)
    void testFilterItemByQuality() {
        WearableItemQuality quality = WearableItemQuality.BASIC;

        Item item = ItemDB.getRandomItem(new ItemFilter(
                new ItemClassFilter(WearableItem.class),
                new ItemTypeFilter(),
                new ItemLevelFilter(),
                new WearableItemQualityFilter(quality)));

        assertEquals(quality, ((WearableItem) item).getWearableItemQuality());
    }


}