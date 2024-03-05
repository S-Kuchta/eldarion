package kuchtastefan.item.consumeableItem;

import kuchtastefan.actions.Action;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ConsumableItem extends Item {

    private ConsumableItemType consumableItemType;
    private final List<? extends Action> actionList;


    public ConsumableItem(Integer itemId, String name, double price, int itemLevel, ConsumableItemType consumableItemType, List<? extends Action> actionList) {
        super(itemId, name, price, itemLevel);
        this.consumableItemType = consumableItemType;
        this.actionList = actionList;
    }

    public void useItem(Hero hero) {
        for (Action action : this.actionList) {
            if (action instanceof ActionWithDuration) {
                hero.addActionWithDuration((ActionWithDuration) action);
            } else {
                action.performAction(hero);
            }
        }

        hero.getHeroInventory().removeItemFromHeroInventory(this);
    }
}
