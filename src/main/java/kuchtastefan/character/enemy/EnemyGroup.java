package kuchtastefan.character.enemy;

import kuchtastefan.character.npc.CharacterDB;
import kuchtastefan.region.location.LocationType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class EnemyGroup {

    private final LocationType locationType;
    private final int groupLevel;
    private final int[] enemiesId;

    public EnemyGroup(LocationType locationType, int groupLevel, int[] enemiesId) {
        this.locationType = locationType;
        this.groupLevel = groupLevel;
        this.enemiesId = enemiesId;
    }

    public List<Enemy> convertEnemyIdToEnemyList() {
        List<Enemy> enemies = new ArrayList<>();

        for (int enemyId : this.enemiesId) {
            enemies.add(CharacterDB.returnNewEnemy(enemyId));
        }

        return enemies;
    }
}
