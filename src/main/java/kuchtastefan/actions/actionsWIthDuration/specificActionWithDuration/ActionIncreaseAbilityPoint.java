package kuchtastefan.actions.actionsWIthDuration.specificActionWithDuration;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.ActionStatusEffect;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.actions.actionValue.ActionWithBaseValue;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.utility.ConsoleColor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class ActionIncreaseAbilityPoint extends ActionWithDuration implements ActionWithBaseValue {

    private final Ability ability;


    public ActionIncreaseAbilityPoint(ActionName actionName, ActionEffectOn actionEffectOn, int baseActionValue, int maxActionTurns,
                                      int actionMaxStacks, Ability ability, int chanceToPerformAction, ActionStatusEffect actionStatusEffect) {
        super(actionName, actionEffectOn, baseActionValue, maxActionTurns, actionMaxStacks, chanceToPerformAction, actionStatusEffect);
        this.ability = ability;
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {

        int increaseAbilityWithStacksValue = (this.returnActionValueRange(gameCharacter).getOnlyValue() * this.getActionCurrentStacks());
        gameCharacter.increaseEffectiveAbilityValue(this.ability, increaseAbilityWithStacksValue);

        System.out.println("\t" + ConsoleColor.YELLOW + this.ability + ConsoleColor.RESET + " is increased by " + increaseAbilityWithStacksValue);
    }

    @Override
    public void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget) {
        System.out.print("Increase " + this.returnTargetName(spellCaster, spellTarget) + " " + this.ability.toString()
                + " by " + ConsoleColor.GREEN + this.returnActionValueRange(spellCaster).getOnlyValue() + ConsoleColor.RESET);
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
        ActionIncreaseAbilityPoint that = (ActionIncreaseAbilityPoint) o;
        return ability == that.ability;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ability);
    }
}
