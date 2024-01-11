package kuchtastefan.characters.hero;

import kuchtastefan.characters.enemy.Enemy;

import java.util.HashMap;
import java.util.Map;

public class EnemyKilled {

    private final Map<String, Integer> enemyKilled;
    private final Map<String, Integer> questEnemyKilled;

    public EnemyKilled() {
        this.enemyKilled = new HashMap<>();
        this.questEnemyKilled = new HashMap<>();
    }

    public void addEnemyKilled(String enemyName) {
        if (this.enemyKilled.containsKey(enemyName)) {
            this.enemyKilled.put(enemyName, this.enemyKilled.get(enemyName) + 1);
        } else {
            this.enemyKilled.put(enemyName, 1);
        }
    }

    public void addQuestEnemyKilled(String enemyName) {
        if (this.questEnemyKilled.containsKey(enemyName)) {
            this.questEnemyKilled.put(enemyName, this.questEnemyKilled.get(enemyName) + 1);
        } else {
            this.questEnemyKilled.put(enemyName, 1);
        }
    }

    public void removeQuestEnemyKilled(Enemy enemy) {
        this.questEnemyKilled.remove(enemy.getName());
    }

    public boolean checkIfContainsEnoughQuestEnemyKilled(String enemyName, int numberOfEnemies) {
        return this.questEnemyKilled.containsKey(enemyName) && this.questEnemyKilled.get(enemyName) >= numberOfEnemies;
    }

    public Map<String, Integer> getEnemyKilled() {
        return enemyKilled;
    }

    public Map<String, Integer> getQuestEnemyKilled() {
        return questEnemyKilled;
    }
}
