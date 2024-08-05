package kuchtastefan.item.itemFilter;

import kuchtastefan.item.Item;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.item.itemFilter.listsToFilter.ItemClassList;
import kuchtastefan.utility.LetterToNumber;
import kuchtastefan.utility.printUtil.PrintUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ItemClassFilter {

    private final List<Class<? extends Item>> itemClassList;

    @SafeVarargs
    public ItemClassFilter(Class<? extends Item>... itemClass) {
        if (itemClass.length == 0) {
            this.itemClassList = ItemClassList.allClassList();
            return;
        }

        this.itemClassList = new ArrayList<>(List.of(itemClass));
    }


    public void printClassChoice(List<Class<? extends Item>> classes) {
        int index = 1;
        for (Class<? extends Item> itemClass : classes) {
            String className;
            if (containsClass(itemClass)) {
                className = itemClass.getSimpleName();
            } else {
                className = ConsoleColor.WHITE + itemClass.getSimpleName() + ConsoleColor.RESET;
            }

            PrintUtil.printIndexAndText(LetterToNumber.getStringFromValue(index++), className);
        }

        System.out.println();
    }

    public void handleClassChoice(Class<? extends Item> itemClass) {
        if (containsClass(itemClass)) {
            removeItemClass(itemClass);
        } else {
            addItemClass(itemClass);
        }
    }

    public boolean containsClass(Class<? extends Item> itemClass) {
        return itemClassList.contains(itemClass);
    }

    private void addItemClass(Class<? extends Item> itemClass) {
        itemClassList.add(itemClass);
    }

    private void removeItemClass(Class<? extends Item> itemClass) {
        itemClassList.remove(itemClass);
    }
}
