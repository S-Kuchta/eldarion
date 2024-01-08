package kuchtastefan.regions.events;

import kuchtastefan.domain.Hero;

public abstract class Event {

    protected final int eventLevel;

    public Event(int eventLevel) {
        this.eventLevel = eventLevel;
    }

    public abstract void eventOccurs(Hero hero);
}
