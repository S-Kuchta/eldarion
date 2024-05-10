package kuchtastefan.item.specificItems.questItem;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.usableItem.UsableItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsableQuestItem extends QuestItem implements UsableItem {

    private boolean wasUsed;

    public UsableQuestItem(Integer itemId, String name, double price, int itemLevel, boolean wasUsed, int questId) {
        super(itemId, name, price, itemLevel, questId);
        this.wasUsed = wasUsed;
    }

    @Override
    public boolean useItem(Hero hero) {
        return true;
    }
}
