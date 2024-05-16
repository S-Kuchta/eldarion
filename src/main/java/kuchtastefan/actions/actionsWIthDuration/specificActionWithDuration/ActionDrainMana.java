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
        this.currentActionValue = this.returnFinalValue(charactersInvolvedInBattle.getSpellCaster()) * this.getActionCurrentStacks();

        if (charactersInvolvedInBattle.getSpellTarget().getEffectiveAbilityValue(Ability.MANA) <= this.currentActionValue) {
            this.currentActionValue = charactersInvolvedInBattle.getSpellTarget().getEffectiveAbilityValue(Ability.MANA);
        }

        charactersInvolvedInBattle.getSpellTarget().decreaseEffectiveAbilityValue(this.currentActionValue, Ability.MANA);
        charactersInvolvedInBattle.getSpellCaster().increaseEffectiveAbilityValue(this.currentActionValue, Ability.MANA);
    }

    @Override
    public void printActionPerforming() {
        System.out.println("\t" + this.charactersInvolvedInBattle.getSpellCaster().getName() + " drains "
                + ConsoleColor.BLUE + this.currentActionValue + ConsoleColor.RESET + " mana from " + this.charactersInvolvedInBattle.getSpellTarget().getName());
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
