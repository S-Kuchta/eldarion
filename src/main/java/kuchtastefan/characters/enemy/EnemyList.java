package kuchtastefan.characters.enemy;

import com.google.gson.Gson;
import kuchtastefan.regions.locations.LocationType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnemyList {
    private final List<Enemy> enemyList;

    public EnemyList() {
        this.enemyList = new ArrayList<>();
    }

    public Map<String, Enemy> returnEnemyMap() {
        Map<String, Enemy> enemyMap = new HashMap<>();
        for (Enemy enemy : this.enemyList) {
            enemyMap.put(enemy.getName(), enemy);
        }

        return enemyMap;
    }

    private Enemy returnEnemyWithNewCopy(Enemy enemy) {
        Gson gson = new Gson();
        Enemy newEnemy = gson.fromJson(gson.toJson(enemy), Enemy.class);
        newEnemy.itemsDrop();
        newEnemy.goldDrop();
        return newEnemy;
    }

    public List<Enemy> returnEnemyListByLocationType(LocationType locationType) {
        List<Enemy> enemies = new ArrayList<>();
        for (Enemy enemy : this.enemyList) {
            for (LocationType locationTypeFromEnemy : enemy.getLocationType()) {
                if (locationTypeFromEnemy.equals(locationType)) {
                    enemies.add(returnEnemyWithNewCopy(enemy));
                }
            }
        }

        return enemies;
    }

    public List<Enemy> returnEnemyListByLocationTypeAndLevel(LocationType locationType, int maxEnemyLevel, Integer minEnemyLevel) {
        List<Enemy> enemies = new ArrayList<>();
        for (Enemy enemy : returnEnemyListByLocationType(locationType)) {
            if (checkEnemyLevelCondition(enemy, maxEnemyLevel, minEnemyLevel)) {
                enemies.add(returnEnemyWithNewCopy(enemy));
            }
        }
        return enemies;
    }

    private boolean checkEnemyLevelCondition(Enemy enemy, int maxEnemyLevel, Integer minEnemyLevel) {
        if (minEnemyLevel == null) {
            minEnemyLevel = maxEnemyLevel;
        }

        return maxEnemyLevel + 1 >= enemy.getLevel() && minEnemyLevel - 1 <= enemy.getLevel();
    }

    public List<Enemy> getEnemyList() {
        return enemyList;
    }
}
