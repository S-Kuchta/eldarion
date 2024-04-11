package kuchtastefan.actions.actionsWIthDuration.specificActionWithDuration;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.actionsWIthDuration.ActionDurationType;
import kuchtastefan.actions.ActionStatusEffect;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.actions.actionsWIthDuration.actionMarkerInterface.ActionWithIncreasedValueByAbility;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.utility.ConsoleColor;

public class ActionDrainMana extends ActionWithDuration implements ActionWithIncreasedValueByAbility {

    public ActionDrainMana(ActionName actionName, ActionEffectOn actionEffectOn, int maxActionValue,
                           int maxActionTurns, int actionMaxStacks, ActionDurationType actionDurationType,
                           int chanceToPerformAction, boolean canBeActionCriticalHit,
                           ActionStatusEffect actionStatusEffect) {

        super(actionName, actionEffectOn, maxActionValue, maxActionTurns, actionMaxStacks, actionDurationType,
                chanceToPerformAction, canBeActionCriticalHit, actionStatusEffect, 3);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        int value = this.currentActionValue;
        if (gameCharacter.getCurrentAbilityValue(Ability.MANA) < this.currentActionValue) {
            value = gameCharacter.getCurrentAbilityValue(Ability.MANA);
        }

        System.out.println("\t" + gameCharacter.getName() + " lost " + value + " Mana");
        gameCharacter.decreaseCurrentAbilityValue(this.currentActionValue, Ability.MANA);
    }

    @Override
    public void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget) {
        System.out.print("Drain " + ConsoleColor.BLUE
                + this.returnActionValueRange(spellCaster).minimumValue()
                + ConsoleColor.RESET + " - " + ConsoleColor.BLUE
                + this.returnActionValueRange(spellCaster).maximumValue()
                + ConsoleColor.RESET
                + " Mana " + " over " + this.getMaxActionTurns() + " from "
                + ConsoleColor.YELLOW + this.returnTargetName(spellCaster, spellTarget) + ConsoleColor.RESET);
    }
}
