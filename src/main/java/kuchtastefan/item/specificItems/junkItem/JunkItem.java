package kuchtastefan.item.specificItems.junkItem;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.utility.ConsoleColor;

public class JunkItem extends Item {
    public JunkItem(Integer itemId, String name, double price, int itemLevel) {
        super(itemId, name, price, itemLevel);
    }

    @Override
    public void printItemDescription(Hero hero) {

        System.out.println(this.getName() + ", Item price: " + this.getPrice() + " golds"
                + "(Sell Value: " + this.returnSellItemPrice() + ")");
    }

    @Override
    public String getName() {
        return ConsoleColor.WHITE + this.name + ConsoleColor.RESET;
    }
}
