package kuchtastefan.characters.hero;

import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.hint.Hint;
import kuchtastefan.hint.HintName;
import kuchtastefan.items.Item;
import kuchtastefan.regions.locations.Location;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
public class GameLoaded {

    private final Hero hero;
    private final int level;
    private final Map<HintName, Hint> hintUtil;
    private final List<Location> forestRegionDiscoveredLocation;
    private final Set<ActionWithDuration> regionActionsWithDuration;
    private final Map<Item, Integer> itemList;

    public GameLoaded(int level, Hero hero, Map<HintName, Hint> hintUtil, List<Location> forestRegionDiscoveredLocation, Set<ActionWithDuration> regionActionsWithDuration, Map<Item, Integer> itemList) {
        this.level = level;
        this.hero = hero;
        this.hintUtil = hintUtil;
        this.forestRegionDiscoveredLocation = forestRegionDiscoveredLocation;
        this.regionActionsWithDuration = regionActionsWithDuration;
        this.itemList = itemList;
    }
}
