package kuchtastefan.regions.events;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.quest.Quest;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class Event {

    protected final int eventLevel;

    public Event(int eventLevel) {
        this.eventLevel = eventLevel;
    }

    public abstract void eventOccurs(Hero hero);
}
