package kuchtastefan.item.itemFilter;

import kuchtastefan.item.itemType.ItemType;
import kuchtastefan.utility.ItemTypeList;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class ItemTypeFilter {

    private final Set<ItemType> itemTypes;


    public ItemTypeFilter() {
        this.itemTypes = ItemTypeList.allTypesList();
    }

    public ItemTypeFilter(ItemType... itemTypes) {
        this.itemTypes = new HashSet<>(List.of(itemTypes));
    }


    public boolean containsType(ItemType itemType) {
        return itemTypes.contains(itemType);
    }

    public boolean checkTypeCondition(ItemType itemType) {
        return containsType(itemType);
    }

    public void addItemType(ItemType itemType) {
        itemTypes.add(itemType);
    }

    public void removeItemType(ItemType itemType) {
        itemTypes.remove(itemType);
    }
}
