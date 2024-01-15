package kuchtastefan.characters.enemy;

import com.google.gson.Gson;
import kuchtastefan.ability.Ability;
import kuchtastefan.regions.locations.LocationType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnemyList {
    private static final List<Enemy> enemyList = new ArrayList<>();

//    public EnemyList() {
//        enemyList = new ArrayList<>();
//    }

    public static Map<String, Enemy> returnEnemyMap() {
        Map<String, Enemy> enemyMap = new HashMap<>();
        for (Enemy enemy : enemyList) {
            enemyMap.put(enemy.getName(), enemy);
        }

        return enemyMap;
    }

    private static Enemy returnEnemyWithNewCopy(Enemy enemy) {
        Gson gson = new Gson();
        Enemy newEnemy = gson.fromJson(gson.toJson(enemy), Enemy.class);
        newEnemy.itemsDrop();
        newEnemy.goldDrop();
        return newEnemy;
    }

    private static boolean checkEnemyLevelCondition(Enemy enemy, int maxEnemyLevel, Integer minEnemyLevel) {
        if (minEnemyLevel == null) {
            minEnemyLevel = maxEnemyLevel;
        }

        return maxEnemyLevel + 1 >= enemy.getLevel() && minEnemyLevel - 1 <= enemy.getLevel();
    }

    public static List<Enemy> returnEnemyListByLocationType(LocationType locationType) {
        List<Enemy> enemies = new ArrayList<>();
        for (Enemy enemy : enemyList) {
            for (LocationType locationTypeFromEnemy : enemy.getLocationType()) {
                if (locationTypeFromEnemy.equals(locationType)) {
                    enemies.add(returnEnemyWithNewCopy(enemy));
                }
            }
        }

        return enemies;
    }

    public static List<Enemy> returnEnemyListByLocationTypeAndLevel(LocationType locationType, int maxEnemyLevel, Integer minEnemyLevel) {
        List<Enemy> enemies = new ArrayList<>();
        for (Enemy enemy : returnEnemyListByLocationType(locationType)) {
            if (checkEnemyLevelCondition(enemy, maxEnemyLevel, minEnemyLevel)) {
                enemies.add(returnEnemyWithNewCopy(enemy));
            }
        }
        return enemies;
    }

    public static List<Enemy> returnStrongerEnemyListByLocationTypeAndLevel(LocationType locationType, int maxEnemyLevel, Integer minEnemyLevel, double multiplier) {
        List<Enemy> enemies = new ArrayList<>();
        for (Enemy enemy : returnEnemyListByLocationType(locationType)) {
            if (checkEnemyLevelCondition(enemy, maxEnemyLevel, minEnemyLevel)) {
                Enemy rareEnemy = returnEnemyWithNewCopy(enemy);
                enemies.add(increaseEnemyAbility(rareEnemy, multiplier));
            }
        }
        return enemies;
    }

    private static Enemy increaseEnemyAbility(Enemy enemy, double multiplier) {
        for (Ability ability : Ability.values()) {
            if (enemy.getAbilities().containsKey(ability)) {
                enemy.getAbilities().put(ability, (int) (enemy.getAbilities().get(ability) * multiplier));
            }
        }
        return enemy;
    }

    public static List<Enemy> getEnemyList() {
        return enemyList;
    }
}
