package kuchtastefan.regions.events;

import kuchtastefan.domain.Hero;

public class NoOutcomeEvent extends Event {
    public NoOutcomeEvent(int eventLevel) {
        super(eventLevel);
    }

    @Override
    public void eventOccurs(Hero hero) {
        System.out.println("\tNothing happened, just calm walking through the region");
    }
}
