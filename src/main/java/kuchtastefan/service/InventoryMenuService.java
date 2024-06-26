package kuchtastefan.service;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.hero.inventory.UsingHeroInventory;
import kuchtastefan.item.Item;
import kuchtastefan.item.itemFilter.ItemFilter;
import kuchtastefan.item.usableItem.UsableItem;

public class InventoryMenuService implements UsingHeroInventory {

    @Override
    public void mainMenu(Hero hero) {
        if (!hero.isInCombat()) {
            hero.getHeroInventoryManager().selectItem(hero, this, new ItemFilter());
        }
    }

    @Override
    public boolean itemOptions(Hero hero, Item item) {
        return UsableItem.useItem(hero, item);
    }
}
