package kuchtastefan.actions.actionsWIthDuration.specificActionWithDuration;

import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.ActionStatusEffect;
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
    public void performAction(GameCharacter spellCaster, GameCharacter spellTarget) {
//        int randomValueFromRange = RandomNumberGenerator.getRandomNumber(this.returnActionValueRange(gameCharacter).minimumValue(),
//                this.returnActionValueRange(gameCharacter).maximumValue());

        int damageWithStacks = spellTarget.receiveDamage(this.returnFinalValue(spellCaster) * this.getActionCurrentStacks());
//        int damageWithStacks = gameCharacter.receiveDamage(this.currentActionValue * this.getActionCurrentStacks());
        System.out.println("\t" + spellTarget.getName() + " take " + ConsoleColor.RED_BRIGHT + damageWithStacks + ConsoleColor.RESET + " damage ");
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
