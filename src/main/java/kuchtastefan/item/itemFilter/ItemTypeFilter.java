package kuchtastefan.item.itemFilter;

import kuchtastefan.item.itemType.ItemType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ItemTypeFilter {

    private ItemType itemType;
    private List<ItemType> itemTypes;

    public ItemTypeFilter() {
        this.itemTypes = new ArrayList<>();
    }

    public ItemTypeFilter(ItemType itemType) {
        this.itemType = itemType;
    }

    public ItemTypeFilter(List<ItemType> itemTypes) {
        this.itemTypes = itemTypes;
    }

    public boolean checkTypeCondition(ItemType itemType) {
        if (itemTypes != null) {
            return itemTypes.contains(itemType);
        }

        return this.itemType == itemType;
    }

    public void addItemType(ItemType itemType) {
        itemTypes.add(itemType);
    }

    public void removeItemType(ItemType itemType) {
        itemTypes.remove(itemType);
    }
}
