package kuchtastefan.regions.events;

import kuchtastefan.characters.enemy.Enemy;
import kuchtastefan.characters.enemy.EnemyList;
import kuchtastefan.characters.enemy.EnemyRarity;
import kuchtastefan.characters.hero.Hero;
import kuchtastefan.regions.locations.Location;
import kuchtastefan.regions.locations.LocationType;
import kuchtastefan.utility.RandomNumberGenerator;
import lombok.Getter;

import java.util.List;

@Getter
public class EventService {

    private final List<Location> allLocations;
    private final List<Location> discoveredLocations;

    public EventService(List<Location> allLocations, List<Location> discoveredLocations) {
        this.allLocations = allLocations;
        this.discoveredLocations = discoveredLocations;
    }

    public void randomRegionEventGenerate(Hero hero, LocationType locationType) {
        int randomNumber = RandomNumberGenerator.getRandomNumber(0, 5);
        int eventLevel = hero.getLevel() + RandomNumberGenerator.getRandomNumber(-1, 1);
        if (eventLevel == 0) {
            eventLevel = 1;
        }

        switch (randomNumber) {
            case 0 -> new MerchantEvent(eventLevel).eventOccurs(hero);
            case 1, 2 -> {
                int randomNumToGenerateEnemyRarity = RandomNumberGenerator.getRandomNumber(0, 20);
                EnemyRarity enemyRarity = EnemyRarity.COMMON;
                if (randomNumToGenerateEnemyRarity == 0) {
                    enemyRarity = EnemyRarity.RARE;
                }
                List<Enemy> suitableEnemies = EnemyList.returnEnemyListByLocationTypeAndLevel(locationType, hero.getLevel(), null, enemyRarity);
                new CombatEvent(eventLevel, suitableEnemies, locationType).eventOccurs(hero);
            }
            case 3 -> new DiscoverLocationEvent(eventLevel, this.allLocations, this.discoveredLocations)
                    .eventOccurs(hero);
            case 4 -> new FindItemEvent(eventLevel)
                    .eventOccurs(hero);

            default -> new NoOutcomeEvent(0).eventOccurs(hero);
        }
    }
}
