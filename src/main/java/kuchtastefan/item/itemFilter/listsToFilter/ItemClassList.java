package kuchtastefan.item.itemFilter.listsToFilter;

import kuchtastefan.item.Item;
import kuchtastefan.item.specificItems.consumeableItem.ConsumableItem;
import kuchtastefan.item.specificItems.craftingItem.CraftingReagentItem;
import kuchtastefan.item.specificItems.junkItem.JunkItem;
import kuchtastefan.item.specificItems.keyItem.KeyItem;
import kuchtastefan.item.specificItems.questItem.QuestItem;
import kuchtastefan.item.specificItems.questItem.UsableQuestItem;
import kuchtastefan.item.specificItems.wearableItem.WearableItem;

import java.util.ArrayList;
import java.util.List;

public class ItemClassList {

    public static List<Class<? extends Item>> allClassList() {
        List<Class<? extends Item>> classList = new ArrayList<>();
        classList.add(WearableItem.class);
        classList.add(ConsumableItem.class);
        classList.add(CraftingReagentItem.class);
        classList.add(QuestItem.class);
        classList.add(UsableQuestItem.class);
        classList.add(KeyItem.class);
        classList.add(JunkItem.class);

        return classList;
    }
}
