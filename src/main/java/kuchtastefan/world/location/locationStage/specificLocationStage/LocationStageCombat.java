package kuchtastefan.world.location.locationStage.specificLocationStage;

import kuchtastefan.character.npc.enemy.Enemy;
import kuchtastefan.character.npc.CharacterDB;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.world.event.specificEvent.CombatEvent;
import kuchtastefan.world.location.Location;
import kuchtastefan.world.location.locationStage.LocationStage;
import kuchtastefan.world.location.locationStage.RemoveLocationStageProgress;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocationStageCombat extends LocationStage implements RemoveLocationStageProgress {

    private final Integer[] locationEnemyIdList;


    public LocationStageCombat(String stageName, Integer[] locationEnemyIdList) {
        super(stageName);
        this.locationEnemyIdList = locationEnemyIdList;
    }

    private List<Enemy> returnLocationEnemies() {
        List<Enemy> enemies = new ArrayList<>();
        for (Integer enemyId : this.locationEnemyIdList) {
            enemies.add(CharacterDB.returnNewEnemy(enemyId));
        }

        return enemies;
    }

    @Override
    public boolean exploreStage(Hero hero, Location location) {
        return new CombatEvent(location.getLocationLevel(), this.returnLocationEnemies()).eventOccurs(hero);
    }

    @Override
    public void removeProgressAfterCompletedStage() {
        Arrays.fill(this.locationEnemyIdList, null);
    }
}
