package kuchtastefan.item.itemFilter;

import kuchtastefan.item.Item;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ItemClassFilter {

    private final Class<? extends Item> itemClass;
    private final List<Class<? extends Item>> itemClassList;

    public ItemClassFilter(Class<? extends Item> itemClass) {
        this.itemClass = itemClass;
        this.itemClassList = new ArrayList<>();
    }

    public ItemClassFilter(Class<? extends Item> itemClass, List<Class<? extends Item>> itemList) {
        this.itemClass = itemClass;
        this.itemClassList = itemList;
    }

    public ItemClassFilter(List<Class<? extends Item>> itemList) {
        this.itemClassList = itemList;
        this.itemClass = itemList.getFirst();
    }


    public boolean containsClass(Class<? extends Item> itemClass) {
        return itemClassList.contains(itemClass);
    }

    public boolean checkClassCondition(Class<? extends Item> itemClass) {
        if (!itemClassList.isEmpty()) {
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
