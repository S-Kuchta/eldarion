package kuchtastefan.character.hero;

import kuchtastefan.character.npc.enemy.QuestEnemy;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class EnemyKilled {

    /**
     * Map<EnemyId, NumberOfEnemies>
     */
    private final Map<Integer, Integer> enemyKilled;

    public EnemyKilled() {
        this.enemyKilled = new HashMap<>();
    }

    public void addQuestEnemyKilled(int questEnemyId) {
        if (containsEnemy(questEnemyId)) {
            this.enemyKilled.put(questEnemyId, this.enemyKilled.get(questEnemyId) + 1);
        } else {
            this.enemyKilled.put(questEnemyId, 1);
        }
    }

    public void removeQuestEnemyKilled(int questEnemyId, int numberOfEnemies) {
        if (numberOfEnemies > this.getAmountOfKilledEnemy(questEnemyId)) {
            numberOfEnemies = this.getAmountOfKilledEnemy(questEnemyId);
        }

        this.enemyKilled.put(questEnemyId, this.enemyKilled.get(questEnemyId) - numberOfEnemies);
    }

    public boolean containsEnemy(int questEnemyId) {
        return this.enemyKilled.containsKey(questEnemyId);
    }

    public int getAmountOfKilledEnemy(int questEnemyId) {
        this.enemyKilled.putIfAbsent(questEnemyId, 0);
        return this.enemyKilled.get(questEnemyId);
    }
}
