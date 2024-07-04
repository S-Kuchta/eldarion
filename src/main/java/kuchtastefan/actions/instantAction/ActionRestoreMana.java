package kuchtastefan.actions.instantAction;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.Action;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.actionValue.ActionWithIncreasedValueByPrimaryAbility;
import kuchtastefan.actions.criticalHit.CanBeCriticalHit;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.utility.ConsoleColor;


public class ActionRestoreMana extends Action implements ActionWithIncreasedValueByPrimaryAbility, CanBeCriticalHit {

    public ActionRestoreMana(ActionName actionName, ActionEffectOn actionEffectOn, int baseActionValue, int chanceToPerformAction) {
        super(actionName, actionEffectOn, baseActionValue, chanceToPerformAction);
    }

    @Override
    public void performAction() {
        charactersInvolvedInBattle.getSpellTarget().restoreAbilityValue(this.currentActionValue, Ability.MANA);
    }

    @Override
    public void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget) {
        System.out.print("Restore "
                + ConsoleColor.BLUE + this.returnActionValueRange(spellCaster).minimumValue() + ConsoleColor.RESET
                + " - " + ConsoleColor.BLUE + this.returnActionValueRange(spellCaster).maximumValue() + ConsoleColor.RESET
                + " of " + ConsoleColor.YELLOW + returnTargetName(spellCaster, spellTarget) + ConsoleColor.RESET
                + " Mana");
    }

    @Override
    public int returnPriorityPoints(GameCharacter spellCaster, GameCharacter spellTarget) {
        if (spellCaster.getEffectiveAbilityValue(Ability.MANA) < (spellCaster.getEnhancedAbilities().get(Ability.MANA) * 0.3)) {
            return 3;
        } else {
            return 2;
        }
    }
}
