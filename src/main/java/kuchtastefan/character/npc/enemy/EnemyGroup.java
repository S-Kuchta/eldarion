package kuchtastefan.character.npc.enemy;

import kuchtastefan.character.npc.CharacterDB;
import kuchtastefan.world.Biome;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class EnemyGroup {

    private final Biome biome;
    private final int groupLevel;
    private final int[] enemiesId;

    public EnemyGroup(Biome biome, int groupLevel, int[] enemiesId) {
        this.biome = biome;
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
