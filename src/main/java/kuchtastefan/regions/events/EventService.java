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
        int randomNumber = RandomNumberGenerator.getRandomNumber(0, 12);
        int eventLevel = hero.getLevel() + RandomNumberGenerator.getRandomNumber(-1, 1);
        if (eventLevel == 0) {
            eventLevel = 1;
        }

        switch (randomNumber) {
            case 0, 1 -> {
                new MerchantEvent(eventLevel, this.itemsLists).eventOccurs(hero);
            }
            case 2, 3, 4 -> {
                new CombatEvent(eventLevel).eventOccurs(hero);
            }
            case 5 -> {
                new DiscoverLocationEvent(eventLevel).eventOccurs(hero);
            }
            case 6 -> {
                new FindItemEvent(eventLevel, this.itemsLists).eventOccurs(hero);
            }
            default -> new NoOutcomeEvent(0).eventOccurs(hero);
        }
    }
}
