package kuchtastefan.quest.questObjectives;

import kuchtastefan.characters.enemy.EnemyRarity;
import lombok.Getter;

@Getter
public class QuestEnemy {
    private final String questEnemyName;
    private final EnemyRarity enemyRarity;

    public QuestEnemy(String questEnemyName, EnemyRarity enemyRarity) {
        this.questEnemyName = questEnemyName;
        this.enemyRarity = enemyRarity;
    }
}
