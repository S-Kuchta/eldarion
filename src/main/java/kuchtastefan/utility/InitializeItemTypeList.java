package kuchtastefan.utility;

import kuchtastefan.item.itemType.ItemType;
import kuchtastefan.item.specificItems.consumeableItem.ConsumableItemType;
import kuchtastefan.item.specificItems.craftingItem.CraftingReagentItemType;
import kuchtastefan.item.specificItems.wearableItem.WearableItemType;

import java.util.ArrayList;
import java.util.List;

public class InitializeItemTypeList {

    public static List<ItemType> itemTypeList() {
        List<ItemType> itemTypes = new ArrayList<>();
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
}
