package kuchtastefan.item.specificItems.questItem;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestItem extends Item {

    final int questId;

    public QuestItem(Integer itemId, String name, double price, int itemLevel, int questId) {
        super(itemId, name, price, itemLevel);
        this.questId = questId;
    }

    @Override
    public void printItemDescription(Hero hero) {
        System.out.println(this.name);
    }
}
