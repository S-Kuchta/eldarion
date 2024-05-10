package kuchtastefan.item.specificItems.keyItem;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.usableItem.UsableItem;

public class KeyItem extends Item implements UsableItem {

    public KeyItem(Integer itemId, String name, double price, int itemLevel) {
        super(itemId, name, price, itemLevel);
    }

    @Override
    public void printItemDescription(Hero hero) {
        System.out.println(this.getName());
    }

    @Override
    public boolean useItem(Hero hero) {
        return true;
    }
}
