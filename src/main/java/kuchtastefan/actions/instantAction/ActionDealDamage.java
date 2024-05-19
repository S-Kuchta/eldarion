package kuchtastefan.actions.instantAction;

import kuchtastefan.actions.Action;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.actionValue.ActionWithIncreasedValueByPrimaryAbility;
import kuchtastefan.actions.criticalHit.CanBeCriticalHit;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.utility.ConsoleColor;

public class ActionDealDamage extends Action implements ActionWithIncreasedValueByPrimaryAbility, CanBeCriticalHit {
    public ActionDealDamage(ActionName actionName, ActionEffectOn actionEffectOn, int baseActionValue, int chanceToPerformAction) {

        super(actionName, actionEffectOn, baseActionValue, chanceToPerformAction);
    }

    @Override
    public void performAction() {
        this.currentActionValue = this.charactersInvolvedInBattle.getSpellTarget().returnDamageAfterResistDamage(
                this.returnFinalValue(charactersInvolvedInBattle.getSpellCaster()));
        this.charactersInvolvedInBattle.getSpellTarget().receiveDamage(this.currentActionValue);

        System.out.println("\t" + ConsoleColor.YELLOW + this.getActionName() + ConsoleColor.RESET + ": " + ConsoleColor.RED_BRIGHT
                + this.currentActionValue + ConsoleColor.RESET + " damage to " + charactersInvolvedInBattle.getSpellTarget().getName());
    }

    @Override
    public void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget) {
        System.out.print("Deal " + ConsoleColor.RED_BRIGHT
                + spellTarget.returnDamageAfterResistDamage(this.returnActionValueRange(spellCaster).minimumValue())
                + ConsoleColor.RESET + " - " + ConsoleColor.RED_BRIGHT
                + spellTarget.returnDamageAfterResistDamage(this.returnActionValueRange(spellCaster).maximumValue())
                + ConsoleColor.RESET
                + " damage to " + ConsoleColor.YELLOW + this.returnTargetName(spellCaster, spellTarget) + ConsoleColor.RESET);
    }

    @Override
    public int returnPriorityPoints(GameCharacter spellCaster, GameCharacter spellTarget) {
        return 1;
    }
}
