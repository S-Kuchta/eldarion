package kuchtastefan.item;

import kuchtastefan.item.itemFilter.ItemClassFilter;
import kuchtastefan.item.itemFilter.ItemFilter;
import kuchtastefan.item.itemFilter.ItemTypeFilter;
import kuchtastefan.item.itemType.HaveType;
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
//        List<Item> itemList = new ArrayList<>(returnFilteredItemList(Item.class, itemFilter));
        List<Item> itemList = new ArrayList<>(returnFilteredItemList(itemFilter));
        itemList.removeIf(item -> item instanceof QuestItem || (item instanceof WearableItem wearableItem &&
                (wearableItem.getWearableItemQuality() == WearableItemQuality.QUEST_REWARD ||
                        wearableItem.getWearableItemQuality() == WearableItemQuality.SPECIAL)));

        return itemList;
    }


//    public static <T extends Item> List<T> returnFilteredItemList(Class<T> itemClass, ItemFilter itemFilter) {
//        List<T> itemList = new ArrayList<>();
//        for (Item item : ITEM_DB.values()) {
//            if (!itemClass.isInstance(item) || !ItemLevelCondition.checkItemLevelCondition(item, itemFilter.getMaxItemLevel(), itemFilter.getMinItemLevel())) {
//                continue;
//            }
//
//            if (item instanceof HaveType itemWithType) {
//                if (itemFilter.isCheckType() && !itemWithType.getItemType().equals(itemFilter.getItemType())) {
//                    continue;
//                }
//            }
//
//            itemList.add(itemClass.cast(item));
//        }
//
//        return itemList;
//    }

    public static List<Item> returnFilteredItemList(ItemFilter itemFilter) {
        List<Item> itemList = new ArrayList<>();
        for (Item item : ITEM_DB.values()) {
//            if (itemFilter.isCheckClass() && !itemFilter.getItemClassFilter().checkClassCondition(item)) {
//                continue;
//            }
//
//            if (itemFilter.isCheckType() && item instanceof HaveType itemWithType) {
//                if (itemFilter.getItemTypeFilter().checkTypeCondition(itemWithType.getItemType())) {
//                    continue;
//                }
//            }
//
//            if (itemFilter.isCheckLevel()) {
//                if (!itemFilter.getItemLevelFilter().checkLevelCondition(item.itemLevel)) {
//                    continue;
//                }
//            }

//            itemList.add(item);

            if (itemFilter.filterItem(item) != null) {
                itemList.add(item);
            }
        }

        return itemList;
    }

//    public static <T extends Item> Item getRandomItem(Class<T> itemClass, ItemFilter itemFilter) {
//        List<T> items = returnFilteredItemList(itemClass, itemFilter);
//        return items.get(RandomNumberGenerator.getRandomNumber(0, items.size() - 1));
//    }

    public static Item getRandomItem(ItemFilter itemFilter) {
        List<Item> items = returnFilteredItemList(itemFilter);
        return items.get(RandomNumberGenerator.getRandomNumber(0, items.size() - 1));
    }

    public static List<Item> returnItemList() {
        return new ArrayList<>(ItemDB.ITEM_DB.values());
    }

}