package kuchtastefan.world.event.specificEvent;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import kuchtastefan.world.event.Event;

public class UseItemEvent extends Event {

    private final int itemId;

    public UseItemEvent(int eventLevel, int itemId) {
        super(eventLevel);
        this.itemId = itemId;
    }

    @Override
    public boolean eventOccurs(Hero hero) {
        Item item = ItemDB.returnItemFromDB(this.itemId);
        if (hero.getHeroInventory().getHeroInventory().containsKey(item)) {
            System.out.println("\tYou are using " + item.getName());
            return true;
        } else {
            System.out.println("\tYou do not have needed item!");
            return false;
        }
    }
}
