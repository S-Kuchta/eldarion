package kuchtastefan.workshop;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;

public interface Workshop {

    void workshopMenu(Hero hero);
    void itemMenu(Hero hero, Item item);
}
