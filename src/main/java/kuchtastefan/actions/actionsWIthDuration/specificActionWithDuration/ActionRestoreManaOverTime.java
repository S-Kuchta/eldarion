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

public class ActionRestoreManaOverTime extends ActionWithDuration implements ActionWithIncreasedValueByAbility {

    public ActionRestoreManaOverTime(ActionName actionName, ActionEffectOn actionEffectOn,
                                     int maxActionValue, int maxActionTurns, int actionMaxStacks,
                                     ActionDurationType actionDurationType, int chanceToPerformAction,
                                     boolean canBeActionCriticalHit, ActionStatusEffect actionStatusEffect) {

        super(actionName, actionEffectOn, maxActionValue, maxActionTurns, actionMaxStacks,
                actionDurationType, chanceToPerformAction, canBeActionCriticalHit, actionStatusEffect, 2);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        gameCharacter.restoreAbility(this.currentActionValue, Ability.MANA);
    }

    @Override
    public void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget) {
            System.out.print("Restore " + ConsoleColor.RED
                    + (this.returnActionValueRange(spellCaster).minimumValue() * this.getMaxActionTurns())
                    + ConsoleColor.RESET + " - " + ConsoleColor.RED
                    + (this.returnActionValueRange(spellCaster).maximumValue() * this.getMaxActionTurns())
                    + ConsoleColor.RESET
                    + " Mana " + " over " + this.getMaxActionTurns() + " turns on "
                    + ConsoleColor.YELLOW + this.returnTargetName(spellCaster, spellTarget) + ConsoleColor.RESET);
    }

    @Override
    public int returnPriorityPoints(GameCharacter spellCaster, GameCharacter spellTarget) {
        if (spellCaster.getCurrentAbilityValue(Ability.MANA) < (spellCaster.getMaxAbilities().get(Ability.MANA) * 0.3)) {
            return 3;
        } else {
            return this.priorityPoints;
        }
    }
}
