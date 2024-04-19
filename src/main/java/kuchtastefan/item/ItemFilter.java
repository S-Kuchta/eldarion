package kuchtastefan.item;

import lombok.Getter;

@Getter
public class ItemFilter {

    private ItemType itemType;
    private final int maxItemLevel;
    private final int minItemLevel;

    public ItemFilter(ItemType itemType, int maxItemLevel, int minItemLevel) {
        this.itemType = itemType;
        this.maxItemLevel = maxItemLevel;
        this.minItemLevel = minItemLevel;
    }

    public ItemFilter(int maxItemLevel, int minItemLevel) {
        this.maxItemLevel = maxItemLevel;
        this.minItemLevel = minItemLevel;
    }

    public ItemFilter(int maxItemLevel) {
        this.maxItemLevel = maxItemLevel;
        this.minItemLevel = maxItemLevel;
    }

    public ItemFilter(ItemType itemType, int maxItemLevel) {
        this.maxItemLevel = maxItemLevel;
        this.minItemLevel = maxItemLevel;
        this.itemType = itemType;
    }


    public boolean isCheckType() {
        return itemType != null;
    }
}
