package kuchtastefan.character.hero;

import kuchtastefan.character.npc.enemy.QuestEnemy;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.QuestObjectiveDB;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class EnemyKilled {

    private final Map<Integer, Integer> enemyKilled;

    public EnemyKilled() {
        this.enemyKilled = new HashMap<>();
    }

    public void addQuestEnemyKilled(Hero hero, QuestEnemy questEnemy) {
        QuestObjective questObjective = QuestObjectiveDB.getQuestObjectiveById(questEnemy.getQuestObjectiveId());
        if (hero.getSaveGameEntities().getHeroQuestObjectives().containsEntity(questEnemy.getQuestObjectiveId())) {
            if (QuestObjectiveDB.getQuestObjectiveById(questEnemy.getQuestObjectiveId()).isCompleted()) {
                return;
            }

            if (containsEnemy(questEnemy.getNpcId())) {
                this.enemyKilled.put(questEnemy.getNpcId(), this.enemyKilled.get(questEnemy.getNpcId()) + 1);
            } else {
                this.enemyKilled.put(questEnemy.getNpcId(), 1);
            }

            questObjective.printQuestObjectiveProgress(hero);
            questObjective.verifyQuestObjectiveCompletion(hero);
        }
    }

    public boolean containsEnemy(int questEnemyId) {
        return this.enemyKilled.containsKey(questEnemyId);
    }

    public int getAmountOfKilledEnemy(QuestEnemy questEnemy) {
        this.enemyKilled.putIfAbsent(questEnemy.getNpcId(), 0);
        return this.enemyKilled.get(questEnemy.getNpcId());
    }
}
