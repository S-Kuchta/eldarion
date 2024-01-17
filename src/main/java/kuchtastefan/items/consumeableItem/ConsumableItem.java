package kuchtastefan.items.consumeableItem;

import kuchtastefan.ability.Ability;
import kuchtastefan.items.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
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
}
