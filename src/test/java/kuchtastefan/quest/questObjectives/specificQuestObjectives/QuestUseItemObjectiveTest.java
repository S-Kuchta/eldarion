package kuchtastefan.quest.questObjectives.specificQuestObjectives;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import kuchtastefan.item.specificItems.questItem.UsableQuestItem;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.QuestObjectiveDB;
import kuchtastefan.service.FileService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestUseItemObjectiveTest {

    @BeforeAll
    static void setUp() {
        FileService fileService = new FileService();
        fileService.importQuestsObjectiveFromFile();
        fileService.importQuestItemsFromFile();
    }

    @Test
    void assertQuestCompleted() {
        Hero hero = new Hero("Test");

        QuestObjective questObjective = QuestObjectiveDB.getQuestObjectiveById(8);
        QuestUseItemObjective questUseItemObjective = (QuestUseItemObjective) questObjective;
        Item item = ItemDB.returnItemFromDB(questUseItemObjective.getItemId());

        hero.getHeroInventory().addItemToInventory(item, 1);
        UsableQuestItem usableQuestItem = (UsableQuestItem) hero.getHeroInventory().getItemFromInventoryById(item.getItemId());
        usableQuestItem.useItem(hero);
        usableQuestItem.setWasUsed(true);

        questUseItemObjective.printProgress(hero);
        questUseItemObjective.verifyQuestObjectiveCompletion(hero);

        assertTrue(questObjective.isCompleted());
    }


}