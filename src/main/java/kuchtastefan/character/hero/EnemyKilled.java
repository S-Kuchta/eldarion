package kuchtastefan.character.hero;

import kuchtastefan.character.npc.enemy.QuestEnemy;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class EnemyKilled {

    private final Map<QuestEnemy, Integer> enemyKilled;

    public EnemyKilled() {
        this.enemyKilled = new HashMap<>();
    }

    public void addQuestEnemyKilled(Hero hero, QuestEnemy questEnemy) {
        if (hero.getHeroQuests().containsQuestObjective(questEnemy.getQuestObjectiveId())) {
            if (containsEnemy(questEnemy)) {
                this.enemyKilled.put(questEnemy, this.enemyKilled.get(questEnemy) + 1);
            } else {
                this.enemyKilled.put(questEnemy, 1);
            }

            hero.getHeroQuests().makeProgressInQuestObjective(hero, questEnemy.getQuestObjectiveId());
        }
    }

    public boolean containsEnemy(QuestEnemy questEnemy) {
        return this.enemyKilled.containsKey(questEnemy);
    }

    public int getAmountOfKilledEnemy(QuestEnemy questEnemy) {
        this.enemyKilled.putIfAbsent(questEnemy, 0);
        return this.enemyKilled.get(questEnemy);
    }
}
