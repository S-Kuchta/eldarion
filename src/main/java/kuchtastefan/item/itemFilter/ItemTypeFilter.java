package kuchtastefan.item.itemFilter;

import kuchtastefan.item.itemFilter.listsToFilter.ItemTypeList;
import kuchtastefan.item.itemType.ItemType;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.LetterToNumber;
import kuchtastefan.utility.printUtil.PrintUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class ItemTypeFilter {

    private final Set<ItemType> itemTypes;
    private final boolean filterTypes;
    private int numOfIndexes;

    public ItemTypeFilter(boolean filterTypes) {
        this.itemTypes = new HashSet<>();
        this.filterTypes = filterTypes;
    }

    public ItemTypeFilter(ItemType... itemTypes) {
        this.itemTypes = new HashSet<>(Set.of(itemTypes));
        this.filterTypes = itemTypes.length != 0;
    }


    public void printTypeChoice(ItemFilter itemFilter, int indexStart) {
        int index = indexStart;
        List<ItemType> itemTypesPrintCopy = new ArrayList<>(ItemTypeList.itemTypesByClass(itemFilter.getItemClassFilter()));
        numOfIndexes = itemTypesPrintCopy.size();

        if (itemTypesPrintCopy.isEmpty()) {
            return;
        }

        for (ItemType itemType : itemTypesPrintCopy) {
            String typeName;
            if (containsType(itemType)) {
                typeName = itemType.toString();
            } else {
                typeName = ConsoleColor.WHITE + itemType.toString() + ConsoleColor.RESET;
            }

            PrintUtil.printIndexAndText(LetterToNumber.getStringFromValue(index++), typeName);
        }

        System.out.println();

    }

    public void handleTypeChoice(ItemType itemType) {
        if (containsType(itemType)) {
            itemTypes.remove(itemType);
        } else {
            itemTypes.add(itemType);
        }
    }

    public boolean containsType(ItemType itemType) {
        return itemTypes.contains(itemType);
    }
}
