package kuchtastefan.regions.events;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.service.BattleService;

public class CombatEvent extends Event {

    private final BattleService battleService;

    public CombatEvent(int eventLevel) {
        super(eventLevel);
        this.battleService = new BattleService();
    }

    @Override
    public void eventOccurs(Hero hero) {
        System.out.println("\tCombat event");
    }
}
