package kuchtastefan.item.questItem;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;

public class QuestItem extends Item {
    public QuestItem(Integer itemId, String name, double price, int itemLevel) {
        super(itemId, name, price, itemLevel);
    }

    @Override
    public void printItemDescription(Hero hero) {

    }
}
