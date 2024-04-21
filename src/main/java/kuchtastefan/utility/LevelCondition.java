package kuchtastefan.utility;

import kuchtastefan.item.Item;

public class LevelCondition {

    public static boolean checkItemLevelCondition(Item item, int maxItemLevel, int minItemLevel) {
        if (item.getItemLevel() == 0) {
            item.setItemLevel(maxItemLevel);
        }

        return maxItemLevel == item.getItemLevel() && minItemLevel <= item.getItemLevel();
    }
}
