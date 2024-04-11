package kuchtastefan.actions.instantAction;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.Action;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.actionsWIthDuration.actionMarkerInterface.ActionWithIncreasedValueByAbility;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.utility.ConsoleColor;

public class ActionRestoreHealth extends Action implements ActionWithIncreasedValueByAbility {

    public ActionRestoreHealth(ActionName actionName, ActionEffectOn actionEffectOn,
                               int maxActionValue, int chanceToPerformAction, boolean canBeActionCriticalHit) {
        super(actionName, actionEffectOn, maxActionValue, chanceToPerformAction, canBeActionCriticalHit);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        gameCharacter.restoreAbilityValue(this.getCurrentActionValue(), Ability.HEALTH);
    }

    @Override
    public void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget) {
        System.out.print("Restore "
                + ConsoleColor.RED + this.returnActionValueRange(spellCaster).minimumValue() + ConsoleColor.RESET
                +  " - " + ConsoleColor.RED + this.returnActionValueRange(spellCaster).maximumValue() + ConsoleColor.RESET
                + " of " + ConsoleColor.YELLOW + returnTargetName(spellCaster, spellTarget) + ConsoleColor.RESET
                + " Health");
    }

    @Override
    public int returnPriorityPoints(GameCharacter spellCaster, GameCharacter spellTarget) {
        if (spellCaster.getCurrentAbilityValue(Ability.HEALTH) < spellCaster.getMaxAbilities().get(Ability.HEALTH) / 2) {
            return 2;
        } else if (spellCaster.getCurrentAbilityValue(Ability.HEALTH) < spellCaster.getMaxAbilities().get(Ability.HEALTH) / 3) {
            return 4;
        } else {
            return 1;
        }
    }
}
