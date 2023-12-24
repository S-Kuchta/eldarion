package kuchtastefan.domain;

public class GameLoaded {

    private final Hero hero;
    private final int level;
    private final EquippedItems equippedItems;

    public GameLoaded(int level, Hero hero, EquippedItems equippedItems) {
        this.level = level;
        this.hero = hero;
        this.equippedItems = equippedItems;
    }

    public int getLevel() {
        return level;
    }

    public Hero getHero() {
        return hero;
    }

    public EquippedItems getEquippedItems() {
        return equippedItems;
    }
}
