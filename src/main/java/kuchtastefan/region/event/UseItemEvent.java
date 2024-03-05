package kuchtastefan.region.event;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemsLists;

public class UseItemEvent extends Event {

    private final int itemId;

    public UseItemEvent(int eventLevel, int itemId) {
        super(eventLevel);
        this.itemId = itemId;
    }

    @Override
    public boolean eventOccurs(Hero hero) {
        Item item = ItemsLists.getItemMapIdItem().get(this.itemId);
        if (hero.getHeroInventory().getHeroInventory().containsKey(item)) {
            System.out.println("\tYou are using " + item.getName());
            return true;
        } else {
            System.out.println("\tYou do not have needed item!");
            return false;
        }
    }
}
