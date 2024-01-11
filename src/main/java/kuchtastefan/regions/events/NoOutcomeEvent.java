package kuchtastefan.regions.events;

import kuchtastefan.characters.hero.Hero;

public class NoOutcomeEvent extends Event {
    public NoOutcomeEvent(int eventLevel) {
        super(eventLevel);
    }

    @Override
    public void eventOccurs(Hero hero) {
        System.out.println("\t--> Nothing happened, just calm walking through the region <--");
    }
}
