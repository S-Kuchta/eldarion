package kuchtastefan.actions.actionsWIthDuration.specificActionWithDuration;

import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.ActionStatusEffect;
import kuchtastefan.actions.actionValue.ActionValueRange;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.actions.actionValue.ActionWithIncreasedValueByPrimaryAbility;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.utility.ConsoleColor;

public class ActionDealDamageOverTime extends ActionWithDuration implements ActionWithIncreasedValueByPrimaryAbility {
    public ActionDealDamageOverTime(ActionName actionName, ActionEffectOn actionEffectOn, int baseActionValue, int maxActionTurns,
                                    int actionMaxStacks, int chanceToPerformAction, ActionStatusEffect actionStatusEffect) {
        super(actionName, actionEffectOn, baseActionValue, maxActionTurns, actionMaxStacks, chanceToPerformAction, actionStatusEffect);
    }

    @Override
    public void performAction() {
        this.currentActionValue = this.charactersInvolvedInBattle.getSpellTarget().returnDamageAfterResistDamage(
                this.returnFinalValue(charactersInvolvedInBattle.getSpellCaster()) * this.getActionCurrentStacks());

        this.charactersInvolvedInBattle.getSpellTarget().receiveDamage(this.currentActionValue);
    }

    @Override
    public void printActionPerforming() {
        System.out.println("\t" + charactersInvolvedInBattle.getSpellTarget().getName() + " take " + ConsoleColor.RED_BRIGHT + this.currentActionValue + ConsoleColor.RESET + " damage ");
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

    @Override
    public int returnPriorityPoints(GameCharacter spellCaster, GameCharacter spellTarget) {
        return 1;
    }
}
