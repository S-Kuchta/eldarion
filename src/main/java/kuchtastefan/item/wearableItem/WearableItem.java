package kuchtastefan.item.wearableItem;

import kuchtastefan.ability.Ability;
import kuchtastefan.item.Item;

import java.util.Map;

public class WearableItem extends Item {

    private WearableItemType wearableItemType;
    private Map<Ability, Integer> abilities;
    private int itemLevel;
    private WearableItemQuality wearableItemQuality;

    public WearableItem(String name, double price) {
        super(name, price);
    }

    public WearableItem(String name,
                        double price,
                        WearableItemType wearableItemType,
                        Map<Ability, Integer> abilities,
                        int itemLevel) {
        super(name, price);
        this.wearableItemType = wearableItemType;
        this.abilities = abilities;
        this.itemLevel = itemLevel;
    }

    public void setItemAbilities(WearableItem wearableItem) {
        for (Ability ability : Ability.values()) {
            if (wearableItem.getAbilities().get(ability) != 0) {
                wearableItem.getAbilities().put(ability, (wearableItem.getAbilities().get(ability) + 1));
            }
        }
    }

    public Map<Ability, Integer> getAbilities() {
        return abilities;
    }

    public int getItemLevel() {
        return itemLevel;
    }

    public WearableItemType getType() {
        return wearableItemType;
    }

    public void setItemType(WearableItemType wearableItemType) {
        this.wearableItemType = wearableItemType;
    }

    public WearableItemQuality getItemQuality() {
        return wearableItemQuality;
    }

    public void setItemQuality(WearableItemQuality wearableItemQuality) {
        this.wearableItemQuality = wearableItemQuality;
    }

}
