package kuchtastefan.item.itemFilter;

import kuchtastefan.item.specificItems.wearableItem.WearableItem;
import kuchtastefan.item.specificItems.wearableItem.WearableItemQuality;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.LetterToNumber;
import kuchtastefan.utility.printUtil.PrintUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class WearableItemQualityFilter {

    private final List<WearableItemQuality> wearableItemQualities;
    private final boolean filterQualities;

    public WearableItemQualityFilter() {
        this.wearableItemQualities = new ArrayList<>();
        this.filterQualities = false;
    }

    public WearableItemQualityFilter(WearableItemQuality... wearableItemQualities) {
        this.wearableItemQualities = new ArrayList<>(List.of(wearableItemQualities));
        this.filterQualities = true;
    }


    public void printQualityChoice(ItemFilter itemFilter, int indexStart) {
        int index = indexStart;

        if (!itemFilter.getItemClassFilter().containsClass(WearableItem.class)) {
            return;
        }

        System.out.print("\tWearable Item Quality:");
        for (WearableItemQuality quality : WearableItemQuality.values()) {
            String typeName;
            if (containsQuality(quality)) {
                typeName = quality.toString();
            } else {
                typeName = ConsoleColor.WHITE + quality.toString() + ConsoleColor.RESET;
            }

            PrintUtil.printIndexAndText(LetterToNumber.getStringFromValue(index++), typeName);
        }

        System.out.println();
    }

    public void handleQualityChoice(WearableItemQuality quality) {
        if (!containsQuality(quality)) {
            wearableItemQualities.add(quality);
        } else {
            wearableItemQualities.remove(quality);
        }
    }

    public boolean containsQuality(WearableItemQuality quality) {
        return wearableItemQualities.contains(quality);
    }

}
