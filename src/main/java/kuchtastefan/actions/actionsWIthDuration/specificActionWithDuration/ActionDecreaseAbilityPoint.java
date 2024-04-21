package kuchtastefan.actions.actionsWIthDuration.specificActionWithDuration;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.ActionStatusEffect;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.actions.actionsWIthDuration.actionMarkerInterface.actionValue.ActionWithBaseValue;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.utility.ConsoleColor;
import lombok.Getter;

import java.util.Objects;

@Getter
public class ActionDecreaseAbilityPoint extends ActionWithDuration implements ActionWithBaseValue {

    private final Ability ability;

    public ActionDecreaseAbilityPoint(ActionName actionName, ActionEffectOn actionEffectOn, int baseActionValue, int maxActionTurns,
                                      int actionMaxStacks, int chanceToPerformAction, Ability ability, ActionStatusEffect actionStatusEffect) {
        super(actionName, actionEffectOn, baseActionValue, maxActionTurns, actionMaxStacks, chanceToPerformAction, actionStatusEffect);
        this.ability = ability;
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {

        int decreaseAbilityWithStacksValue = this.getCurrentActionValue() * this.getActionCurrentStacks();
        gameCharacter.decreaseEffectiveAbilityValue(decreaseAbilityWithStacksValue, this.ability);

        System.out.println("\t" + ConsoleColor.RED + this.ability + ConsoleColor.RESET + " is decreased by " + decreaseAbilityWithStacksValue);
    }

    @Override
    public void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget) {
        System.out.print("Decrease " + this.returnTargetName(spellCaster, spellTarget) + " " + this.ability.toString() + " by " + this.returnActionValueRange(spellCaster).onlyValue());
    }

    @Override
    public int returnPriorityPoints(GameCharacter spellCaster, GameCharacter spellTarget) {
        return 2;
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
