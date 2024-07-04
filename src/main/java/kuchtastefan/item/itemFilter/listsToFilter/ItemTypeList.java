package kuchtastefan.item.itemFilter.listsToFilter;

import kuchtastefan.item.itemFilter.ItemClassFilter;
import kuchtastefan.item.itemType.ItemType;
import kuchtastefan.item.specificItems.consumeableItem.ConsumableItem;
import kuchtastefan.item.specificItems.consumeableItem.ConsumableItemType;
import kuchtastefan.item.specificItems.craftingItem.CraftingReagentItem;
import kuchtastefan.item.specificItems.craftingItem.CraftingReagentItemType;
import kuchtastefan.item.specificItems.wearableItem.WearableItem;
import kuchtastefan.item.specificItems.wearableItem.WearableItemType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemTypeList {

    public static Set<ItemType> allTypesList() {
        Set<ItemType> itemTypes = new HashSet<>();
        itemTypes.addAll(List.of(WearableItemType.values()));
        itemTypes.addAll(List.of(ConsumableItemType.values()));
        itemTypes.addAll(List.of(CraftingReagentItemType.values()));

        return itemTypes;
    }

    public static List<ItemType> itemTypesByClass(ItemClassFilter itemClassFilter) {
        List<ItemType> itemTypes = new ArrayList<>();
        if (itemClassFilter.containsClass(WearableItem.class)) {
            itemTypes.addAll(List.of(WearableItemType.values()));
        }

        if (itemClassFilter.containsClass(CraftingReagentItem.class)) {
            itemTypes.addAll(List.of(CraftingReagentItemType.values()));
        }

        if (itemClassFilter.containsClass(ConsumableItem.class)) {
            itemTypes.addAll(List.of(ConsumableItemType.values()));
        }

        return itemTypes;
    }
}
