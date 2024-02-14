package kuchtastefan.regions.events;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.items.Item;
import kuchtastefan.items.ItemsLists;

public class FindItemEvent extends Event {

    private final int itemId;

    public FindItemEvent(int eventLevel, int itemId) {
        super(eventLevel);
        this.itemId = itemId;
    }

    @Override
    public boolean eventOccurs(Hero hero) {
        Item item = ItemsLists.getItemMapIdItem().get(this.itemId);
        hero.getHeroInventory().addItemWithNewCopyToItemList(item);
        System.out.println("\tYou found " + item.getName());
        hero.checkIfQuestObjectivesAndQuestIsCompleted();
        return true;
    }
}
