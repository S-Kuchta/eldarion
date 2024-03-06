package kuchtastefan.region.event;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.actionsWIthDuration.ActionDurationType;
import kuchtastefan.character.enemy.Enemy;
import kuchtastefan.character.enemy.EnemyGroupList;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.constant.Constant;
import kuchtastefan.region.location.Location;
import kuchtastefan.region.location.LocationType;
import kuchtastefan.utility.RandomNumberGenerator;
import lombok.Getter;

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
                List<Enemy> enemyList = EnemyGroupList.returnEnemyGroupByLocationTypeAndHeroLevel(locationType, hero.getLevel());
//                List<Enemy> suitableEnemies = CharacterList.returnEnemyListByLocationTypeAndLevel(locationType, maxRegionLevel, minRegionLevel, CharacterRarity.COMMON);
//
//                int randomNumberForEnemyOrder = 0;
//                List<Enemy> enemyList = new ArrayList<>();
//
//                for (int i = 0; i < RandomNumberGenerator.getRandomNumber(1, 3); i++) {
//                    if (!suitableEnemies.isEmpty()) {
//                        randomNumberForEnemyOrder = RandomNumberGenerator.getRandomNumber(0, suitableEnemies.size() - 1);
//                    } else {
//                        return;
//                    }
//
//                    enemyList.add(CharacterList.returnEnemyWithNewCopy(
//                            suitableEnemies.get(randomNumberForEnemyOrder),
//                            suitableEnemies.get(randomNumberForEnemyOrder).getCharacterRarity()));
//                }

                new CombatEvent(eventLevel, enemyList, locationType).eventOccurs(hero);
            }
            case 3 -> new DiscoverLocationEvent(eventLevel, this.allLocations).eventOccurs(hero);
            case 4 -> new GatherCraftingReagentItemEvent(eventLevel).eventOccurs(hero);

            default -> new NoOutcomeEvent(0).eventOccurs(hero);
        }

        hero.restoreAbility(hero.getCurrentAbilityValue(Ability.INTELLECT)
                * Constant.RESTORE_MANA_PER_ONE_INTELLECT, Ability.MANA);

        hero.checkAndRemoveActionTurns();
        hero.updateCurrentCharacterStateDependsOnActiveActionsAndIncreaseTurn(ActionDurationType.REGION_ACTION);
    }
}
