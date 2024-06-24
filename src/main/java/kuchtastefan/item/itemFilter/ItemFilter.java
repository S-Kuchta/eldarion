package kuchtastefan.item.itemFilter;

import kuchtastefan.item.Item;
import kuchtastefan.item.itemType.HaveType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * This class represents a filter for items. It is used to filter items based on their type and level.
 */
@Getter
public class ItemFilter {

    private ItemLevelFilter itemLevelFilter;
    private ItemTypeFilter itemTypeFilter;
    private ItemClassFilter itemClassFilter;
    @Setter
    private boolean canBeChanged = true;

    /**
     * Empty Constructor creates a new ItemFilter with default values.
     */
    public ItemFilter() {
        this.itemClassFilter = new ItemClassFilter(Item.class);
        this.itemTypeFilter = new ItemTypeFilter();
        this.itemLevelFilter = new ItemLevelFilter(1,5);
    }

    public ItemFilter(ItemClassFilter itemClassFilter, ItemLevelFilter itemLevelFilter, ItemTypeFilter itemTypeFilter) {
        this.itemClassFilter = itemClassFilter;
        this.itemLevelFilter = itemLevelFilter;
        this.itemTypeFilter = itemTypeFilter;
    }

    public ItemFilter(ItemLevelFilter itemLevelFilter, ItemTypeFilter itemTypeFilter) {
        this.itemLevelFilter = itemLevelFilter;
        this.itemTypeFilter = itemTypeFilter;
    }

    public ItemFilter(ItemClassFilter itemClassFilter, ItemLevelFilter itemLevelFilter) {
        this.itemClassFilter = itemClassFilter;
        this.itemLevelFilter = itemLevelFilter;
    }

    public ItemFilter(ItemLevelFilter itemLevelFilter) {
        this.itemLevelFilter = itemLevelFilter;
    }

    public ItemFilter(ItemTypeFilter itemTypeFilter) {
        this.itemTypeFilter = itemTypeFilter;
    }

    public ItemFilter(ItemClassFilter itemClassFilter) {
        this.itemClassFilter = itemClassFilter;
    }


    public boolean isCheckType() {
        return itemTypeFilter != null;
    }

    public boolean isCheckLevel() {
        return itemLevelFilter != null;
    }

    public boolean isCheckClass() {
        return itemClassFilter != null;
    }

    public Item filterItem(Item item) {
        if (this.isCheckClass() && !this.itemClassFilter.checkClassCondition(item.getClass())) {
            return null;
        }

        if (this.isCheckType() && item instanceof HaveType itemWithType) {
            if (this.itemTypeFilter.checkTypeCondition(itemWithType.getItemType())) {
                return null;
            }
        }

        if (this.isCheckLevel()) {
            if (!this.itemLevelFilter.checkLevelCondition(item.getItemLevel())) {
                return null;
            }
        }

        return item;
    }

}
