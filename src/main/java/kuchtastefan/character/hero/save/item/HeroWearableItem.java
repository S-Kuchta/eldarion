package kuchtastefan.character.hero.save.item;

import kuchtastefan.item.specificItems.wearableItem.WearableItemQuality;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeroWearableItem extends HeroItem {

    private WearableItemQuality quality;

    public HeroWearableItem(int itemId, WearableItemQuality quality) {
        super(itemId);
        this.quality = quality;
    }
}
