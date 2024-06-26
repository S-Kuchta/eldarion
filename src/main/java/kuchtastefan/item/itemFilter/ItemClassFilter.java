package kuchtastefan.item.itemFilter;

import kuchtastefan.item.Item;
import kuchtastefan.utility.ItemClassList;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ItemClassFilter {

    private final List<Class<? extends Item>> itemClassList;

    public ItemClassFilter() {
        this.itemClassList = ItemClassList.allClassList();
    }

    @SafeVarargs
    public ItemClassFilter(Class<? extends Item>... itemClass) {
        this.itemClassList = new ArrayList<>(List.of(itemClass));
    }


    public boolean containsClass(Class<? extends Item> itemClass) {
        return itemClassList.contains(itemClass);
    }

    public boolean checkClassCondition(Class<? extends Item> itemClass) {
        return containsClass(itemClass);
    }

    public void addItemClass(Class<? extends Item> itemClass) {
        itemClassList.add(itemClass);
    }

    public void removeItemClass(Class<? extends Item> itemClass) {
        itemClassList.remove(itemClass);
    }
}
