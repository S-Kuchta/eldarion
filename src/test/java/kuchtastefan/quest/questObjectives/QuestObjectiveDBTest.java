package kuchtastefan.quest.questObjectives;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.hero.save.quest.HeroQuestObjective;
import kuchtastefan.quest.questObjectives.specificQuestObjectives.QuestKillObjective;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestObjectiveDBTest {

    @BeforeEach
    void setUp() {
        QuestObjectiveDB.getQUEST_OBJECTIVE_DB().clear();
    }

    @Test
    void addQuestObjectiveToDB() {
        QuestKillObjective questObjective = new QuestKillObjective("Enemy name", 1, 1, 1);
        QuestObjectiveDB.addQuestObjectiveToDB(questObjective);
        assertEquals(1, QuestObjectiveDB.getQUEST_OBJECTIVE_DB().size());
    }

    @Test
    void getQuestObjectiveListByIds() {
    }

    @Test
    void getQuestObjectiveById() {
        QuestKillObjective questObjective = new QuestKillObjective("Enemy name", 1, 1, 1);
        QuestObjectiveDB.addQuestObjectiveToDB(questObjective);
        assertEquals(questObjective, QuestObjectiveDB.getQuestObjectiveById(1));
    }

    @Test
    void syncWithSaveGame() {
        Hero hero = new Hero("Test");
        hero.getSaveGameEntities().getHeroQuestObjectives().addEntity(new HeroQuestObjective(1, true));
        QuestKillObjective questObjective = new QuestKillObjective("Enemy name", 1, 1, 1);
        QuestObjectiveDB.addQuestObjectiveToDB(questObjective);

        QuestObjectiveDB.syncWithSaveGame(hero.getSaveGameEntities().getHeroQuestObjectives());
        assertTrue(QuestObjectiveDB.getQuestObjectiveById(1).isCompleted());
    }
}