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
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class ActionIncreaseAbilityPoint extends ActionWithDurationPerformedOnce implements ActionWithBaseValue, ActionWithAffectingAbility {

    private final Ability ability;

    public ActionIncreaseAbilityPoint(ActionName actionName, ActionEffectOn actionEffectOn, int maxActionValue, int maxActionTurns,
                                      int actionMaxStacks, int chanceToPerformAction, ActionStatusEffect actionStatusEffect, Ability ability) {
        super(actionName, actionEffectOn, maxActionValue, maxActionTurns, actionMaxStacks, chanceToPerformAction, actionStatusEffect);
        this.ability = ability;
    }


    @Override
    public void performAction() {
        this.currentActionValue = this.returnFinalValue(this.charactersInvolvedInBattle.getSpellCaster()) * this.getActionCurrentStacks();
        charactersInvolvedInBattle.getSpellTarget().increaseEffectiveAbilityValue(this.currentActionValue, this.ability);
    }

    @Override
    public void printActionPerforming() {
        System.out.println("\t" + ConsoleColor.YELLOW + this.ability + ConsoleColor.RESET + " is increased by " + this.currentActionValue);
    }

    @Override
    public void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget) {
        System.out.print("Increase " + this.returnTargetName(spellCaster, spellTarget) + " " + this.ability.toString()
                + " by " + ConsoleColor.GREEN + this.returnActionValueRange(spellCaster).getOnlyValue() + ConsoleColor.RESET
                + " for " + this.getMaxActionTurns() + " turns");
    }

    @Override
    public void printActiveAction() {
        System.out.println("\t" + this.charactersInvolvedInBattle.getSpellTarget() + " " + this.ability + " is increased by " + this.currentActionValue);
    }

    @Override
    public int returnPriorityPoints(GameCharacter spellCaster, GameCharacter spellTarget) {
        return 2;
    }

    @Override
    public void returnToDefaultValues() {
        this.charactersInvolvedInBattle.getSpellTarget().decreaseEffectiveAbilityValue(this.currentActionValue, this.ability);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ActionIncreaseAbilityPoint that = (ActionIncreaseAbilityPoint) o;
        return ability == that.ability;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ability);
    }
}
