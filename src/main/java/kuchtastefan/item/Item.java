package kuchtastefan.item;

import kuchtastefan.ability.Ability;

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

    public void improveItemQuality(Item item) {
        ItemQuality itemQuality = item.getItemQuality();
        if (itemQuality == ItemQuality.BASIC) {
            item.setItemQuality(ItemQuality.IMPROVED);
            setItemAbilities(item);
        } else if (itemQuality == ItemQuality.IMPROVED) {
            item.setItemQuality(ItemQuality.SUPERIOR);
            setItemAbilities(item);
        }
    }

    private void setItemAbilities(Item item) {

        for (Map.Entry<Ability, Integer> abilityEntry : item.getAbilities().entrySet())
            for (Ability ability : Ability.values()) {
                if (abilityEntry.getValue() != 0) {
                    item.getAbilities().put(ability, abilityEntry.getValue() + 1);
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
