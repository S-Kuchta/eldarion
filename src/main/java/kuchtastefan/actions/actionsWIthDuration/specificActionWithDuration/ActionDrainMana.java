package kuchtastefan.actions.actionsWIthDuration.specificActionWithDuration;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.ActionStatusEffect;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.actions.actionsWIthDuration.actionMarkerInterface.actionValue.ActionWithIncreasedValueByPrimaryAbility;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.utility.ConsoleColor;

public class ActionDrainMana extends ActionWithDuration implements ActionWithIncreasedValueByPrimaryAbility {

    public ActionDrainMana(ActionName actionName, ActionEffectOn actionEffectOn, int baseActionValue, int maxActionTurns,
                           int actionMaxStacks, int chanceToPerformAction, ActionStatusEffect actionStatusEffect) {
        super(actionName, actionEffectOn, baseActionValue, maxActionTurns, actionMaxStacks, chanceToPerformAction, actionStatusEffect);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        int value = this.currentActionValue;
        if (gameCharacter.getEffectiveAbilityValue(Ability.MANA) < this.currentActionValue) {
            value = gameCharacter.getEffectiveAbilityValue(Ability.MANA);
        }

        System.out.println("\t" + gameCharacter.getName() + " lost " + value + " Mana");
        gameCharacter.decreaseEffectiveAbilityValue(this.currentActionValue, Ability.MANA);
    }

    @Override
    public void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget) {
        System.out.print("Drain " + ConsoleColor.BLUE
                + this.returnActionValueRange(spellCaster).minimumValue()
                + ConsoleColor.RESET + " - " + ConsoleColor.BLUE
                + this.returnActionValueRange(spellCaster).maximumValue()
                + ConsoleColor.RESET
                + " Mana over " + this.getMaxActionTurns() + " from "
                + ConsoleColor.YELLOW + this.returnTargetName(spellCaster, spellTarget) + ConsoleColor.RESET);
    }

    @Override
    public int returnPriorityPoints(GameCharacter spellCaster, GameCharacter spellTarget) {
        return 3;
    }
}
