package kuchtastefan.region.event;

import kuchtastefan.character.hero.Hero;
import lombok.Getter;

@Getter
public abstract class Event {

    protected final int eventLevel;

    public Event(int eventLevel) {
        this.eventLevel = eventLevel;
    }

    public abstract boolean eventOccurs(Hero hero);
}
