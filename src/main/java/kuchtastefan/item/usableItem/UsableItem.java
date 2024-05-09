package kuchtastefan.item.usableItem;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.specificItems.consumeableItem.ConsumableItem;
import kuchtastefan.item.specificItems.wearableItem.WearableItem;

public interface UsableItem {

    boolean useItem(Hero hero);

    static String returnUseItemText(Item item) {
        if (item instanceof ConsumableItem) {
            return "Consume item";
        }

        if (item instanceof WearableItem) {
            return "Equip item";
        }

        return "Use item";
    }
}
