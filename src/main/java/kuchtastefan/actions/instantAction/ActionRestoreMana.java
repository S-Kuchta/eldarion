package kuchtastefan.actions.instantAction;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.Action;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.actionsWIthDuration.actionMarkerInterface.ActionWithIncreasedValueByAbility;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.utility.ConsoleColor;


public class ActionRestoreMana extends Action implements ActionWithIncreasedValueByAbility {
    public ActionRestoreMana(ActionName actionName, ActionEffectOn actionEffectOn,
                             int maxActionValue, int chanceToPerformAction, boolean canBeActionCriticalHit) {
        super(actionName, actionEffectOn, maxActionValue, chanceToPerformAction, canBeActionCriticalHit);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        gameCharacter.restoreAbilityValue(this.currentActionValue, Ability.MANA);
    }

    @Override
    public void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget) {
        System.out.print("Restore "
                + ConsoleColor.BLUE + this.returnActionValueRange(spellCaster).minimumValue() + ConsoleColor.RESET
                +  " - " + ConsoleColor.BLUE + this.returnActionValueRange(spellCaster).maximumValue() + ConsoleColor.RESET
                + returnTargetName(spellCaster, spellTarget)
                + " Mana");
    }

    @Override
    public int returnPriorityPoints(GameCharacter spellCaster, GameCharacter spellTarget) {
        if (spellCaster.getCurrentAbilityValue(Ability.MANA) < (spellCaster.getMaxAbilities().get(Ability.MANA) * 0.3)) {
            return 3;
        } else {
            return 2;
        }
    }
}
