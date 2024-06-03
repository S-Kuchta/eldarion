package kuchtastefan.quest.questObjectives.specificQuestObjectives;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.quest.Quest;
import kuchtastefan.quest.QuestDB;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.QuestObjectiveDB;
import kuchtastefan.service.FileService;
import kuchtastefan.world.location.Location;
import kuchtastefan.world.location.LocationDB;
import kuchtastefan.world.location.QuestLocation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuestClearLocationObjectiveTest {

    @BeforeAll
    static void setUp() {
        FileService fileService = new FileService();
        fileService.importLocationsFromFile();
        fileService.importQuestsObjectiveFromFile();
        fileService.importQuestsListFromFile();
    }

    @Test
    void verifyQuestObjectiveCompletionTrue() {
        Hero hero = new Hero("Test");

        List<Quest> quests = new ArrayList<>(QuestDB.getQUEST_DB().values());
        QuestDB.setInitialQuestsStatusFromGivenList(hero, quests);

        Location questLocation = LocationDB.returnLocation(2);
        QuestObjective questObjective = QuestObjectiveDB.getQuestObjectiveById(((QuestLocation) questLocation).getQuestObjectiveId());

        hero.getDiscoveredLocationList().put(questLocation.getLocationId(), questLocation);
        hero.getDiscoveredLocationList().get(questLocation.getLocationId()).setCleared(hero, true);

        assertTrue(questObjective.isCompleted());
    }

    @Test
    void verifyQuestObjectiveCompletionFalse() {
        Hero hero = new Hero("Test");

        FileService fileService = new FileService();
        fileService.importLocationsFromFile();
        fileService.importQuestsObjectiveFromFile();
        fileService.importQuestsListFromFile();

        List<Quest> quests = new ArrayList<>(QuestDB.getQUEST_DB().values());
        QuestDB.setInitialQuestsStatusFromGivenList(hero, quests);

        Location questLocation = LocationDB.returnLocation(2);
        QuestObjective questObjective = QuestObjectiveDB.getQuestObjectiveById(((QuestLocation) questLocation).getQuestObjectiveId());

        hero.getDiscoveredLocationList().put(questLocation.getLocationId(), questLocation);
        hero.getDiscoveredLocationList().get(questLocation.getLocationId()).setCleared(hero, false);

        assertFalse(questObjective.isCompleted());
    }
}