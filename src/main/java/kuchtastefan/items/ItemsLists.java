package kuchtastefan.items;

import kuchtastefan.items.consumeableItem.ConsumableItem;
import kuchtastefan.items.consumeableItem.ConsumableItemType;
import kuchtastefan.items.craftingItem.CraftingReagentItem;
import kuchtastefan.items.craftingItem.CraftingReagentItemType;
import kuchtastefan.items.junkItem.JunkItem;
import kuchtastefan.items.questItem.QuestItem;
import kuchtastefan.items.wearableItem.WearableItem;
import kuchtastefan.items.wearableItem.WearableItemQuality;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemsLists {
    @Getter
    private static final Map<String, Item> allItemsMap = new HashMap<>();
    @Getter
    private static final List<WearableItem> wearableItemList = new ArrayList<>();
    @Getter
    private static final List<CraftingReagentItem> craftingReagentItems = new ArrayList<>();
    @Getter
    private static final List<QuestItem> questItems = new ArrayList<>();
    @Getter
    private static final List<ConsumableItem> consumableItems = new ArrayList<>();
    @Getter
    private static final List<JunkItem> junkItems = new ArrayList<>();


    public static void initializeAllItemsMapToStringItemMap() {
        List<Item> itemList = returnAllItemsList();
        itemList.addAll(questItems);
        for (Item item : itemList) {
            allItemsMap.put(item.getName(), item);
        }
    }

    private static List<Item> returnAllItemsList() {
        List<Item> itemList = new ArrayList<>();
        itemList.addAll(wearableItemList);
        itemList.addAll(craftingReagentItems);
        itemList.addAll(consumableItems);
        itemList.addAll(junkItems);
        return itemList;
    }

    public static List<Item> returnItemListByLevel(int maxItemLevel, Integer minItemLevel) {
        List<Item> itemListAfterLevelCheck = new ArrayList<>();

        for (Item item : returnAllItemsList()) {
            if (item.getItemLevel() == 0) {
                itemListAfterLevelCheck.add(item);
            } else if (checkItemLevelCondition(item, maxItemLevel, minItemLevel)) {
                itemListAfterLevelCheck.add(item);
            }
        }
        return itemListAfterLevelCheck;
    }

    private static boolean checkItemLevelCondition(Item item, int maxItemLevel, Integer minItemLevel) {
        if (minItemLevel == null) {
            minItemLevel = maxItemLevel;
        }

        return maxItemLevel + 1 >= item.getItemLevel() && minItemLevel - 1 <= item.getItemLevel();
    }

    public static List<CraftingReagentItem> returnCraftingReagentItemListByType(CraftingReagentItemType craftingReagentItemType) {
        List<CraftingReagentItem> tempList = new ArrayList<>();
        for (CraftingReagentItem craftingReagentItem : craftingReagentItems) {
            if (craftingReagentItem.getCraftingReagentItemType().equals(craftingReagentItemType)) {
                tempList.add(craftingReagentItem);
            }
        }
        return tempList;
    }

    public static List<CraftingReagentItem> returnCraftingReagentItemListByItemLevel(int maxItemLevel, Integer minItemLevel) {
        List<CraftingReagentItem> tempList = new ArrayList<>();
        for (CraftingReagentItem craftingReagentItem : craftingReagentItems) {
            if (checkItemLevelCondition(craftingReagentItem, maxItemLevel, minItemLevel)) {
                tempList.add(craftingReagentItem);
            }
        }
        return tempList;
    }

    public static List<CraftingReagentItem> returnCraftingReagentItemListByTypeAndItemLevel(CraftingReagentItemType craftingReagentItemType, int maxItemLevel, Integer minItemLevel) {
        List<CraftingReagentItem> tempList = new ArrayList<>();
        for (CraftingReagentItem craftingReagentItem : returnCraftingReagentItemListByType(craftingReagentItemType)) {
            if (checkItemLevelCondition(craftingReagentItem, maxItemLevel, minItemLevel)) {
                tempList.add(craftingReagentItem);
            }
        }
        return tempList;
    }

    public static List<ConsumableItem> returnConsumableItemListByType(ConsumableItemType consumableItemType) {
        List<ConsumableItem> tempList = new ArrayList<>();
        for (ConsumableItem consumableItem : consumableItems) {
            if (consumableItem.getConsumableItemType().equals(consumableItemType)) {
                tempList.add(consumableItem);
            }
        }
        return tempList;
    }

    public static List<ConsumableItem> returnConsumableItemListByItemLevel(int maxItemLevel, Integer minItemLevel) {
        List<ConsumableItem> tempList = new ArrayList<>();
        for (ConsumableItem consumableItem : consumableItems) {
            if (checkItemLevelCondition(consumableItem, maxItemLevel, minItemLevel)) {
                tempList.add(consumableItem);
            }
        }
        return tempList;
    }

    public static List<ConsumableItem> returnConsumableItemListByTypeAndItemLevel(ConsumableItemType consumableItemType, int maxItemLevel, Integer minItemLevel) {
        List<ConsumableItem> tempList = new ArrayList<>();
        for (ConsumableItem consumableItem : returnConsumableItemListByType(consumableItemType)) {
            if (checkItemLevelCondition(consumableItem, maxItemLevel, minItemLevel)) {
                tempList.add(consumableItem);
            }
        }
        return tempList;
    }

    public static List<WearableItem> returnWearableItemListByItemLevel(int maxItemLevel, Integer minItemLevel, boolean specialItems) {
        List<WearableItem> tempList = new ArrayList<>();
        for (WearableItem wearableItem : wearableItemList) {
            if (checkItemLevelCondition(wearableItem, maxItemLevel, minItemLevel)) {
                if (!specialItems && !wearableItem.getWearableItemQuality().equals(WearableItemQuality.SPECIAL)) {
                    tempList.add(wearableItem);
                }

                if (specialItems && wearableItem.getItemQuality().equals(WearableItemQuality.SPECIAL)) {
                    tempList.add(wearableItem);
                }
            }
        }
        return tempList;
    }

    public static List<JunkItem> returnJunkItemListByItemLevel(int maxItemLevel, Integer minItemLevel) {
        List<JunkItem> tempList = new ArrayList<>();
        for (JunkItem junkItem : junkItems) {
            if (checkItemLevelCondition(junkItem, maxItemLevel, minItemLevel)) {
                tempList.add(junkItem);
            }
        }
        return tempList;
    }
}
