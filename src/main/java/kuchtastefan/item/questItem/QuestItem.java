package kuchtastefan.item.questItem;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestItem extends Item {

    public QuestItem(Integer itemId, String name, double price, int itemLevel) {
        super(itemId, name, price, itemLevel);
    }

    @Override
    public void printItemDescription(Hero hero) {
        System.out.print(this.name);
    }
}
