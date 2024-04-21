package kuchtastefan.character.hero.inventory;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;

public interface UsingHeroInventory {
    void mainMenu(Hero hero);
    boolean itemOptions(Hero hero, Item item);
}
