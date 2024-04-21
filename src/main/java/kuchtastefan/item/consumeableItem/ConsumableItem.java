package kuchtastefan.item.consumeableItem;

import kuchtastefan.actions.Action;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.HaveType;
import kuchtastefan.item.Item;
import kuchtastefan.item.UsableItem;
import kuchtastefan.service.ActionService;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.PrintUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ConsumableItem extends Item implements UsableItem, HaveType {

    private ConsumableItemType itemType;
    private final List<? extends Action> actionList;


    public ConsumableItem(Integer itemId, String name, double price, int itemLevel, ConsumableItemType itemType, List<? extends Action> actionList) {
        super(itemId, name, price, itemLevel);
        this.itemType = itemType;
        this.actionList = actionList;
    }

    public boolean useItem(Hero hero) {
        if (hero.isInCombat() && this.itemType.equals(ConsumableItemType.FOOD)) {
            System.out.println("\t" + ConsoleColor.RED + this.getName() + "Can't be used in combat!" + ConsoleColor.RESET);
        } else {
            ActionService actionService = new ActionService();
            for (Action action : this.actionList) {
                actionService.applyActionToTarget(action, hero);
            }

            hero.getHeroInventory().removeItemFromHeroInventory(this, 1);
            return true;
        }

        return false;
    }

    @Override
    public void printItemDescription(Hero hero) {

        System.out.print(this.getName()
                + ", " + this.getItemType()
                + ", iLevel: " + this.getItemLevel());
        System.out.print(", Item Price: " + this.getPrice()
                + "(Sell Value: " + this.returnSellItemPrice() + ")");

        System.out.println();
        for (Action action : this.getActionList()) {
//            System.out.print("\t- " + ConsoleColor.YELLOW + action.getActionName() + ConsoleColor.RESET + " on " + action.getActionEffectOn() + " ");
            action.printActionDescription(hero, hero);
            System.out.println();
//            PrintUtil.printActionDetails(action, action.getCurrentActionValue());
//            PrintUtil.printActionDetails(action, action.returnActionValueRange(hero).onlyValue());
        }
    }
}
