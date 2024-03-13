package kuchtastefan.actions.actionsWIthDuration;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.utility.ConsoleColor;
import lombok.Getter;

import java.util.Objects;

@Getter
public class ActionDecreaseAbilityPoint extends ActionWithDuration {

    private final Ability ability;

    public ActionDecreaseAbilityPoint(ActionName actionName, ActionEffectOn actionEffectOn, int maxActionValue,
                                      int maxActionTurns, int actionMaxStacks, ActionDurationType actionDurationType,
                                      int chanceToPerformAction, boolean canBeActionCriticalHit, Ability ability,
                                      ActionStatusEffect actionStatusEffect) {
        super(actionName, actionEffectOn, maxActionValue, maxActionTurns, actionMaxStacks,
                actionDurationType, chanceToPerformAction, canBeActionCriticalHit, actionStatusEffect);
        this.ability = ability;
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {

        int decreaseAbilityWithStacksValue = this.getCurrentActionValue() * this.getActionCurrentStacks();
        gameCharacter.decreaseCurrentAbility(decreaseAbilityWithStacksValue, this.ability);

        System.out.println("\t" + ConsoleColor.RED + this.ability + ConsoleColor.RESET + " is decreased by " + decreaseAbilityWithStacksValue);
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
