package kuchtastefan.regions.locations;

import kuchtastefan.characters.enemy.Enemy;
import kuchtastefan.characters.enemy.EnemyList;
import kuchtastefan.characters.hero.Hero;
import kuchtastefan.regions.events.CombatEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocationStageCombat extends LocationStage {

    private final Integer[] locationEnemyIdList;


    public LocationStageCombat(String stageName, Integer[] locationEnemyIdList) {
        super(stageName);
        this.locationEnemyIdList = locationEnemyIdList;
    }

    public List<Enemy> returnLocationEnemies() {
        List<Enemy> enemies = new ArrayList<>();
        for (Integer enemyId : this.locationEnemyIdList) {
            enemies.add(EnemyList.returnNewEnemyCopy(enemyId));
        }

        return enemies;
    }

    @Override
    public boolean exploreStage(Hero hero, Location location) {
        return new CombatEvent(location.getLocationLevel(), this.returnLocationEnemies(),
                location.getLocationType(), 1, 1).eventOccurs(hero);
    }

    @Override
    public void completeStage() {
        Arrays.fill(this.locationEnemyIdList, null);
    }
}
