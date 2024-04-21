package kuchtastefan.character.hero.inventory.itemFilter;

import kuchtastefan.item.itemType.ItemType;
import lombok.Getter;

/**
 * This class represents a filter for items. It is used to filter items based on their type and level.
 */
@Getter
public class ItemFilter {

    private ItemType itemType;
    private int maxItemLevel;
    private int minItemLevel;

    public ItemFilter(ItemType itemType, int maxItemLevel, int minItemLevel) {
        this.itemType = itemType;
        this.maxItemLevel = maxItemLevel;
        this.minItemLevel = minItemLevel;
    }

    /**
     * Constructs a new ItemFilter with the specified item type and maximum level. The minimum level is set to the same value as the maximum level.
     *
     * @param itemType     The type of items to filter.
     * @param maxItemLevel The maximum level of items to filter.
     */
    public ItemFilter(ItemType itemType, int maxItemLevel) {
        this.itemType = itemType;
        this.maxItemLevel = maxItemLevel;
        this.minItemLevel = maxItemLevel;
    }

    public ItemFilter(int maxItemLevel, int minItemLevel) {
        this.maxItemLevel = maxItemLevel;
        this.minItemLevel = minItemLevel;
    }

    public ItemFilter(int maxItemLevel) {
        this.maxItemLevel = maxItemLevel;
        this.minItemLevel = maxItemLevel;
    }

    public ItemFilter(ItemType itemType) {
        this.itemType = itemType;
    }

    public ItemFilter() {
    }

    public boolean isCheckType() {
        return itemType != null;
    }

    public boolean isCheckLevel() {
        return maxItemLevel != 0 || minItemLevel != 0;
    }
}
