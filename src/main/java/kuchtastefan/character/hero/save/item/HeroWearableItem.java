package kuchtastefan.character.hero.save.item;

import kuchtastefan.character.hero.save.SaveGameEntity;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import kuchtastefan.item.specificItems.wearableItem.WearableItem;
import kuchtastefan.item.specificItems.wearableItem.WearableItemQuality;
import kuchtastefan.utility.IntegerLength;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeroWearableItem extends HeroItem implements SaveGameEntity {

    private WearableItemQuality quality;

    public HeroWearableItem(int id, int amount, WearableItemQuality quality) {
        super(id, amount);
        this.quality = quality;
    }

    @Override
    public Item getItem() {
        WearableItem item = (WearableItem) ItemDB.returnItemFromDB(getId());
        item.setWearableItemQuality(this.quality);
        return item;
    }

    @Override
    public int getId() {
        if (IntegerLength.getIntegerLength(this.id) > 3) {
            return this.id / 10;
        } else {
            return this.id;
        }
    }
}
