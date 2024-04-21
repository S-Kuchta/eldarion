package kuchtastefan.item.specificItems.craftingItem;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.itemType.HaveType;
import kuchtastefan.item.Item;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CraftingReagentItem extends Item implements HaveType {

    private CraftingReagentItemType itemType;


    public CraftingReagentItem(Integer itemId, String name, double price, CraftingReagentItemType itemType, int itemLevel) {
        super(itemId, name, price, itemLevel);
        this.itemType = itemType;
    }

    @Override
    public void printItemDescription(Hero hero) {
        System.out.println(this.getName() + ", Item Type: " + this.getItemType()
                + ", iLevel: " + this.getItemLevel() + ", Item price: " + this.getPrice() + " golds"
                + "(Sell Value: " + this.returnSellItemPrice() + ")");
    }
}
