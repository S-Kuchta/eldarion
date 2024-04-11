package kuchtastefan.actions.instantActions;

import kuchtastefan.actions.Action;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.actionsWIthDuration.actionMarkerInterface.ActionWithIncreasedValueByAbility;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.utility.ConsoleColor;

public class ActionDealDamage extends Action implements ActionWithIncreasedValueByAbility {
    public ActionDealDamage(ActionName actionName, ActionEffectOn actionEffectOn, int maxActionValue,
                            int chanceToPerformAction, boolean canBeActionCriticalHit) {

        super(actionName, actionEffectOn, maxActionValue, chanceToPerformAction, canBeActionCriticalHit, 1);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        System.out.print("\t" + ConsoleColor.YELLOW + this.getActionName() + ConsoleColor.RESET + ": ");
        gameCharacter.receiveDamage(this.currentActionValue);
    }

    @Override
    public void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget) {
        System.out.print("Deal " + ConsoleColor.RED_BRIGHT
                + spellTarget.returnDamageAfterResistDamage(this.returnActionValueRange(spellCaster).minimumValue())
                + ConsoleColor.RESET + " to " + ConsoleColor.RED_BRIGHT
                + spellTarget.returnDamageAfterResistDamage(this.returnActionValueRange(spellCaster).maximumValue())
                + ConsoleColor.RESET
                + " damage to " + ConsoleColor.YELLOW + this.returnTargetName(spellCaster, spellTarget) + ConsoleColor.RESET);
    }
}
