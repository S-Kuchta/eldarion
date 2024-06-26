package kuchtastefan.item.itemFilter;

import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.printUtil.PrintUtil;
import lombok.Getter;

@Getter
public class ItemLevelFilter {

    private int minItemLevel;
    private int maxItemLevel;

    public ItemLevelFilter(int minItemLevel, int maxItemLevel) {
        this.minItemLevel = minItemLevel;
        this.maxItemLevel = maxItemLevel;
    }

    public ItemLevelFilter(int maxItemLevel) {
        this.maxItemLevel = maxItemLevel;
        this.minItemLevel = maxItemLevel;
    }

    public ItemLevelFilter() {
        this.minItemLevel = 0;
        this.maxItemLevel = 0;
    }

    public boolean checkLevelCondition(int itemLevel) {
        return itemLevel >= minItemLevel && itemLevel <= maxItemLevel;
    }

    public void printLevelRange() {
        System.out.print("\tCurrent level Range -> Min level: " + ConsoleColor.MAGENTA + minItemLevel + ConsoleColor.RESET
                + " Max level: " + ConsoleColor.MAGENTA + maxItemLevel + ConsoleColor.RESET);
    }

    public void handleItemLevel(String choice, ItemFilter itemFilter) {
        if (itemFilter.isCanBeChanged()) {
            switch (choice) {
                case "+" -> increaseMinLevel();
                case "-" -> decreaseMinLevel();
                case "++" -> increaseMaxLevel();
                case "--" -> decreaseMaxLevel();
                default -> PrintUtil.printEnterValidInput();
            }
        }
    }

    private void increaseMinLevel() {
        minItemLevel++;
    }

    private void decreaseMinLevel() {
        if (minItemLevel > 0) {
            minItemLevel--;
        }
    }

    private void increaseMaxLevel() {
        maxItemLevel++;
    }

    private void decreaseMaxLevel() {
        if (maxItemLevel > 0) {
            maxItemLevel--;
        }
    }

}
