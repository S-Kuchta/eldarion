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

public class ActionRestoreHealthOverTime extends ActionWithDuration implements ActionWithIncreasedValueByAbility {

    public ActionRestoreHealthOverTime(ActionName actionName, ActionEffectOn actionEffectOn,
                                       int maxActionValue, int maxActionTurns, int actionMaxStacks,
                                       ActionDurationType actionDurationType, int chanceToPerformAction,
                                       boolean canBeActionCriticalHit, ActionStatusEffect actionStatusEffect) {

        super(actionName, actionEffectOn, maxActionValue, maxActionTurns, actionMaxStacks,
                actionDurationType, chanceToPerformAction, canBeActionCriticalHit, actionStatusEffect, 2);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        gameCharacter.restoreAbility(getCurrentActionValue(), Ability.HEALTH);
    }

    @Override
    public void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget) {
        System.out.print("Restore " + ConsoleColor.RED
                + this.returnActionValueRange(spellCaster).minimumValue()
                + ConsoleColor.RESET + " - " + ConsoleColor.RED
                + this.returnActionValueRange(spellCaster).maximumValue()
                + ConsoleColor.RESET
                + " Health " + " over " + this.getMaxActionTurns() + " turns on "
                + ConsoleColor.YELLOW + this.returnTargetName(spellCaster, spellTarget) + ConsoleColor.RESET);
    }

    @Override
    public int returnPriorityPoints(GameCharacter spellCaster, GameCharacter spellTarget) {
        if (spellCaster.getCurrentAbilityValue(Ability.HEALTH) < spellCaster.getMaxAbilities().get(Ability.HEALTH) / 2) {
            return 2;
        } else if (spellCaster.getCurrentAbilityValue(Ability.HEALTH) < spellCaster.getMaxAbilities().get(Ability.HEALTH) / 3) {
            return 4;
        }

        return this.priorityPoints;
    }

}
