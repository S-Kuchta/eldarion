package kuchtastefan.quest;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.QuestObjectiveDB;
import kuchtastefan.quest.questObjectives.specificQuestObjectives.QuestKillObjective;
import kuchtastefan.service.FileService;
import kuchtastefan.world.location.Location;
import kuchtastefan.world.location.LocationDB;
import kuchtastefan.world.location.QuestLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
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

        List<Quest> quests = new ArrayList<>(QuestDB.getQUEST_DB().values());
        QuestDB.setInitialQuestsStatusFromGivenList(hero, quests);

        Location questLocation = LocationDB.returnLocation(2);
        QuestObjective questObjective = QuestObjectiveDB.getQuestObjectiveById(((QuestLocation) questLocation).getQuestObjectiveId());

        hero.getDiscoveredLocationList().put(questLocation.getLocationId(), questLocation);
        hero.getDiscoveredLocationList().get(questLocation.getLocationId()).setCompleted();
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

    // TODO - write test after adding chain quest
    @Test
    void canBeQuestAcceptedByPreviousQuestShouldReturnFalse() {

    }

    // TODO - write test after adding chain quest
    @Test
    void canBeQuestAcceptedByPreviousQuestShouldReturnTrue() {

    }
}