package kuchtastefan.utility;

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

    public static List<ItemType> wearableItemTypeList() {
        return new ArrayList<>(List.of(WearableItemType.values()));
    }

    public static List<ItemType> craftingReagentItemTypeList() {
        return new ArrayList<>(List.of(CraftingReagentItemType.values()));
    }

    public static List<ItemType> consumeableItemTypeList() {
        return new ArrayList<>(List.of(ConsumableItemType.values()));
    }

    public static List<ItemType> itemTypesByClass(ItemClassFilter itemClassFilter) {
        List<ItemType> itemTypes = new ArrayList<>();
        if (itemClassFilter.containsClass(WearableItem.class)) {
            itemTypes.addAll(wearableItemTypeList());
        }

        if (itemClassFilter.containsClass(CraftingReagentItem.class)) {
            itemTypes.addAll(craftingReagentItemTypeList());
        }

        if (itemClassFilter.containsClass(ConsumableItem.class)) {
            itemTypes.addAll(consumeableItemTypeList());
        }

        return itemTypes;
    }

    public static List<ItemType> itemTypes(ItemType... types) {
        return new ArrayList<>(List.of(types));
    }
}
