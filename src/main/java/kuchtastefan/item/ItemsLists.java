package kuchtastefan.item;

import kuchtastefan.item.consumeableItem.ConsumableItem;
import kuchtastefan.item.consumeableItem.ConsumableItemType;
import kuchtastefan.item.craftingItem.CraftingReagentItem;
import kuchtastefan.item.craftingItem.CraftingReagentItemType;
import kuchtastefan.item.questItem.QuestItem;
import kuchtastefan.item.wearableItem.WearableItem;

import java.util.ArrayList;
import java.util.List;

public class ItemsLists {
    private final List<WearableItem> wearableItemList;
    private final List<CraftingReagentItem> craftingReagentItems;
    private final List<QuestItem> questItems;
    private final List<ConsumableItem> consumableItems;

    public ItemsLists() {
        this.wearableItemList = new ArrayList<>();
        this.craftingReagentItems = new ArrayList<>();
        this.questItems = new ArrayList<>();
        this.consumableItems = new ArrayList<>();
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

    public List<ConsumableItem> returnConsumableItemListByType(ConsumableItemType consumableItemType) {
        List<ConsumableItem> tempList = new ArrayList<>();
        for (ConsumableItem consumableItem : this.consumableItems) {
            if (consumableItem.getConsumableItemType().equals(consumableItemType)) {
                tempList.add(consumableItem);
            }
        }
        return tempList;
    }

    public List<CraftingReagentItem> returnCraftingReagentItemListByTypeAndLevel(CraftingReagentItemType craftingReagentItemType, int itemLevel) {
        List<CraftingReagentItem> tempList = new ArrayList<>();
        for (CraftingReagentItem craftingReagentItem : returnCraftingReagentItemListByType(craftingReagentItemType)) {
            if (itemLevel + 1 > craftingReagentItem.getItemLevel() || itemLevel - 1 < craftingReagentItem.getItemLevel()) {
                tempList.add(craftingReagentItem);
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
}
