package kuchtastefan.item.craftingItem;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CraftingReagentItem extends Item {

    private CraftingReagentItemType craftingReagentItemType;


    public CraftingReagentItem(Integer itemId, String name, double price, CraftingReagentItemType craftingReagentItemType, int itemLevel) {
        super(itemId, name, price, itemLevel);
        this.craftingReagentItemType = craftingReagentItemType;
    }

    @Override
    public void printItemDescription(Hero hero) {
        System.out.println(this.getName() + ", Item Type: " + this.getCraftingReagentItemType()
                + ", iLevel: " + this.getItemLevel() + ", Item price: " + this.getPrice() + " golds"
                + "(Sell Value: " + this.returnSellItemPrice() + ")");
    }
}
