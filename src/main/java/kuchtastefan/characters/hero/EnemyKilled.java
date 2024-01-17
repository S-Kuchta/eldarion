package kuchtastefan.characters.hero;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
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

    public void removeQuestEnemyKilled(String enemy) {
        this.questEnemyKilled.remove(enemy);
    }

    public boolean checkIfHeroContainsEnoughQuestEnemyKilled(String enemyName, int numberOfEnemies) {
        return this.questEnemyKilled.containsKey(enemyName) && this.questEnemyKilled.get(enemyName) >= numberOfEnemies;
    }
}
