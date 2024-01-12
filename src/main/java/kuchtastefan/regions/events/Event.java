package kuchtastefan.regions.events;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.quest.Quest;

public abstract class Event {

    protected final int eventLevel;

    public Event(int eventLevel) {
        this.eventLevel = eventLevel;
    }

    public abstract void eventOccurs(Hero hero);

    public int getEventLevel() {
        return eventLevel;
    }
}
