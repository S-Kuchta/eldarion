package kuchtastefan.actions.actionsWithDuration.specificActionWithDuration;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.ActionStatusEffect;
import kuchtastefan.actions.actionValue.ActionWithBaseValue;
import kuchtastefan.actions.actionsWithDuration.ActionWithAffectingAbility;
import kuchtastefan.actions.actionsWithDuration.ActionWithDurationPerformedOnce;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.utility.ConsoleColor;
import lombok.Getter;

import java.util.Objects;

@Getter
public class ActionDecreaseAbilityPoint extends ActionWithDurationPerformedOnce implements ActionWithBaseValue, ActionWithAffectingAbility {

    private final Ability ability;


    public ActionDecreaseAbilityPoint(ActionName actionName, ActionEffectOn actionEffectOn, int maxActionValue, int maxActionTurns,
                                      int actionMaxStacks, int chanceToPerformAction, ActionStatusEffect actionStatusEffect, Ability ability) {
        super(actionName, actionEffectOn, maxActionValue, maxActionTurns, actionMaxStacks, chanceToPerformAction, actionStatusEffect);
        this.ability = ability;
    }

    @Override
    public void performAction() {
        this.currentActionValue = this.returnFinalValue(this.charactersInvolvedInBattle.getSpellCaster()) * this.getActionCurrentStacks();
        charactersInvolvedInBattle.getSpellTarget().decreaseEffectiveAbilityValue(this.currentActionValue, this.ability);
    }

    @Override
    public void printActionPerforming() {
        System.out.println("\t" + ConsoleColor.RED + this.ability + ConsoleColor.RESET + " is decreased by " + this.currentActionValue);
    }

    @Override
    public void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget) {
        System.out.print("Decrease " + this.returnTargetName(spellCaster, spellTarget) + " " + this.ability.toString() + " by " + this.returnActionValueRange(spellCaster).getOnlyValue());
    }

    @Override
    public void printActiveAction() {
        System.out.println("\t" + this.charactersInvolvedInBattle.getSpellTarget() + " " + this.ability + " is decreased by " + this.currentActionValue);
    }

    @Override
    public int returnPriorityPoints(GameCharacter spellCaster, GameCharacter spellTarget) {
        return 2;
    }

    @Override
    public void returnToDefaultValues() {
        charactersInvolvedInBattle.getSpellTarget().increaseEffectiveAbilityValue(this.currentActionValue, this.ability);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActionDecreaseAbilityPoint that = (ActionDecreaseAbilityPoint) o;
        return ability == that.ability;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ability);
    }
}
