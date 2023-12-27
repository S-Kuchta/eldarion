package kuchtastefan.domain;

public class GameLoaded {

    private final Hero hero;
    private final int level;

    public GameLoaded(int level, Hero hero) {
        this.level = level;
        this.hero = hero;
    }

    public int getLevel() {
        return level;
    }

    public Hero getHero() {
        return hero;
    }
}
