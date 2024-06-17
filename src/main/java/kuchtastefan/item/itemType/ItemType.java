package kuchtastefan.item.itemType;

import kuchtastefan.item.itemFilter.ItemFilter;

public interface ItemType {

    String getDescription();

    String name();

    ItemFilter returnItemType(int ordinal);

    void printTypeSelection();
}
