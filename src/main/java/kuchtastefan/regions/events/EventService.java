package kuchtastefan.regions.events;

import kuchtastefan.domain.Hero;
import kuchtastefan.item.ItemsLists;
import kuchtastefan.utility.RandomNumberGenerator;

public class EventService {

    private final ItemsLists itemsLists;

    public EventService(ItemsLists itemsLists) {
        this.itemsLists = itemsLists;
    }

    public void randomRegionEventGenerate(Hero hero) {
        int randomNumber = RandomNumberGenerator.getRandomNumber(0, 7);
        int eventLevel = hero.getLevel() + RandomNumberGenerator.getRandomNumber(-1, 1);
        if (eventLevel == 0) {
            eventLevel = 1;
        }

        switch (randomNumber) {
            case 0 -> {
                new MerchantEvent(eventLevel, this.itemsLists).eventOccurs(hero);
            }
            case 1, 2 -> {
                new CombatEvent(eventLevel).eventOccurs(hero);
            }
            case 3 -> {
                new DiscoverLocationEvent(eventLevel).eventOccurs(hero);
            }
            case 4 -> {
                new FindItemEvent(eventLevel, this.itemsLists).eventOccurs(hero);
            }
            default -> new NoOutcomeEvent(0).eventOccurs(hero);
        }
    }
}
