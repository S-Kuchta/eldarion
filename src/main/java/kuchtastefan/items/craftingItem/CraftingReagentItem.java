package kuchtastefan.items.craftingItem;

import kuchtastefan.items.Item;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CraftingReagentItem extends Item {

    private CraftingReagentItemType craftingReagentItemType;


    public CraftingReagentItem(String name, double price, CraftingReagentItemType craftingReagentItemType, int itemLevel) {
        super(name, price, itemLevel);
        this.craftingReagentItemType = craftingReagentItemType;
    }
}
