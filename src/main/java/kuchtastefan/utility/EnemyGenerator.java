package kuchtastefan.utility;

import kuchtastefan.ability.Ability;
import kuchtastefan.domain.Enemy;

import java.util.HashMap;
import java.util.Map;

public class EnemyGenerator {
    public static Map<Integer, Enemy> createEnemies() {
        Map<Integer, Enemy> enemies = new HashMap<>();

        enemies.put(
                1, new Enemy("Goblin", new HashMap<>(Map.of(
                        Ability.ATTACK, 3,
                        Ability.DEFENCE, 1,
                        Ability.DEXTERITY, 2,
                        Ability.SKILL, 2,
                        Ability.LUCK, 4,
                        Ability.HEALTH, 38
                )))
        );
        enemies.put(
                2, new Enemy("Orc", new HashMap<>(Map.of(
                        Ability.ATTACK, 3,
                        Ability.DEFENCE, 1,
                        Ability.DEXTERITY, 4,
                        Ability.SKILL, 3,
                        Ability.LUCK, 1,
                        Ability.HEALTH, 35
                )))
        );
        enemies.put(
                3, new Enemy("Golem", new HashMap<>(Map.of(
                        Ability.ATTACK, 4,
                        Ability.DEFENCE, 4,
                        Ability.DEXTERITY, 1,
                        Ability.SKILL, 2,
                        Ability.LUCK, 5,
                        Ability.HEALTH, 50
                )))
        );
        enemies.put(
                4, new Enemy("Troll", new HashMap<>(Map.of(
                        Ability.ATTACK, 5,
                        Ability.DEFENCE, 5,
                        Ability.DEXTERITY, 1,
                        Ability.SKILL, 1,
                        Ability.LUCK, 1,
                        Ability.HEALTH, 60
                )))
        );
        enemies.put(
                5, new Enemy("Dragon", new HashMap<>(Map.of(
                        Ability.ATTACK, 5,
                        Ability.DEFENCE, 5,
                        Ability.DEXTERITY, 5,
                        Ability.SKILL, 5,
                        Ability.LUCK, 5,
                        Ability.HEALTH, 60
                )))
        );

        return enemies;
    }
}
