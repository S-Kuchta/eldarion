package kuchtastefan.items;

import java.util.Objects;

public class Item {

    protected String name;
    protected double price;
    protected int itemLevel;

    public Item(String name, double price, int itemLevel) {
        this.name = name;
        this.price = price;
        this.itemLevel = itemLevel;
    }

    public double returnSellItemPrice() {
        return Math.floor(this.price * 0.7);
    }

    public String getName() {
        return name;
    }

    public Integer getItemLevel() {
        return itemLevel;
    }

    public void setItemLevel(int itemLevel) {
        this.itemLevel = itemLevel;
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

    @Override
    public String toString() {
        return "name: " + name + ", Item price: " + price + ", Item level: " + itemLevel;
    }
}
