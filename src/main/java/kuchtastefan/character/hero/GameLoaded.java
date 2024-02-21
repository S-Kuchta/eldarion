package kuchtastefan.character.hero;

import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.hint.Hint;
import kuchtastefan.hint.HintName;
import kuchtastefan.items.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class GameLoaded {

    private final Hero hero;
    private final int level;
    private final Map<HintName, Hint> hintUtil;
    private final Set<ActionWithDuration> regionActionsWithDuration;
    private final Map<Item, Integer> itemList;

    public GameLoaded(int level, Hero hero, Map<HintName, Hint> hintUtil, Set<ActionWithDuration> regionActionsWithDuration, Map<Item, Integer> itemList) {
        this.level = level;
        this.hero = hero;
        this.hintUtil = hintUtil;
        this.regionActionsWithDuration = regionActionsWithDuration;
        this.itemList = itemList;
    }
}
