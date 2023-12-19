package kuchtastefan.domain;

public class GameLoaded {

    private final int playedLevel;
    private final Hero hero;

    public GameLoaded(int playedLevel, Hero hero) {
        this.playedLevel = playedLevel;
        this.hero = hero;
    }

    public int getPlayedLevel() {
        return playedLevel;
    }

    public Hero getHero() {
        return hero;
    }
}
