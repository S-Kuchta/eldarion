package kuchtastefan.items;

import kuchtastefan.items.consumeableItem.ConsumableItem;
import kuchtastefan.items.consumeableItem.ConsumableItemType;
import kuchtastefan.items.craftingItem.CraftingReagentItem;
import kuchtastefan.items.craftingItem.CraftingReagentItemType;
import kuchtastefan.items.junkItem.JunkItem;
import kuchtastefan.items.questItem.QuestItem;
import kuchtastefan.items.wearableItem.WearableItem;

import java.util.ArrayList;
import java.util.List;

public class ItemsLists {
    private final List<WearableItem> wearableItemList;
    private final List<CraftingReagentItem> craftingReagentItems;
    private final List<QuestItem> questItems;
    private final List<ConsumableItem> consumableItems;
    private final List<JunkItem> junkItems;

    public ItemsLists() {
        this.wearableItemList = new ArrayList<>();
        this.craftingReagentItems = new ArrayList<>();
        this.questItems = new ArrayList<>();
        this.consumableItems = new ArrayList<>();
        this.junkItems = new ArrayList<>();
    }

    public List<Item> returnItemListByLevel(int maxItemLevel, Integer minItemLevel) {
        List<Item> itemList = new ArrayList<>();
        List<Item> itemListAfterLevelCheck = new ArrayList<>();
        itemList.addAll(this.wearableItemList);
        itemList.addAll(this.craftingReagentItems);
        itemList.addAll(this.consumableItems);
        itemList.addAll(this.junkItems);

        for (Item item : itemList) {
            if (item.getItemLevel() == 0) {
                itemListAfterLevelCheck.add(item);
            } else if (checkItemLevelCondition(item, maxItemLevel, minItemLevel)) {
                itemListAfterLevelCheck.add(item);
            }
        }
        return itemListAfterLevelCheck;
    }

    private boolean checkItemLevelCondition(Item item, int maxItemLevel, Integer minItemLevel) {
        if (minItemLevel == null) {
            minItemLevel = maxItemLevel;
        }

        return maxItemLevel + 1 >= item.getItemLevel() && minItemLevel - 1 <= item.getItemLevel();
    }

    public List<CraftingReagentItem> returnCraftingReagentItemListByType(CraftingReagentItemType craftingReagentItemType) {
        List<CraftingReagentItem> tempList = new ArrayList<>();
        for (CraftingReagentItem craftingReagentItem : this.craftingReagentItems) {
            if (craftingReagentItem.getCraftingReagentItemType().equals(craftingReagentItemType)) {
                tempList.add(craftingReagentItem);
            }
        }
        return tempList;
    }

    public List<CraftingReagentItem> returnCraftingReagentItemListByItemLevel(int maxItemLevel, Integer minItemLevel) {
        List<CraftingReagentItem> tempList = new ArrayList<>();
        for (CraftingReagentItem craftingReagentItem : this.craftingReagentItems) {
            if (checkItemLevelCondition(craftingReagentItem, maxItemLevel, minItemLevel)) {
                tempList.add(craftingReagentItem);
            }
        }
        return tempList;
    }

    public List<CraftingReagentItem> returnCraftingReagentItemListByTypeAndItemLevel(CraftingReagentItemType craftingReagentItemType, int maxItemLevel, Integer minItemLevel) {
        List<CraftingReagentItem> tempList = new ArrayList<>();
        for (CraftingReagentItem craftingReagentItem : returnCraftingReagentItemListByType(craftingReagentItemType)) {
            if (checkItemLevelCondition(craftingReagentItem, maxItemLevel, minItemLevel)) {
                tempList.add(craftingReagentItem);
            }
        }
        return tempList;
    }

    public List<ConsumableItem> returnConsumableItemListByType(ConsumableItemType consumableItemType) {
        List<ConsumableItem> tempList = new ArrayList<>();
        for (ConsumableItem consumableItem : this.consumableItems) {
            if (consumableItem.getConsumableItemType().equals(consumableItemType)) {
                tempList.add(consumableItem);
            }
        }
        return tempList;
    }

    public List<ConsumableItem> returnConsumableItemListByItemLevel(int maxItemLevel, Integer minItemLevel) {
        List<ConsumableItem> tempList = new ArrayList<>();
        for (ConsumableItem consumableItem : this.consumableItems) {
            if (checkItemLevelCondition(consumableItem, maxItemLevel, minItemLevel)) {
                tempList.add(consumableItem);
            }
        }
        return tempList;
    }

    public List<ConsumableItem> returnConsumableItemListByTypeAndItemLevel(ConsumableItemType consumableItemType, int maxItemLevel, Integer minItemLevel) {
        List<ConsumableItem> tempList = new ArrayList<>();
        for (ConsumableItem consumableItem : returnConsumableItemListByType(consumableItemType)) {
            if (checkItemLevelCondition(consumableItem, maxItemLevel, minItemLevel)) {
                tempList.add(consumableItem);
            }
        }
        return tempList;
    }

    public List<WearableItem> returnWearableItemListByItemLevel(int maxItemLevel, Integer minItemLevel) {
        List<WearableItem> tempList = new ArrayList<>();
        for (WearableItem wearableItem : this.wearableItemList) {
            if (checkItemLevelCondition(wearableItem, maxItemLevel, minItemLevel)) {
                tempList.add(wearableItem);
            }
        }
        return tempList;
    }

    public List<JunkItem> returnJunkItemListByItemLevel(int maxItemLevel, Integer minItemLevel) {
        List<JunkItem> tempList = new ArrayList<>();
        for (JunkItem junkItem : this.junkItems) {
            if (checkItemLevelCondition(junkItem, maxItemLevel, minItemLevel)) {
                tempList.add(junkItem);
            }
        }
        return tempList;
    }

    public List<WearableItem> getWearableItemList() {
        return wearableItemList;
    }

    public List<CraftingReagentItem> getCraftingReagentItems() {
        return craftingReagentItems;
    }

    public List<QuestItem> getQuestItems() {
        return questItems;
    }

    public List<ConsumableItem> getConsumableItems() {
        return consumableItems;
    }

    public List<JunkItem> getJunkItems() {
        return junkItems;
    }
}
