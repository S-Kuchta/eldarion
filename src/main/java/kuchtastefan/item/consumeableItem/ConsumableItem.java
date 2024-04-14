package kuchtastefan.item.consumeableItem;

import kuchtastefan.actions.Action;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.service.ActionService;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.PrintUtil;
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
        ActionService actionService = new ActionService();
        for (Action action : this.actionList) {
            actionService.applyActionToTarget(action, hero);
        }

        hero.getHeroInventory().removeItemFromHeroInventory(this);
    }

    @Override
    public void printItemDescription(Hero hero) {

        System.out.print(this.getName()
                + ", " + this.getConsumableItemType()
                + ", iLevel: " + this.getItemLevel());
        System.out.print(", Item Price: " + this.getPrice()
                + "(Sell Value: " + this.returnSellItemPrice() + ")");

        System.out.println();
        for (Action action : this.getActionList()) {
            System.out.print("\t- " + ConsoleColor.YELLOW + action.getActionName() + ConsoleColor.RESET + " on " + action.getActionEffectOn() + " ");
            PrintUtil.printActionDetails(action, action.getCurrentActionValue());
        }
    }
}
