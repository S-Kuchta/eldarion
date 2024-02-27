package kuchtastefan.actions.actionsWIthDuration;

import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.utility.ConsoleColor;

public class ActionDealDamageOverTime extends ActionWithDuration {
    public ActionDealDamageOverTime(ActionName actionName, ActionEffectOn actionEffectOn,
                                    int maxActionValue, int maxActionTurns, int actionMaxStacks,
                                    ActionDurationType actionDurationType, int chanceToPerformAction,
                                    boolean canBeActionCriticalHit, ActionStatusEffect actionStatusEffect) {

        super(actionName, actionEffectOn, maxActionValue, maxActionTurns, actionMaxStacks,
                actionDurationType, chanceToPerformAction, canBeActionCriticalHit, actionStatusEffect);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        int damageWithStacks = this.getCurrentActionValue() * this.getActionCurrentStacks();

        System.out.print("\t" + ConsoleColor.YELLOW + this.getActionName() + ConsoleColor.RESET + ": ");
        gameCharacter.receiveDamage(damageWithStacks);
    }
}
