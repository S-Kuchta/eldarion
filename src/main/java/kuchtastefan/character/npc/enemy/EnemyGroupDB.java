package kuchtastefan.character.npc.enemy;

import kuchtastefan.character.npc.CharacterDB;
import kuchtastefan.world.Biome;
import kuchtastefan.utility.RandomNumberGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnemyGroupDB {

    public static final List<EnemyGroup> ENEMY_GROUP_DB = new ArrayList<>();

    public static List<Enemy> returnEnemyGroupByBiomeAndHeroLevel(Biome biome, int heroLevel) {
        List<EnemyGroup> enemyGroups = new ArrayList<>();

        for (EnemyGroup enemyGroup : ENEMY_GROUP_DB) {
            if (enemyGroup.getBiome().equals(biome) && enemyGroup.getGroupLevel() <= heroLevel) {
                enemyGroups.add(enemyGroup);
            }
        }

        try {
            return enemyGroups.get(RandomNumberGenerator.getRandomNumber(0, enemyGroups.size() - 1)).convertEnemyIdToEnemyList();
        } catch (IllegalArgumentException e) {
            return Collections.singletonList(CharacterDB.returnNewEnemy(200));
        }
    }
}
