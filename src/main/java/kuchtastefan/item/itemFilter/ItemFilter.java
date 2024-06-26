package kuchtastefan.item.itemFilter;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.itemType.HaveType;
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
    @Setter
    private boolean canBeChanged = true;

    /**
     * Creates a new ItemFilter with default values.
     */
    public ItemFilter(Hero hero) {
        this.itemClassFilter = new ItemClassFilter();
        this.itemTypeFilter = new ItemTypeFilter();
        this.itemLevelFilter = new ItemLevelFilter(1, hero.getLevel());
    }

    public ItemFilter(ItemClassFilter itemClassFilter, ItemTypeFilter itemTypeFilter, ItemLevelFilter itemLevelFilter) {
        this.itemClassFilter = itemClassFilter;
        this.itemTypeFilter = itemTypeFilter;
        this.itemLevelFilter = itemLevelFilter;
    }

    public Item filterItem(Item item) {
        if (!this.itemClassFilter.containsClass(item.getClass())) {
            return null;
        }

        if (item instanceof HaveType itemWithType) {
            if (!this.itemTypeFilter.containsType(itemWithType.getItemType())) {
                return null;
            }
        }

        if (!this.itemLevelFilter.checkLevelCondition(item.getItemLevel())) {
            return null;
        }

        return item;
    }
}
