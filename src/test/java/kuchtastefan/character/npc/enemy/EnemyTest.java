package kuchtastefan.character.npc.enemy;

import kuchtastefan.character.npc.CharacterDB;
import kuchtastefan.service.FileService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnemyTest {
    @BeforeAll
    static void setUp() {
        FileService fileService = new FileService();
        fileService.importCreaturesFromFile();
        fileService.importWearableItemsFromFile();
        fileService.importQuestItemsFromFile();
        fileService.importCraftingReagentItemsFromFile();
        fileService.importJunkItemsFromFile();
        fileService.importKeyItemsFromFile();
        fileService.importConsumableItemsFromFile();
    }

    @Test
    void setItemDropTestAssertNotNull() {
        Enemy enemy = (Enemy) CharacterDB.CHARACTER_DB.get(200);
        enemy.setItemDrop();
        assertNotNull(enemy.getItemsDrop());
    }

    @Test
    void setItemDropTestAssertNotEmpty() {
        Enemy enemy = (Enemy) CharacterDB.CHARACTER_DB.get(200);
        enemy.setItemDrop();
        assertFalse(enemy.getItemsDrop().isEmpty());
    }


}