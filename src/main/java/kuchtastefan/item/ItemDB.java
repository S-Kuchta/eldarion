package kuchtastefan.item;

import kuchtastefan.item.itemFilter.ItemFilter;
import kuchtastefan.item.itemType.HaveType;
import kuchtastefan.item.specificItems.questItem.QuestItem;
import kuchtastefan.utility.LevelCondition;
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
        List<Item> itemList = new ArrayList<>(returnItemListByLevelAndType(Item.class, itemFilter));
        itemList.removeIf(item -> item instanceof QuestItem);

        return itemList;
    }

    public static <T extends Item> List<T> returnItemListByLevelAndType(Class<T> itemClass, ItemFilter itemFilter) {
        List<T> itemList = new ArrayList<>();
        for (Item item : ITEM_DB.values()) {
            if (!itemClass.isInstance(item) || !LevelCondition.checkItemLevelCondition(item, itemFilter.getMaxItemLevel(), itemFilter.getMinItemLevel())) {
                continue;
            }

            if (item instanceof HaveType itemWithType) {
                if (itemFilter.isCheckType() && !itemWithType.getItemType().equals(itemFilter.getItemType())) {
                    continue;
                }
            }

            itemList.add(itemClass.cast(item));
        }

        return itemList;
    }

    public static <T extends Item> Item getRandomItem(Class<T> itemClass, ItemFilter itemFilter) {
        List<T> items = returnItemListByLevelAndType(itemClass, itemFilter);
        return items.get(RandomNumberGenerator.getRandomNumber(0, items.size() - 1));
    }

}