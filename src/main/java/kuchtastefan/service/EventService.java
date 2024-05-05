package kuchtastefan.service;

import kuchtastefan.character.npc.enemy.Enemy;
import kuchtastefan.character.npc.enemy.EnemyGroupDB;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.utility.AutosaveCount;
import kuchtastefan.world.event.specificEvent.*;
import kuchtastefan.world.location.Location;
import kuchtastefan.world.Biome;
import kuchtastefan.utility.RandomNumberGenerator;
import lombok.Getter;

import java.util.List;

@Getter
public class EventService {

    private final List<Location> allLocations;


    public EventService(List<Location> allLocations) {
        this.allLocations = allLocations;
    }

    public void randomRegionEventGenerate(Hero hero, Biome biome) {
        AutosaveCount.checkAutosaveCount(hero);

        int randomNumber = RandomNumberGenerator.getRandomNumber(0, 4);
        int eventLevel = hero.getLevel();

        switch (randomNumber) {
            case 0 -> new MerchantEvent(eventLevel).eventOccurs(hero);
            case 1 -> {
                List<Enemy> enemyList = EnemyGroupDB.returnEnemyGroupByBiomeAndHeroLevel(biome, hero.getLevel());
                new CombatEvent(eventLevel, enemyList).eventOccurs(hero);
            }
            case 2 -> new DiscoverLocationEvent(eventLevel, this.allLocations).eventOccurs(hero);
            case 3 -> new GatherCraftingReagentItemEvent(eventLevel).eventOccurs(hero);
            default -> new NoOutcomeEvent(0).eventOccurs(hero);
        }

        hero.restoreHealthAndManaAfterTurn();
        hero.performActionsWithDuration(true);
    }
}
