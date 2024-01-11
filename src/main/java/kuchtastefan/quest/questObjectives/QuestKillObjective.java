package kuchtastefan.quest.questObjectives;

import kuchtastefan.characters.enemy.Enemy;
import kuchtastefan.characters.hero.Hero;

import java.util.Map;

public class QuestKillObjective extends QuestObjective {
    private final String enemyToKill;
    private final int countEnemyToKill;

    public QuestKillObjective(String enemyToKill, int countEnemyToKill) {
        this.enemyToKill = enemyToKill;
        this.countEnemyToKill = countEnemyToKill;
    }

    @Override
    public void checkCompleted(Hero hero) {
        if (hero.getEnemyKilled().checkIfContainsEnoughQuestEnemyKilled(this.enemyToKill, this.countEnemyToKill)) {
            setCompleted(true);
        }
    }
}
