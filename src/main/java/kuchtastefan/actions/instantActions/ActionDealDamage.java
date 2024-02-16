package kuchtastefan.actions.instantActions;

import kuchtastefan.actions.Action;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.characters.GameCharacter;
import kuchtastefan.utility.ConsoleColor;

public class ActionDealDamage extends Action {
    public ActionDealDamage(ActionName actionName, ActionEffectOn actionEffectOn, int maxActionValue,
                            int chanceToPerformAction, boolean canBeActionCriticalHit) {

        super(actionName, actionEffectOn, maxActionValue, chanceToPerformAction, canBeActionCriticalHit);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        System.out.print("\t"+ ConsoleColor.YELLOW + this.getActionName() + ConsoleColor.RESET + ": ");
        gameCharacter.receiveDamage(this.currentActionValue);
    }
}
