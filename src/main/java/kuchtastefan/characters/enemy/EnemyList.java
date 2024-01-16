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

    public static Map<String, Enemy> returnEnemyMap() {
        Map<String, Enemy> enemyMap = new HashMap<>();
        for (Enemy enemy : enemyList) {
            enemyMap.put(enemy.getName(), enemy);
        }

        return enemyMap;
    }

    private static Enemy returnEnemyWithNewCopy(Enemy enemy, EnemyRarity enemyRarity) {
        Gson gson = new Gson();
        Enemy newEnemy = gson.fromJson(gson.toJson(enemy), Enemy.class);
        newEnemy.itemsDrop();
        newEnemy.goldDrop();

        if (!enemyRarity.equals(EnemyRarity.COMMON)) {
            double multiplier = 0;
            if (enemyRarity.equals(EnemyRarity.RARE)) {
                newEnemy.setEnemyRarity(EnemyRarity.RARE);
                multiplier = 1.4;
            } else if (enemyRarity.equals(EnemyRarity.ELITE)) {
                newEnemy.setEnemyRarity(EnemyRarity.ELITE);
                multiplier = 1.7;
            }
            for (Ability ability : Ability.values()) {
                if (newEnemy.getAbilities().containsKey(ability)) {
                    newEnemy.getAbilities().put(ability, (int) (newEnemy.getAbilities().get(ability) * multiplier));
                }
            }
        } else {
            newEnemy.setEnemyRarity(EnemyRarity.COMMON);
        }

//        newEnemy.setName(newEnemy.getName() + " - " + enemyRarity.name() + " -");
        return newEnemy;
    }

    private static boolean checkEnemyLevelCondition(Enemy enemy, int maxEnemyLevel, Integer minEnemyLevel) {
        if (minEnemyLevel == null) {
            minEnemyLevel = maxEnemyLevel;
        }

        return maxEnemyLevel + 1 >= enemy.getLevel() && minEnemyLevel - 1 <= enemy.getLevel();
    }

    public static List<Enemy> returnEnemyListByLocationType(LocationType locationType, EnemyRarity enemyRarity) {
        List<Enemy> enemies = new ArrayList<>();
        for (Enemy enemy : enemyList) {
            for (LocationType locationTypeFromEnemy : enemy.getLocationType()) {
                if (locationTypeFromEnemy.equals(locationType)) {
                    enemies.add(returnEnemyWithNewCopy(enemy, enemyRarity));
                }
            }
        }

        return enemies;
    }

    public static List<Enemy> returnEnemyListByLocationTypeAndLevel(LocationType locationType, int maxEnemyLevel, Integer minEnemyLevel, EnemyRarity enemyRarity) {
        List<Enemy> enemies = new ArrayList<>();
        for (Enemy enemy : returnEnemyListByLocationType(locationType, enemyRarity)) {
            if (checkEnemyLevelCondition(enemy, maxEnemyLevel, minEnemyLevel)) {
//                enemies.add(returnEnemyWithNewCopy(enemy, enemyRarity));
                enemies.add(enemy);
            }
        }
        return enemies;
    }

    public static List<Enemy> getEnemyList() {
        return enemyList;
    }
}
