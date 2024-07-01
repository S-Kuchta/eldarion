package kuchtastefan.item;

import kuchtastefan.item.itemFilter.ItemFilter;
import kuchtastefan.item.specificItems.keyItem.KeyItem;
import kuchtastefan.item.specificItems.questItem.QuestItem;
import kuchtastefan.item.specificItems.wearableItem.WearableItem;
import kuchtastefan.item.specificItems.wearableItem.WearableItemQuality;
import kuchtastefan.utility.RandomNumberGenerator;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemDB {

    @Getter
    private static final Map<Integer, Item> ITEM_DB = new HashMap<>();

    public static Item returnItemFromDB(int itemId) {

        return ITEM_DB.get(itemId);
    }

    public static void addItemToDB(Item item) {
        ITEM_DB.put(item.getItemId(), item);
    }

    public static List<Item> returnItemListForEnemyDrop(ItemFilter itemFilter) {
        List<Item> itemList = new ArrayList<>(returnFilteredItemList(itemFilter));
        itemList.removeIf(item -> item instanceof QuestItem ||
                item instanceof KeyItem ||
                (item instanceof WearableItem wearableItem && (
                        wearableItem.getWearableItemQuality() == WearableItemQuality.QUEST_REWARD || wearableItem.getWearableItemQuality() == WearableItemQuality.SPECIAL)));

        return itemList;
    }

    public static List<Item> returnFilteredItemList(ItemFilter itemFilter) {
        List<Item> itemList = new ArrayList<>();
        for (Item item : ITEM_DB.values()) {
            if (itemFilter.filterItem(item) != null) {
                itemList.add(item);
            }
        }

        return itemList;
    }

    public static Item getRandomItem(ItemFilter itemFilter) {
        List<Item> items = returnFilteredItemList(itemFilter);
        return items.get(RandomNumberGenerator.getRandomNumber(0, items.size() - 1));
    }

    public static List<Item> returnItemList() {
        return new ArrayList<>(ItemDB.ITEM_DB.values());
    }

}