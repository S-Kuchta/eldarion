package kuchtastefan.item.itemFilter;

import kuchtastefan.item.itemType.ItemType;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.ItemTypeList;
import kuchtastefan.utility.LetterToNumber;
import kuchtastefan.utility.printUtil.PrintUtil;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class ItemTypeFilter {

    private final Set<ItemType> itemTypes;
    private final boolean filterTypes;


    public ItemTypeFilter(boolean filterTypes) {
        this.itemTypes = ItemTypeList.allTypesList();
        this.filterTypes = filterTypes;
    }

    public ItemTypeFilter(ItemType... itemTypes) {
        this.filterTypes = itemTypes.length != 0;
        this.itemTypes = new HashSet<>(List.of(itemTypes));

    }


    public void printTypeChoice(ItemFilter itemFilter, int indexStart) {
        int index = indexStart;
        List<ItemType> itemTypes = ItemTypeList.itemTypesByClass(itemFilter.getItemClassFilter());

        if (itemTypes.isEmpty()) {
            return;
        }

        for (ItemType itemType : itemTypes) {
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
            removeItemType(itemType);
        } else {
            addItemType(itemType);
        }
    }

    public boolean containsType(ItemType itemType) {
        return itemTypes.contains(itemType);
    }

    private void addItemType(ItemType itemType) {
        itemTypes.add(itemType);
    }

    private void removeItemType(ItemType itemType) {
        itemTypes.remove(itemType);
    }
}
