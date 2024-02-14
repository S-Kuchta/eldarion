package kuchtastefan.regions.events;

import kuchtastefan.actions.actionsWIthDuration.ActionDurationType;
import kuchtastefan.characters.enemy.Enemy;
import kuchtastefan.characters.enemy.EnemyList;
import kuchtastefan.characters.enemy.EnemyRarity;
import kuchtastefan.characters.hero.Hero;
import kuchtastefan.regions.locations.Location;
import kuchtastefan.regions.locations.LocationType;
import kuchtastefan.utility.RandomNumberGenerator;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class EventService {

    private final List<Location> allLocations;


    public EventService(List<Location> allLocations) {
        this.allLocations = allLocations;
    }

    public void randomRegionEventGenerate(Hero hero, LocationType locationType, int minRegionLevel, int maxRegionLevel) {
        int randomNumber = RandomNumberGenerator.getRandomNumber(0, 5);
        int eventLevel = hero.getLevel() + RandomNumberGenerator.getRandomNumber(-1, 1);
        if (eventLevel == 0) {
            eventLevel = 1;
        }

        switch (randomNumber) {
            case 0 -> new MerchantEvent(eventLevel).eventOccurs(hero);
            case 1, 2 -> {
//                int randomNumToGenerateEnemyRarity = RandomNumberGenerator.getRandomNumber(0, 20);
//                EnemyRarity enemyRarity = EnemyRarity.COMMON;
//                if (randomNumToGenerateEnemyRarity == 0) {
//                    enemyRarity = EnemyRarity.RARE;
//                }

                List<Enemy> suitableEnemies = EnemyList.returnEnemyListByLocationTypeAndLevel(locationType, maxRegionLevel, minRegionLevel, EnemyRarity.COMMON);

                int randomNumberForEnemyOrder = 0;
                List<Enemy> enemyList = new ArrayList<>();

                for (int i = 0; i < RandomNumberGenerator.getRandomNumber(1, 3); i++) {
                    if (!suitableEnemies.isEmpty()) {
                        randomNumberForEnemyOrder = RandomNumberGenerator.getRandomNumber(0, suitableEnemies.size() - 1);
                    } else {
                        return;
                    }

                    enemyList.add(EnemyList.returnEnemyWithNewCopy(
                            suitableEnemies.get(randomNumberForEnemyOrder),
                            suitableEnemies.get(randomNumberForEnemyOrder).getEnemyRarity()));
                }

                new CombatEvent(eventLevel, enemyList, locationType).eventOccurs(hero);
            }
            case 3 -> new DiscoverLocationEvent(eventLevel, this.allLocations)
                    .eventOccurs(hero);
            case 4 -> new GatherCraftingReagentItemEvent(eventLevel)
                    .eventOccurs(hero);

            default -> new NoOutcomeEvent(0).eventOccurs(hero);
        }

        hero.checkAndRemoveActionTurns();
        hero.updateCurrentCharacterStateDependsOnActiveActionsAndIncreaseTurn(ActionDurationType.REGION_ACTION);

    }
}
