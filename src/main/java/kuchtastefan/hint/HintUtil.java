package kuchtastefan.hint;

import com.google.gson.annotations.Expose;
import kuchtastefan.utility.PrintUtil;

import java.util.Map;

public class HintUtil {

    private final Map<HintName, Hint> hintList;

    public HintUtil(Map<HintName, Hint> hintList) {
        this.hintList = hintList;
    }

    public void initializeHintList() {
        hintList.put(HintName.BLACKSMITH, new Hint("\t- You can dismantle wearable item. This will destroy item,\nbut you get crafting reagents (items),\n" +
                "\t- You can refinement wearable item. This will increase item\nprice and ability bonus, but you need crafting reagents (items)"));
    }

    public void printHint(HintName hintName) {
        for (Map.Entry<HintName, Hint> hint : hintList.entrySet()) {
            if (hint.getKey().equals(hintName) && !hint.getValue().isShowed()) {
                PrintUtil.printLongDivider();
                PrintUtil.printStringSlowly(HintName.BLACKSMITH.name() + " HINT\n" + hint.getValue().getText());
                PrintUtil.printLongDivider();
                hint.getValue().setShowed(true);
            }
        }
    }

    public Map<HintName, Hint> getHintList() {
        return hintList;
    }
}
