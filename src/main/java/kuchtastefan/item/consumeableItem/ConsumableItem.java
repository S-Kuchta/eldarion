package kuchtastefan.item.consumeableItem;

import kuchtastefan.item.Item;

public class ConsumableItem extends Item {

    private final int restoreAmount;
    private final ConsumableItemType consumableItemType;

    public ConsumableItem(String name, double price, int itemLevel, int restoreAmount, ConsumableItemType consumableItemType) {
        super(name, price, itemLevel);
        this.restoreAmount = restoreAmount;
        this.consumableItemType = consumableItemType;
    }

    public int getRestoreAmount() {
        return restoreAmount;
    }

    public ConsumableItemType getConsumableItemType() {
        return consumableItemType;
    }
}
