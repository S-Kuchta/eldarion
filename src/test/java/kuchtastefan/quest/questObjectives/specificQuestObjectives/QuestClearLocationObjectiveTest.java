package kuchtastefan.quest.questObjectives.specificQuestObjectives;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.QuestObjectiveDB;
import kuchtastefan.service.FileService;
import kuchtastefan.world.location.LocationDB;
import kuchtastefan.world.location.QuestLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuestClearLocationObjectiveTest {

    @BeforeEach
    void setUp() {
        QuestObjectiveDB.getQUEST_OBJECTIVE_DB().clear();
        FileService fileService = new FileService();
        fileService.importLocationsFromFile();
        fileService.importQuestsObjectiveFromFile();
    }

    @Test
    void verifyQuestObjectiveCompletionTrue() {
        Hero hero = new Hero("Test");
        QuestLocation questLocation = (QuestLocation) LocationDB.getLocationById(2);
        QuestObjective questObjective = QuestObjectiveDB.getQuestObjectiveById(questLocation.getQuestObjectiveId());
        questLocation.setCompleted();
        questObjective.verifyQuestObjectiveCompletion(hero);

        assertTrue(questObjective.isCompleted());
    }

    @Test
    void verifyQuestObjectiveCompletionFalse() {
        Hero hero = new Hero("Test");
        QuestLocation questLocation = (QuestLocation) LocationDB.getLocationById(2);
        QuestObjective questObjective = QuestObjectiveDB.getQuestObjectiveById(questLocation.getQuestObjectiveId());
        questLocation.setDiscovered();
        questObjective.verifyQuestObjectiveCompletion(hero);

        assertFalse(questObjective.isCompleted());
    }
}