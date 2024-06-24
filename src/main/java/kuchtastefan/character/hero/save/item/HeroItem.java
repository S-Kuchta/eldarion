package kuchtastefan.character.hero.save.item;

import kuchtastefan.character.hero.save.SaveGameEntity;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import lombok.Getter;
import lombok.Setter;

@Getter
public class HeroItem implements SaveGameEntity {

    protected final int id;
    @Setter
    private int amount;

    public HeroItem(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public void increaseAmount(int amount) {
        this.amount += amount;
    }

    public void decreaseAmount(int amount) {
        this.amount -= amount;
    }

    public Item getItem() {
        return ItemDB.returnItemFromDB(this.id);
    }

}
