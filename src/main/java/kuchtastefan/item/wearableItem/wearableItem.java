package kuchtastefan.item.wearableItem;

import kuchtastefan.ability.Ability;

import java.util.Map;
import java.util.Objects;

public class wearableItem {

    private String name;
    private wearableItemType wearableItemType;
    private Map<Ability, Integer> abilities;
    private int itemLevel;
    private int price;
    private wearableItemQuality wearableItemQuality;

    public wearableItem() {
    }

    public wearableItem(String name, wearableItemType type, Map<Ability, Integer> abilities, int itemLevel) {
        this.name = name;
        this.wearableItemType = type;
        this.abilities = abilities;
        this.itemLevel = itemLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItemAbilities(wearableItem wearableItem) {
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

    public wearableItemType getType() {
        return wearableItemType;
    }

    public void setItemType(wearableItemType wearableItemType) {
        this.wearableItemType = wearableItemType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public wearableItemQuality getItemQuality() {
        return wearableItemQuality;
    }

    public void setItemQuality(wearableItemQuality wearableItemQuality) {
        this.wearableItemQuality = wearableItemQuality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        wearableItem wearableItem = (wearableItem) o;
        return itemLevel == wearableItem.itemLevel && price == wearableItem.price && Objects.equals(name, wearableItem.name) && wearableItemType == wearableItem.wearableItemType && Objects.equals(abilities, wearableItem.abilities) && wearableItemQuality == wearableItem.wearableItemQuality;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, wearableItemType, abilities, itemLevel, price, wearableItemQuality);
    }
}
