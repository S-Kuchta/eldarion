package kuchtastefan.quest;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.QuestObjectiveDB;
import kuchtastefan.service.FileService;
import kuchtastefan.world.location.Location;
import kuchtastefan.world.location.LocationDB;
import kuchtastefan.world.location.QuestLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestTest {

    @BeforeEach
    void setUp() {
        QuestDB.getQUEST_DB().clear();
        FileService fileService = new FileService();
        fileService.importLocationsFromFile();
        fileService.importQuestsObjectiveFromFile();
        fileService.importQuestsListFromFile();
        fileService.importQuestItemsFromFile();
        fileService.importCreaturesFromFile();
    }

    @Test
    void startTheQuestQuestStatusShouldBeAccepted() {
        Hero hero = new Hero("Test");
        Quest quest = QuestDB.getQuestById(1);

        List<Quest> quests = new ArrayList<>(QuestDB.getQUEST_DB().values());
        QuestDB.setInitialQuestsStatusFromGivenList(hero, quests);

        quest.startTheQuest(hero);
        assertEquals(QuestStatus.ACCEPTED, quest.getStatus());
    }

    @Test
    void containsQuestObjective() {
        Quest quest = QuestDB.getQuestById(1);
        QuestObjective questObjective = QuestObjectiveDB.getQuestObjectiveById(3);

        assertTrue(quest.containsQuestObjective(questObjective.getId()));
    }

    @Test
    void checkIfQuestIsCompleted() {
        Hero hero = new Hero("Test");
        Quest quest = QuestDB.getQuestById(1);
        quest.setStatus(QuestStatus.ACCEPTED);

        List<Quest> quests = new ArrayList<>(QuestDB.getQUEST_DB().values());
        QuestDB.setInitialQuestsStatusFromGivenList(hero, quests);

        QuestLocation questLocation = (QuestLocation) LocationDB.getLocationById(2);
        QuestObjective questObjective = QuestObjectiveDB.getQuestObjectiveById(questLocation.getQuestObjectiveId());
        questLocation.setCompleted();

        questObjective.verifyQuestObjectiveCompletion(hero);

        quest.checkIfQuestIsCompleted(hero);
        assertEquals(QuestStatus.COMPLETED, quest.getStatus());
    }

    @Test
    void canBeQuestAcceptedByLevelShouldReturnFalse() {
        Hero hero = new Hero("Test");
        hero.setLevel(1);
        Quest quest = QuestDB.getQuestById(3);

        assertFalse(quest.canBeQuestAccepted(hero));
    }

    @Test
    void canBeQuestAcceptedByLevelShouldReturnTrue() {
        Hero hero = new Hero("Test");
        hero.setLevel(2);
        Quest quest = QuestDB.getQuestById(3);

        assertTrue(quest.canBeQuestAccepted(hero));
    }

    @Test
    void isQuestChainInstanceOfQuestChain() {
        Quest quest = QuestDB.getQuestById(7);
        assertInstanceOf(QuestChain.class, quest);
    }

    @Test
    void canBeQuestAcceptedByPreviousQuestShouldReturnFalse() {
        Hero hero = new Hero("Test");
        Quest previousQuest = QuestDB.getQuestById(6);
        Quest chainQuest = QuestDB.getQuestById(7);

        previousQuest.setStatus(QuestStatus.ACCEPTED);
        assertFalse(chainQuest.canBeQuestAccepted(hero));
    }

    @Test
    void canBeQuestAcceptedByPreviousQuestShouldReturnTrue() {
        Hero hero = new Hero("Test");
        Quest previousQuest = QuestDB.getQuestById(6);

        // Quest with id 7 is QuestChain and have Quest with ID 6 as a previous Quest
        Quest chainQuest = QuestDB.getQuestById(7);

        previousQuest.setStatus(QuestStatus.TURNED_IN);
        assertTrue(chainQuest.canBeQuestAccepted(hero));
    }
}