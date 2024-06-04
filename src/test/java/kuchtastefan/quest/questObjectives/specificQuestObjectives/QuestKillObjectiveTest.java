package kuchtastefan.quest.questObjectives.specificQuestObjectives;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.QuestObjectiveDB;
import kuchtastefan.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;

import static org.junit.jupiter.api.Assertions.*;

class QuestKillObjectiveTest {

    @BeforeEach
    void setUp() {
        QuestObjectiveDB.getQUEST_OBJECTIVE_DB().clear();
        FileService fileService = new FileService();
        fileService.importQuestsObjectiveFromFile();
        fileService.importCreaturesFromFile();
    }


    @Test
    void assertQuestObjectiveCompleted() {
        Hero hero = new Hero("Test");

        QuestObjective questObjective = QuestObjectiveDB.getQuestObjectiveById(1);
        QuestKillObjective questKillObjective = (QuestKillObjective) questObjective;

        // comment out next line in case you want to see prints
//        hero.getSaveGameEntities().getHeroQuestObjectives().addEntity(new HeroQuestObjective(questObjective.getId(), questObjective.isCompleted()));

        for (int i = 0; i < questKillObjective.getCountEnemyToKill(); i++) {
            hero.getEnemyKilled().addQuestEnemyKilled(questKillObjective.getQuestEnemyId());
            questKillObjective.printProgress(hero);
            questKillObjective.verifyQuestObjectiveCompletion(hero);
        }

        assertTrue(questObjective.isCompleted());
    }

    @Test
    void assertQuestObjectiveNotCompleted() {
        Hero hero = new Hero("Test");

        QuestObjective questObjective = QuestObjectiveDB.getQuestObjectiveById(1);
        QuestKillObjective questKillObjective = (QuestKillObjective) questObjective;

        // comment out next line in case you want to see prints
//        hero.getSaveGameEntities().getHeroQuestObjectives().addEntity(new HeroQuestObjective(questObjective.getId(), questObjective.isCompleted()));

        for (int i = 0; i < 3; i++) {
            hero.getEnemyKilled().addQuestEnemyKilled(questKillObjective.getQuestEnemyId());
            questKillObjective.printProgress(hero);
            questKillObjective.verifyQuestObjectiveCompletion(hero);
        }

        assertFalse(questObjective.isCompleted());
    }

    @Test
    void resetObjectiveProgressTest() {
        Hero hero = new Hero("Test");

        QuestObjective questObjective = QuestObjectiveDB.getQuestObjectiveById(1);
        QuestKillObjective questKillObjective = (QuestKillObjective) questObjective;

        for (int i = 0; i < questKillObjective.getCountEnemyToKill(); i++) {
            hero.getEnemyKilled().addQuestEnemyKilled(questKillObjective.getQuestEnemyId());
            questKillObjective.verifyQuestObjectiveCompletion(hero);
        }

        ((QuestKillObjective) questObjective).resetCompletedQuestObjectiveAssignment(hero);

        assertEquals(0, hero.getEnemyKilled().getAmountOfKilledEnemy(questKillObjective.getQuestEnemyId()));
    }

    @Test
    void resetObjectiveProgressWithMoreEnemiesTest() {
        Hero hero = new Hero("Test");

        QuestObjective questObjective = QuestObjectiveDB.getQuestObjectiveById(1);
        QuestKillObjective questKillObjective = (QuestKillObjective) questObjective;
        int increaseEnemyBy = 5;

        for (int i = 0; i < questKillObjective.getCountEnemyToKill() + increaseEnemyBy; i++) {
            hero.getEnemyKilled().addQuestEnemyKilled(questKillObjective.getQuestEnemyId());
            questKillObjective.verifyQuestObjectiveCompletion(hero);
        }

        questKillObjective.resetCompletedQuestObjectiveAssignment(hero);

        assertEquals(increaseEnemyBy, hero.getEnemyKilled().getAmountOfKilledEnemy(questKillObjective.getQuestEnemyId()));
    }
}





















