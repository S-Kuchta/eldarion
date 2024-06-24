package kuchtastefan.item.itemFilter;

import kuchtastefan.item.Item;
import kuchtastefan.item.specificItems.consumeableItem.ConsumableItem;
import kuchtastefan.item.specificItems.craftingItem.CraftingReagentItem;
import kuchtastefan.item.specificItems.junkItem.JunkItem;
import kuchtastefan.item.specificItems.keyItem.KeyItem;
import kuchtastefan.item.specificItems.questItem.QuestItem;
import kuchtastefan.item.specificItems.wearableItem.WearableItem;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ItemClassFilter {

    private final Class<? extends Item> itemClass;
    private final List<Class<? extends Item>> itemClassList;

    public ItemClassFilter(Class<? extends Item> itemClass) {
        this.itemClass = itemClass;
        this.itemClassList = initializeClassList();
    }

    public ItemClassFilter(Class<? extends Item> itemClass, List<Class<? extends Item>> itemList) {
        this.itemClass = itemClass;
        this.itemClassList = itemList;
    }


    public List<Class<? extends Item>> initializeClassList() {
        List<Class<? extends Item>> classList = new ArrayList<>();
        classList.add(WearableItem.class);
        classList.add(ConsumableItem.class);
        classList.add(CraftingReagentItem.class);
        classList.add(QuestItem.class);
        classList.add(KeyItem.class);
        classList.add(JunkItem.class);

        return classList;
    }

    public boolean containsClass(Class<? extends Item> itemClass) {
        return itemClassList.contains(itemClass);

    }

    public boolean checkClassCondition(Class<? extends Item> itemClass) {
        if (itemClassList != null) {
            return containsClass(itemClass);
        }

        return this.itemClass == itemClass;
    }

    public void addItemClass(Class<? extends Item> itemClass) {
        itemClassList.add(itemClass);
    }

    public void removeItemClass(Class<? extends Item> itemClass) {
        itemClassList.remove(itemClass);
    }
}
