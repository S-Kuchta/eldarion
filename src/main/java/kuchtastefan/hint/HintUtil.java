package kuchtastefan.hint;

import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.PrintUtil;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class HintUtil {

    @Getter
    private static final Map<HintName, Hint> hintList = new HashMap<>();

    public static void initializeHintList() {
        hintList.put(HintName.BLACKSMITH_HINT, new Hint("\t- You can dismantle wearable item. This will destroy item,\nbut you get crafting reagents (items),\n" +
                "\t- You can refinement wearable item. This will increase item\nprice and ability bonus, but you need crafting reagents (items)"));
    }

    public static void printHint(HintName hintName) {
        for (Map.Entry<HintName, Hint> hint : hintList.entrySet()) {
            if (hint.getKey().equals(hintName) && !hint.getValue().isShowed()) {
                PrintUtil.printLongDivider();
                PrintUtil.printStringSlowly(ConsoleColor.YELLOW + "" + hintName + ConsoleColor.RESET + "\n" + hint.getValue().getText());
                PrintUtil.printLongDivider();
                hint.getValue().setShowed(true);
            }
        }
    }
}
