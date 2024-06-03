package kuchtastefan.quest.questObjectives.specificQuestObjectives;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.QuestObjectiveDB;
import kuchtastefan.service.FileService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class QuestBringItemObjectiveTest {

    @BeforeAll
    static void setUp() {
        FileService fileService = new FileService();
        fileService.importQuestsObjectiveFromFile();
        fileService.importQuestItemsFromFile();
    }

    @Test
    void assertQuestCompleted() {
        Hero hero = new Hero("Test");

        QuestObjective questObjective = QuestObjectiveDB.getQuestObjectiveById(7);
        QuestBringItemObjective questBringItemObjective = (QuestBringItemObjective) questObjective;
        Item item = ItemDB.returnItemFromDB(questBringItemObjective.getItemId());

        hero.getHeroInventory().addItemToInventory(item, questBringItemObjective.getItemCountNeeded());
        questBringItemObjective.printProgress(hero);
        questBringItemObjective.verifyQuestObjectiveCompletion(hero);

        assertTrue(questObjective.isCompleted());
    }

}