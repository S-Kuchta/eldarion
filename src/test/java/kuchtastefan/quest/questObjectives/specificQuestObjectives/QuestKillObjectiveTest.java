package kuchtastefan.quest.questObjectives.specificQuestObjectives;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.QuestObjectiveDB;
import kuchtastefan.service.FileService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class QuestKillObjectiveTest {

    @BeforeAll
    static void setUp() {
        FileService fileService = new FileService();
        fileService.importQuestsObjectiveFromFile();
        fileService.importCreaturesFromFile();
    }

    @Test
    void assertQuestCompleted() {
        Hero hero = new Hero("Test");

        QuestObjective questObjective = QuestObjectiveDB.getQuestObjectiveById(1);
        QuestKillObjective questKillObjective = (QuestKillObjective) questObjective;
//        HeroQuestObjective heroQuestObjective = new HeroQuestObjective(questObjective.getId(), questObjective.isCompleted());
//        hero.getSaveGameEntities().getHeroQuestObjectives().addEntity(heroQuestObjective);

        for (int i = 0; i < questKillObjective.getCountEnemyToKill(); i++) {
            hero.getEnemyKilled().addQuestEnemyKilled(questKillObjective.getQuestEnemyId());
            questKillObjective.printProgress(hero);
            questKillObjective.verifyQuestObjectiveCompletion(hero);
        }

        assertTrue(questObjective.isCompleted());
    }
}