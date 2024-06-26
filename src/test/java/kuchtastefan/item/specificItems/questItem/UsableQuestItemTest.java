package kuchtastefan.item.specificItems.questItem;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import kuchtastefan.service.FileService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsableQuestItemTest {

    @BeforeAll
    static void setUp() {
        FileService fileService = new FileService();
        fileService.importQuestItemsFromFile();
    }

    @Test
    void useItem() {
        Item item = ItemDB.returnItemFromDB(1300);
        UsableQuestItem usableQuestItem = (UsableQuestItem) item;
        assertEquals(UsableQuestItem.class, usableQuestItem.getClass());
    }

    @Test
    void addToInventory() {
        Hero hero = new Hero("Test");
        Item item = ItemDB.returnItemFromDB(1300);
        hero.getHeroInventoryManager().addItem(item, 1);
        assertTrue(hero.getHeroInventoryManager().hasRequiredItems(item, 1));
    }
}