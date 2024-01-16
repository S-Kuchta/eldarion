package kuchtastefan.items.wearableItem;

import kuchtastefan.ability.Ability;
import kuchtastefan.constant.Constant;
import kuchtastefan.items.Item;

import java.util.Map;
import java.util.Objects;

public class WearableItem extends Item {

    private WearableItemType wearableItemType;
    private final Map<Ability, Integer> abilities;
    private WearableItemQuality wearableItemQuality;

    public WearableItem(String name, double price, int itemLevel,
                        WearableItemType wearableItemType,
                        Map<Ability, Integer> abilities,
                        WearableItemQuality wearableItemQuality) {
        super(name, price, itemLevel);
        this.wearableItemType = wearableItemType;
        this.abilities = abilities;
        this.wearableItemQuality = wearableItemQuality;
    }

    public void setItemAbilities(WearableItem wearableItem) {
        for (Ability ability : Ability.values()) {
            if (wearableItem.getAbilities().get(ability) != 0) {
                if (ability.equals(Ability.HEALTH)) {
                    wearableItem.getAbilities().put(ability, (wearableItem.getAbilities().get(ability) + Constant.HEALTH_OF_ONE_POINT));
                } else {
                    wearableItem.getAbilities().put(ability, (wearableItem.getAbilities().get(ability) + 1));
                }
            }
        }
    }

    public Map<Ability, Integer> getAbilities() {
        return abilities;
    }

    public WearableItemType getWearableItemType() {
        return wearableItemType;
    }

    public void setWearableItemType(WearableItemType wearableItemType) {
        this.wearableItemType = wearableItemType;
    }

    public WearableItemQuality getItemQuality() {
        return wearableItemQuality;
    }

    public void setItemQuality(WearableItemQuality wearableItemQuality) {
        this.wearableItemQuality = wearableItemQuality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        WearableItem that = (WearableItem) o;
        return itemLevel == that.itemLevel && wearableItemType == that.wearableItemType && Objects.equals(abilities, that.abilities) && wearableItemQuality == that.wearableItemQuality;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), wearableItemType, abilities, itemLevel, wearableItemQuality);
    }


}
