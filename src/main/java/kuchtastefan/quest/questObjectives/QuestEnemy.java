package kuchtastefan.quest.questObjectives;

import kuchtastefan.characters.enemy.EnemyRarity;
import lombok.Getter;

import java.util.Objects;

@Getter
public class QuestEnemy {
    private final String questEnemyName;
    private final EnemyRarity questEnemyRarity;

    public QuestEnemy(String questEnemyName, EnemyRarity questEnemyRarity) {
        this.questEnemyName = questEnemyName;
        this.questEnemyRarity = questEnemyRarity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestEnemy that = (QuestEnemy) o;
        return Objects.equals(questEnemyName, that.questEnemyName) && questEnemyRarity == that.questEnemyRarity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(questEnemyName, questEnemyRarity);
    }
}
