package kuchtastefan.item.itemFilter;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.itemType.HaveType;
import kuchtastefan.item.specificItems.wearableItem.WearableItem;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a filter for items. It is used to filter items based on their type and level.
 */
@Getter
public class ItemFilter {

    private final ItemLevelFilter itemLevelFilter;
    private final ItemTypeFilter itemTypeFilter;
    private final ItemClassFilter itemClassFilter;
    private final WearableItemQualityFilter wearableItemQualityFilter;
    @Setter
    private boolean canBeChanged = true;

    /**
     * Creates a new ItemFilter with default values.
     */
    public ItemFilter(Hero hero) {
        this.itemClassFilter = new ItemClassFilter();
        this.itemTypeFilter = new ItemTypeFilter(true);
        this.itemLevelFilter = new ItemLevelFilter(hero.getLevel());
        this.wearableItemQualityFilter = new WearableItemQualityFilter(true);
    }

    public ItemFilter(ItemClassFilter itemClassFilter, ItemTypeFilter itemTypeFilter, ItemLevelFilter itemLevelFilter) {
        this.itemClassFilter = itemClassFilter;
        this.itemTypeFilter = itemTypeFilter;
        this.itemLevelFilter = itemLevelFilter;
        this.wearableItemQualityFilter = new WearableItemQualityFilter(false);
    }

    public ItemFilter(ItemClassFilter itemClassFilter, ItemTypeFilter itemTypeFilter, ItemLevelFilter itemLevelFilter, WearableItemQualityFilter wearableItemQualityFilter) {
        this.itemClassFilter = itemClassFilter;
        this.itemTypeFilter = itemTypeFilter;
        this.itemLevelFilter = itemLevelFilter;
        this.wearableItemQualityFilter = wearableItemQualityFilter;
    }

    public Item filterItem(Item item) {
        if (!itemClassFilter.containsClass(item.getClass())) {
            return null;
        }

        if (itemTypeFilter.isFilterTypes()) {
            if (item instanceof HaveType itemWithType) {
                if (!itemTypeFilter.containsType(itemWithType.getItemType())) {
                    return null;
                }
            } else {
                return null;
            }
        }

        if (!itemLevelFilter.checkLevelCondition(item.getItemLevel())) {
            return null;
        }

        if (item instanceof WearableItem wearableItem) {
            if (wearableItemQualityFilter.isFilterQualities()) {
                if (!wearableItemQualityFilter.containsQuality(wearableItem.getWearableItemQuality())) {
                    return null;
                }
            }
        }

        return item;
    }
}
