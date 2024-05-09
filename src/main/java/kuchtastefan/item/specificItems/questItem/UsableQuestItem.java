package kuchtastefan.item.specificItems.questItem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsableQuestItem extends QuestItem {

    private boolean wasUsed;

    public UsableQuestItem(Integer itemId, String name, double price, int itemLevel, boolean wasUsed, int questId) {
        super(itemId, name, price, itemLevel, questId);
        this.wasUsed = wasUsed;
    }
}
