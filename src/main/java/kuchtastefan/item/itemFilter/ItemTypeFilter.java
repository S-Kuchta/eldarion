package kuchtastefan.item.itemFilter;

import kuchtastefan.item.itemType.ItemType;
import lombok.Getter;

import java.util.List;

@Getter
public class ItemTypeFilter {

    private ItemType itemType;
    private List<ItemType> itemTypes;

    public ItemTypeFilter(ItemType itemType) {
        this.itemType = itemType;
    }

    public ItemTypeFilter(List<ItemType> itemTypes) {
        this.itemTypes = itemTypes;
    }

    public boolean isCheckType() {
        return itemType != null;
    }

    public boolean isCheckTypes() {
        return itemTypes != null;
    }
}
