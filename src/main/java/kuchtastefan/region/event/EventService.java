package kuchtastefan.region.event;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.actionsWIthDuration.ActionDurationType;
import kuchtastefan.character.enemy.Enemy;
import kuchtastefan.character.enemy.EnemyGroupDB;
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

    public void randomRegionEventGenerate(Hero hero, LocationType locationType) {
        int randomNumber = RandomNumberGenerator.getRandomNumber(0, 4);
        int eventLevel = hero.getLevel() + RandomNumberGenerator.getRandomNumber(-1, 1);
        if (eventLevel == 0) {
            eventLevel = 1;
        }

        switch (randomNumber) {
            case 0 -> new MerchantEvent(eventLevel).eventOccurs(hero);
            case 1 -> {
                List<Enemy> enemyList = EnemyGroupDB.returnEnemyGroupByLocationTypeAndHeroLevel(locationType, hero.getLevel());

                new CombatEvent(eventLevel, enemyList, locationType).eventOccurs(hero);
            }
            case 2 -> new DiscoverLocationEvent(eventLevel, this.allLocations).eventOccurs(hero);
            case 3 -> new GatherCraftingReagentItemEvent(eventLevel).eventOccurs(hero);
            default -> new NoOutcomeEvent(0).eventOccurs(hero);
        }

        hero.restoreAbility(hero.getCurrentAbilityValue(Ability.INTELLECT)
                * Constant.RESTORE_MANA_PER_ONE_INTELLECT, Ability.MANA);

        hero.performActionsWithDuration(ActionDurationType.REGION_ACTION);
    }
}
