package kuchtastefan.character.hero;

import kuchtastefan.character.npc.CharacterDB;
import kuchtastefan.character.npc.enemy.Enemy;
import kuchtastefan.character.npc.enemy.QuestEnemy;
import kuchtastefan.service.FileService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnemyKilledTest {

    @BeforeAll
    static void setUp() {
        FileService fileService = new FileService();
        fileService.importQuestsObjectiveFromFile();
        fileService.importKeyItemsFromFile();
        fileService.importConsumableItemsFromFile();
        fileService.importWearableItemsFromFile();
        fileService.importCraftingReagentItemsFromFile();
        fileService.importCreaturesFromFile();
    }

    @RepeatedTest(10)
    void checkIfEnemyIsQuestEnemy() {
        Enemy questEnemy = CharacterDB.returnNewEnemy(401);
        assertInstanceOf(QuestEnemy.class, questEnemy);
    }

    @Test
    void addQuestEnemyKilled() {
        Hero hero = new Hero("Test");

        Enemy questEnemy = CharacterDB.returnNewEnemy(401);
        hero.getEnemyKilled().addQuestEnemyKilled(questEnemy.getNpcId());

        assertEquals(1, hero.getEnemyKilled().getAmountOfKilledEnemy(questEnemy.getNpcId()));
    }

    @Test
    void containsEnemy() {
        Hero hero = new Hero("Test");
        Enemy questEnemy = CharacterDB.returnNewEnemy(401);
        hero.getEnemyKilled().addQuestEnemyKilled(questEnemy.getNpcId());

        assertTrue(hero.getEnemyKilled().containsEnemy(questEnemy.getNpcId()));
    }

    @Test
    void getAmountOfKilledEnemy() {
        Hero hero = new Hero("Test");
        int characterId = 401;
        int numberOfIteration = 5;

        for (int i = 0; i < numberOfIteration; i++) {
            Enemy questEnemy = CharacterDB.returnNewEnemy(characterId);
            hero.getEnemyKilled().addQuestEnemyKilled(questEnemy.getNpcId());
        }

        assertEquals(numberOfIteration, hero.getEnemyKilled().getAmountOfKilledEnemy(characterId));

    }
}