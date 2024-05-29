package kuchtastefan.character.hero;

import kuchtastefan.character.npc.enemy.QuestEnemy;
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
        if (hero.getHeroQuests().containsQuestObjective(questEnemy.getQuestObjectiveId())) {
            if (containsEnemy(questEnemy.getNpcId())) {
                this.enemyKilled.put(questEnemy.getNpcId(), this.enemyKilled.get(questEnemy.getNpcId()) + 1);
            } else {
                this.enemyKilled.put(questEnemy.getNpcId(), 1);
            }

            hero.getHeroQuests().updateQuestObjectiveProgress(hero, questEnemy.getQuestObjectiveId());
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
