package kuchtastefan.characters.hero;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.hint.Hint;
import kuchtastefan.hint.HintName;
import kuchtastefan.regions.locations.Location;

import java.util.List;
import java.util.Map;

public class GameLoaded {

    private final Hero hero;
    private final int level;
    private final Map<HintName, Hint> hintUtil;
    private final List<Location> forestRegionDiscoveredLocation;

    public GameLoaded(int level, Hero hero, Map<HintName, Hint> hintUtil,  List<Location> forestRegionDiscoveredLocation) {
        this.level = level;
        this.hero = hero;
        this.hintUtil = hintUtil;
        this.forestRegionDiscoveredLocation = forestRegionDiscoveredLocation;
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

    public List<Location> getForestRegionDiscoveredLocation() {
        return forestRegionDiscoveredLocation;
    }
}
