package kuchtastefan.region.event;

import kuchtastefan.character.hero.Hero;

public class NoOutcomeEvent extends Event {
    public NoOutcomeEvent(int eventLevel) {
        super(eventLevel);
    }

    @Override
    public boolean eventOccurs(Hero hero) {
        System.out.println("\t--> Nothing happened, just calm walking through the region <--");
        return true;
    }
}
