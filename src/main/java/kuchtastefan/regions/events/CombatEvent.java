package kuchtastefan.regions.events;

import kuchtastefan.domain.Hero;

public class CombatEvent extends Event {

    public CombatEvent(int eventLevel) {
        super(eventLevel);
    }

    @Override
    public void eventOccurs(Hero hero) {
        System.out.println("Combat event");
    }
}
