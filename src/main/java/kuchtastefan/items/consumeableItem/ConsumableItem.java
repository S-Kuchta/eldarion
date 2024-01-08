package kuchtastefan.items.consumeableItem;

import kuchtastefan.ability.Ability;
import kuchtastefan.items.Item;

import java.util.HashMap;
import java.util.Map;

public class ConsumableItem extends Item {

    private Integer restoreAmount;
    private Map<Ability, Integer> increaseAbilityPoint;
    private ConsumableItemType consumableItemType;

    public ConsumableItem(String name, double price, int itemLevel, int restoreAmount, ConsumableItemType consumableItemType) {
        super(name, price, itemLevel);
        this.restoreAmount = restoreAmount;
        this.consumableItemType = consumableItemType;
        this.increaseAbilityPoint = new HashMap<>();
    }

    public Integer getRestoreAmount() {
        return restoreAmount;
    }

    public Map<Ability, Integer> getIncreaseAbilityPoint() {
        return increaseAbilityPoint;
    }

    public void setRestoreAmount(Integer restoreAmount) {
        this.restoreAmount = restoreAmount;
    }

    public void setConsumableItemType(ConsumableItemType consumableItemType) {
        this.consumableItemType = consumableItemType;
    }

    public void setIncreaseAbilityPoint(Map<Ability, Integer> increaseAbilityPoint) {
        this.increaseAbilityPoint = increaseAbilityPoint;
    }

    public ConsumableItemType getConsumableItemType() {
        return consumableItemType;
    }
}
