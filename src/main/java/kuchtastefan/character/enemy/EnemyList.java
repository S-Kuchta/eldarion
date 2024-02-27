package kuchtastefan.character.enemy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kuchtastefan.character.CharacterRarity;
import kuchtastefan.regions.locations.LocationType;
import kuchtastefan.utility.RuntimeTypeAdapterFactoryUtil;
import lombok.Getter;

import java.util.*;

public class EnemyList {
    @Getter
    private static final List<Enemy> enemyList = new ArrayList<>();
    @Getter
    private static final Map<Integer, Enemy> enemyMap = new HashMap<>();


    public static Enemy returnEnemyWithNewCopy(Enemy enemy, CharacterRarity characterRarity) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.actionsRuntimeTypeAdapterFactory).create();

        Enemy newEnemy = gson.fromJson(gson.toJson(enemy), Enemy.class);
        newEnemy.addItemsDropFromEnemy();
        newEnemy.goldDrop();
        newEnemy.setCanPerformAction(true);

        if (newEnemy.getRegionActionsWithDuration() == null) {
            newEnemy.setRegionActionsWithDuration(new HashSet<>());
        }

        if (newEnemy.getBattleActionsWithDuration() == null) {
            newEnemy.setBattleActionsWithDuration(new HashSet<>());
        }

        if (!characterRarity.equals(CharacterRarity.COMMON)) {
            double multiplier = 0;
            if (characterRarity.equals(CharacterRarity.RARE)) {
                newEnemy.setCharacterRarity(CharacterRarity.RARE);
                multiplier = 1.4;
            } else if (characterRarity.equals(CharacterRarity.ELITE)) {
                newEnemy.setCharacterRarity(CharacterRarity.ELITE);
                multiplier = 1.7;
            }

            newEnemy.increaseAbilityPointsByMultiplier(multiplier);
            newEnemy.setGoldDrop(newEnemy.getGoldDrop() * multiplier);
        } else {
            newEnemy.setCharacterRarity(CharacterRarity.COMMON);
        }

        newEnemy.setMaxAbilitiesAndCurrentAbilities();

        return newEnemy;
    }

    public static Enemy returnNewEnemyCopy(int id) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.actionsRuntimeTypeAdapterFactory).create();

        Enemy newEnemy = gson.fromJson(gson.toJson(enemyMap.get(id)), Enemy.class);
        newEnemy.addItemsDropFromEnemy();
        newEnemy.goldDrop();
        newEnemy.setCanPerformAction(true);
        newEnemy.setMaxAbilitiesAndCurrentAbilities();

        return newEnemy;
    }

    public static List<Enemy> returnEnemyListByLocationType(LocationType locationType, CharacterRarity characterRarity) {
        List<Enemy> enemies = new ArrayList<>();
        for (Enemy enemy : enemyList) {
            for (LocationType locationTypeFromEnemy : enemy.getLocationType()) {
                if (locationTypeFromEnemy.equals(locationType)) {
                    enemies.add(returnEnemyWithNewCopy(enemy, characterRarity));
                }
            }
        }

        return enemies;
    }

    public static List<Enemy> returnEnemyListByLocationTypeAndLevel(LocationType locationType, int maxEnemyLevel, Integer minEnemyLevel, CharacterRarity characterRarity) {
        List<Enemy> enemies = new ArrayList<>();
        for (Enemy enemy : returnEnemyListByLocationType(locationType, characterRarity)) {
            if (checkEnemyLevelCondition(enemy, maxEnemyLevel, minEnemyLevel)) {
                enemies.add(enemy);
            }
        }
        return enemies;
    }

    private static boolean checkEnemyLevelCondition(Enemy enemy, int maxEnemyLevel, Integer minEnemyLevel) {
        if (minEnemyLevel == null) {
            minEnemyLevel = maxEnemyLevel;
        }

        return maxEnemyLevel + 1 >= enemy.getLevel() && minEnemyLevel - 1 <= enemy.getLevel();
    }
}
