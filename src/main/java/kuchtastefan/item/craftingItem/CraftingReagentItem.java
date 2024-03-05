package kuchtastefan.item.craftingItem;

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
}
