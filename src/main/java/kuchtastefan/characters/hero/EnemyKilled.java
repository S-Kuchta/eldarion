package kuchtastefan.characters.hero;

import kuchtastefan.quest.questObjectives.QuestEnemy;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class EnemyKilled {

    private final Map<String, Integer> enemyKilled;
    private final Map<QuestEnemy, Integer> questEnemyKilled;


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
}
