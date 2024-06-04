package kuchtastefan.world.event.specificEvent;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import kuchtastefan.item.specificItems.questItem.QuestItem;
import kuchtastefan.world.event.Event;

public class FindItemEvent extends Event {

    private final int itemId;

    public FindItemEvent(int eventLevel, int itemId) {
        super(eventLevel);
        this.itemId = itemId;
    }

    @Override
    public boolean eventOccurs(Hero hero) {
        Item item = ItemDB.returnItemFromDB(this.itemId);

        if (item instanceof QuestItem questItem) {
            hero.getHeroInventory().addQuestItemToInventory(questItem, 1, hero);
            return true;
        }

        hero.getHeroInventory().addItemToInventory(item, 1);
        return true;
    }
}
