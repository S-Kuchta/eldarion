package kuchtastefan.item;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import kuchtastefan.item.craftingItem.CraftingReagentItem;
import kuchtastefan.item.wearableItem.WearableItem;

import java.util.Objects;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        property = "itemClass",
        include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = WearableItem.class, name = "wearableItem"),
        @JsonSubTypes.Type(value = CraftingReagentItem.class, name = "craftingReagentItem")
})
public abstract class Item {
    protected String name;
    protected double price;
    protected int itemLevel;

    public Item(String name, double price, int itemLevel) {
        this.name = name;
        this.price = price;
        this.itemLevel = itemLevel;
    }

    public String getName() {
        return name;
    }

    public int getItemLevel() {
        return itemLevel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Double.compare(price, item.price) == 0 && Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}
