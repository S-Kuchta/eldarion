package kuchtastefan.item.itemFilter;

import lombok.Getter;

@Getter
public class ItemLevelFilter {

    private final int minItemLevel;
    private final int maxItemLevel;

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


    public boolean isCheckLevel() {
        return maxItemLevel != 0 || minItemLevel != 0;
    }
}
