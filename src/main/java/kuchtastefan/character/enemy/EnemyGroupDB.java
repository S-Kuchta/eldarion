package kuchtastefan.character.enemy;

import kuchtastefan.character.npc.CharacterDB;
import kuchtastefan.region.location.LocationType;
import kuchtastefan.utility.RandomNumberGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnemyGroupDB {

    public static final List<EnemyGroup> ENEMY_GROUP_DB = new ArrayList<>();

    public static List<Enemy> returnEnemyGroupByLocationTypeAndHeroLevel(LocationType locationType, int heroLevel) {
        List<EnemyGroup> enemyGroups = new ArrayList<>();

        for (EnemyGroup enemyGroup : ENEMY_GROUP_DB) {
            if (enemyGroup.getLocationType().equals(locationType) && enemyGroup.getGroupLevel() <= heroLevel) {
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
