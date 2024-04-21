package kuchtastefan.actions.instantAction;

import kuchtastefan.actions.Action;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.actionsWIthDuration.actionMarkerInterface.actionValue.ActionWithIncreasedValueByPrimaryAbility;
import kuchtastefan.actions.actionsWIthDuration.actionMarkerInterface.criticalHit.CanBeCriticalHit;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.utility.ConsoleColor;

public class ActionDealDamage extends Action implements ActionWithIncreasedValueByPrimaryAbility, CanBeCriticalHit {
    public ActionDealDamage(ActionName actionName, ActionEffectOn actionEffectOn, int baseActionValue, int chanceToPerformAction) {

        super(actionName, actionEffectOn, baseActionValue, chanceToPerformAction);
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

    @Override
    public int returnPriorityPoints(GameCharacter spellCaster, GameCharacter spellTarget) {
        return 1;
    }
}
