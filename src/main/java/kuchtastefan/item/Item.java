package kuchtastefan.item;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.constant.Constant;
import kuchtastefan.utility.ConsoleColor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public abstract class Item {

    private final Integer itemId;
    protected String name;
    protected double price;
    protected Integer itemLevel;


    public Item(Integer itemId, String name, double price, int itemLevel) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.itemLevel = itemLevel;
    }


    public abstract void printItemDescription(Hero hero);

    public double returnSellItemPrice() {
        return Math.floor(this.price * Constant.SELL_ITEM_PRICE_MULTIPLIER);
    }

    public String getName() {
        return ConsoleColor.YELLOW + name + ConsoleColor.RESET;
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
