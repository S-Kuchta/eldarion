package kuchtastefan.quest;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.hero.save.quest.HeroQuest;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.QuestObjectiveDB;
import kuchtastefan.quest.questObjectives.specificQuestObjectives.QuestKillObjective;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestDBTest {


    @BeforeEach
    void setUp() {
        QuestObjectiveDB.getQUEST_OBJECTIVE_DB().clear();
        QuestDB.getQUEST_DB().clear();
    }

    @Test
    void addQuestToDB() {
        QuestReward reward = new QuestReward(new Integer[]{1}, 100, 100);
        QuestObjectiveDB.addQuestObjectiveToDB(new QuestKillObjective("Enemy name", 1, 1, 1));
        Quest quest = new Quest(1, "Test Quest", "Test Description", 1, new int[]{1}, reward, false);

        QuestDB.addQuestToDB(quest);
        assertEquals(1, QuestDB.getQUEST_DB().size());
    }

    @Test
    void getQuestListByIdsSize() {
        QuestReward reward = new QuestReward(new Integer[]{1}, 100, 100);
        QuestObjectiveDB.addQuestObjectiveToDB(new QuestKillObjective("Enemy name", 1, 1, 1));
        Quest quest = new Quest(1, "Test Quest", "Test Description", 1, new int[]{1}, reward, false);
        Quest quest2 = new Quest(2, "Test Quest", "Test Description", 1, new int[]{1}, reward, false);
        QuestDB.addQuestToDB(quest);
        QuestDB.addQuestToDB(quest2);
        assertEquals(2, QuestDB.getQUEST_DB().size());
    }

    @Test
    void getQuestById() {
        QuestReward reward = new QuestReward(new Integer[]{1}, 100, 100);
        QuestObjectiveDB.addQuestObjectiveToDB(new QuestKillObjective("Enemy name", 1, 1, 1));
        Quest quest = new Quest(1, "Test Quest", "Test Description", 1, new int[]{1}, reward, false);
        QuestDB.addQuestToDB(quest);
        Quest returnedQuest = QuestDB.getQuestById(1);
        assertEquals(quest, returnedQuest);
    }

    @Test
    void getQuestByIdException() {
        QuestObjectiveDB.addQuestObjectiveToDB(
                new QuestKillObjective("Enemy name", 1, 1, 1));

        Quest quest = new Quest(1, "Test Quest", "Test Description", 1, new int[]{1},
                new QuestReward(new Integer[]{1}, 100, 100), false);
        QuestDB.addQuestToDB(quest);

        assertThrows(IllegalArgumentException.class, () -> QuestDB.getQuestById(quest.getId() + 1));
    }

    @Test
    void syncWithSaveGameEquals() {
        QuestReward reward = new QuestReward(new Integer[]{1}, 100, 100);
        Hero hero = new Hero("Test");
        QuestObjectiveDB.addQuestObjectiveToDB(new QuestKillObjective("Enemy name", 1, 1, 1));
        Quest quest = new Quest(1, "Test Quest", "Test Description", 1, new int[]{1}, reward, false);
        QuestDB.addQuestToDB(quest);
        quest.setInitialQuestStatus(hero);
        HeroQuest heroQuest = new HeroQuest(1, QuestStatus.ACCEPTED);
        hero.getSaveGameEntities().getHeroQuests().addEntity(heroQuest);
        QuestDB.syncWithSaveGame(hero.getSaveGameEntities().getHeroQuests());
        assertEquals(QuestStatus.ACCEPTED, QuestDB.getQuestById(1).getStatus());
    }

    @Test
    void syncWithSaveGameNotEquals() {
        QuestReward reward = new QuestReward(new Integer[]{1}, 100, 100);
        Hero hero = new Hero("Test");
        QuestObjectiveDB.addQuestObjectiveToDB(new QuestKillObjective("Enemy name", 1, 1, 1));
        Quest quest = new Quest(1, "Test Quest", "Test Description", 1, new int[]{1}, reward, false);
        QuestDB.addQuestToDB(quest);
        quest.setInitialQuestStatus(hero);
        HeroQuest heroQuest = new HeroQuest(1, QuestStatus.ACCEPTED);
        hero.getSaveGameEntities().getHeroQuests().addEntity(heroQuest);
        QuestDB.syncWithSaveGame(hero.getSaveGameEntities().getHeroQuests());
        assertNotEquals(QuestStatus.AVAILABLE, QuestDB.getQuestById(1).getStatus());
    }

    @Test
    void findQuestByObjectiveId() {
        QuestObjectiveDB.addQuestObjectiveToDB(
                new QuestKillObjective("Enemy name", 1, 1, 1));

        Quest quest = new Quest(1, "Test Quest", "Test Description", 1, new int[]{1},
                new QuestReward(new Integer[]{1}, 100, 100), false);
        QuestDB.addQuestToDB(quest);

        Quest findedQuest = QuestDB.findQuestByObjectiveId(1);
        assertEquals(quest, findedQuest);
    }


}