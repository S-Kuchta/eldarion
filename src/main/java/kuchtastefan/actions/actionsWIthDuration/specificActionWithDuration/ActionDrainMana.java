package kuchtastefan.actions.actionsWIthDuration.specificActionWithDuration;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.ActionStatusEffect;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.actions.actionValue.ActionWithIncreasedValueByPrimaryAbility;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.utility.ConsoleColor;

public class ActionDrainMana extends ActionWithDuration implements ActionWithIncreasedValueByPrimaryAbility {

    public ActionDrainMana(ActionName actionName, ActionEffectOn actionEffectOn, int baseActionValue, int maxActionTurns,
                           int actionMaxStacks, int chanceToPerformAction, ActionStatusEffect actionStatusEffect) {
        super(actionName, actionEffectOn, baseActionValue, maxActionTurns, actionMaxStacks, chanceToPerformAction, actionStatusEffect);
    }

    @Override
    public void performAction() {
        int drainManaValue = this.returnFinalValue(charactersInvolvedInBattle.getSpellCaster()) * this.getActionCurrentStacks();
        System.out.println(this.charactersInvolvedInBattle.getSpellCaster());
        System.out.println(this.charactersInvolvedInBattle.getSpellTarget());
        System.out.println(drainManaValue);

        if (charactersInvolvedInBattle.getSpellTarget().getEffectiveAbilityValue(Ability.MANA) <= drainManaValue) {
            drainManaValue = charactersInvolvedInBattle.getSpellTarget().getEffectiveAbilityValue(Ability.MANA);
        }

        System.out.println(drainManaValue);
        charactersInvolvedInBattle.getSpellTarget().decreaseEffectiveAbilityValue(drainManaValue, Ability.MANA);
        charactersInvolvedInBattle.getSpellCaster().increaseEffectiveAbilityValue(drainManaValue, Ability.MANA);
    }

    @Override
    public void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget) {
        System.out.print("Drain " + ConsoleColor.BLUE + this.returnActionValueRange(spellCaster).minimumValue() + ConsoleColor.RESET
                + " - " + ConsoleColor.BLUE + this.returnActionValueRange(spellCaster).maximumValue() + ConsoleColor.RESET
                + " Mana over " + this.getMaxActionTurns() + " from " + this.returnTargetName(spellCaster, spellTarget));
    }

    @Override
    public int returnPriorityPoints(GameCharacter spellCaster, GameCharacter spellTarget) {
        return 3;
    }
}
