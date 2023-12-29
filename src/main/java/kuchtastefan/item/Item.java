package kuchtastefan.item;

import kuchtastefan.ability.Ability;

import java.util.Map;

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
}
