package kuchtastefan.item;

import kuchtastefan.ability.Ability;
import kuchtastefan.domain.Hero;

import java.util.Map;
import java.util.Objects;

public class Item {

    private String name;
    private ItemType itemType;
    private Map<Ability, Integer> abilities;
    private int itemLevel;
    private int price;
    private ItemQuality itemQuality;

    public Item() {
    }

    public Item(String name, ItemType type, Map<Ability, Integer> abilities, int itemLevel) {
        this.name = name;
        this.itemType = type;
        this.abilities = abilities;
        this.itemLevel = itemLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItemAbilities(Item item) {
        for (Ability ability : Ability.values()) {
            if (item.getAbilities().get(ability) != 0) {
                item.getAbilities().put(ability, (item.getAbilities().get(ability) + 1));
            }
        }
    }

    public Map<Ability, Integer> getAbilities() {
        return abilities;
    }

    public int getItemLevel() {
        return itemLevel;
    }

    public ItemType getType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ItemQuality getItemQuality() {
        return itemQuality;
    }

    public void setItemQuality(ItemQuality itemQuality) {
        this.itemQuality = itemQuality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return itemLevel == item.itemLevel && price == item.price && Objects.equals(name, item.name) && itemType == item.itemType && Objects.equals(abilities, item.abilities) && itemQuality == item.itemQuality;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, itemType, abilities, itemLevel, price, itemQuality);
    }
}
