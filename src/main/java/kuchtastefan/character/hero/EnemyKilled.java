package kuchtastefan.character.hero;

import kuchtastefan.character.npc.NonPlayerCharacter;
import kuchtastefan.character.npc.enemy.Enemy;
import kuchtastefan.character.npc.enemy.QuestEnemy;

import java.util.HashMap;
import java.util.Map;

public class EnemyKilled {

    private final Map<NonPlayerCharacter, Integer> enemyKilled;

    public EnemyKilled() {
        this.enemyKilled = new HashMap<>();
    }

    public void addQuestEnemyKilled(Hero hero, QuestEnemy questEnemy) {
        if (hero.getHeroQuests().containsQuestObjective(questEnemy.getQuestObjectiveId())) {
            if (this.enemyKilled.containsKey(questEnemy)) {
                this.enemyKilled.put(questEnemy, this.enemyKilled.get(questEnemy) + 1);
            } else {
                this.enemyKilled.put(questEnemy, 1);
            }

            hero.getHeroQuests().getQuestObjective(questEnemy.getQuestObjectiveId()).questObjectiveAssignment(hero);
        }
    }

    public boolean containsEnemy(Enemy enemy) {
        return this.enemyKilled.containsKey(enemy);
    }

    public int getAmountOfKilledEnemy(NonPlayerCharacter enemy) {
        this.enemyKilled.putIfAbsent(enemy, 0);
        return this.enemyKilled.get(enemy);
    }
}
