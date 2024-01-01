package kuchtastefan.domain;

import kuchtastefan.hint.Hint;
import kuchtastefan.hint.HintName;

import java.util.Map;

public class GameLoaded {

    private final Hero hero;
    private final int level;
    private final Map<HintName, Hint> hintUtil;


    public GameLoaded(int level, Hero hero, Map<HintName, Hint> hintUtil) {
        this.level = level;
        this.hero = hero;
        this.hintUtil = hintUtil;
    }

    public int getLevel() {
        return level;
    }

    public Hero getHero() {
        return hero;
    }

    public Map<HintName, Hint> getHintUtil() {
        return hintUtil;
    }
}
