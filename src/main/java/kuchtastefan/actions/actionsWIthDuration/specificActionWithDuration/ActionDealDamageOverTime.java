package kuchtastefan.actions.actionsWIthDuration.specificActionWithDuration;

import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.actionsWIthDuration.ActionDurationType;
import kuchtastefan.actions.ActionStatusEffect;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.actions.actionsWIthDuration.actionMarkerInterface.ActionWithIncreasedValueByAbility;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.utility.ConsoleColor;

public class ActionDealDamageOverTime extends ActionWithDuration implements ActionWithIncreasedValueByAbility {
    public ActionDealDamageOverTime(ActionName actionName, ActionEffectOn actionEffectOn,
                                    int maxActionValue, int maxActionTurns, int actionMaxStacks,
                                    ActionDurationType actionDurationType, int chanceToPerformAction,
                                    boolean canBeActionCriticalHit, ActionStatusEffect actionStatusEffect) {

        super(actionName, actionEffectOn, maxActionValue, maxActionTurns, actionMaxStacks,
                actionDurationType, chanceToPerformAction, canBeActionCriticalHit, actionStatusEffect, 1);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        int damageWithStacks = this.getCurrentActionValue() * this.getActionCurrentStacks();

        System.out.print("\t" + ConsoleColor.YELLOW + this.getActionName() + ConsoleColor.RESET + ": ");
        gameCharacter.receiveDamage(damageWithStacks);
    }

    @Override
    public void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget) {
        System.out.print("Deal " + ConsoleColor.RED_BRIGHT
                + spellTarget.returnDamageAfterResistDamage(this.returnActionValueRange(spellCaster).minimumValue()) * this.getMaxActionTurns()
                + ConsoleColor.RESET + " - " + ConsoleColor.RED_BRIGHT
                + spellTarget.returnDamageAfterResistDamage(this.returnActionValueRange(spellCaster).maximumValue()) * this.getMaxActionTurns()
                + ConsoleColor.RESET
                + " damage over " + this.getMaxActionTurns() + " turns to "
                + ConsoleColor.YELLOW + this.returnTargetName(spellCaster, spellTarget) + ConsoleColor.RESET);
    }
}
