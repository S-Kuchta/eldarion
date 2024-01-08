package kuchtastefan.regions.events;

import kuchtastefan.domain.Hero;

public class DiscoverLocationEvent extends Event {

    public DiscoverLocationEvent(int eventLevel) {
        super(eventLevel);
    }

    @Override
    public void eventOccurs(Hero hero) {
        System.out.println("\tDiscover Location Event");
    }
}
