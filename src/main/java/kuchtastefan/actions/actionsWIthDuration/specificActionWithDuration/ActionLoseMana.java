package kuchtastefan.actions.actionsWIthDuration.specificActionWithDuration;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.ActionStatusEffect;
import kuchtastefan.actions.actionValue.ActionWithBaseValue;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.utility.ConsoleColor;

public class ActionLoseMana extends ActionWithDuration implements ActionWithBaseValue {

    public ActionLoseMana(ActionName actionName, ActionEffectOn actionEffectOn, int maxActionValue, int maxActionTurns,
                          int actionMaxStacks, int chanceToPerformAction, ActionStatusEffect actionStatusEffect) {

        super(actionName, actionEffectOn, maxActionValue, maxActionTurns, actionMaxStacks, chanceToPerformAction, actionStatusEffect);
    }


    @Override
    public void performAction() {
        this.currentActionValue = this.returnFinalValue(charactersInvolvedInBattle.getSpellCaster()) * this.getActionCurrentStacks();

        if (charactersInvolvedInBattle.getSpellTarget().getEffectiveAbilityValue(Ability.MANA) < this.currentActionValue) {
            this.currentActionValue = charactersInvolvedInBattle.getSpellTarget().getEffectiveAbilityValue(Ability.MANA);
        }

        charactersInvolvedInBattle.getSpellTarget().decreaseEffectiveAbilityValue(this.currentActionValue, Ability.MANA);
    }

    @Override
    public void printActionPerforming() {
        System.out.println("\t" + this.charactersInvolvedInBattle.getSpellTarget().getName() + " loses "
                + ConsoleColor.BLUE + this.currentActionValue + ConsoleColor.RESET + " mana");
    }

    @Override
    public void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget) {
        System.out.print("Drain " + ConsoleColor.BLUE + this.returnActionValueRange(spellCaster).getOnlyValue() + ConsoleColor.RESET + " mana");
    }

    @Override
    public void printActiveAction() {
        System.out.println("\t" + this.charactersInvolvedInBattle.getSpellTarget().getName() + " loses "
                + ConsoleColor.BLUE + this.currentActionValue + ConsoleColor.RESET + " mana");
    }

    @Override
    public int returnPriorityPoints(GameCharacter spellCaster, GameCharacter spellTarget) {
        return 0;
    }
}
