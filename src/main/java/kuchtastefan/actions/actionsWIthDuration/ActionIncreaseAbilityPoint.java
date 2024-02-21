package kuchtastefan.actions.actionsWIthDuration;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.utility.ConsoleColor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionIncreaseAbilityPoint extends ActionWithDuration {

    private final Ability ability;


    public ActionIncreaseAbilityPoint(ActionName actionName, ActionEffectOn actionEffectOn, int maxActionValue,
                                      int maxActionTurns, int actionMaxStacks, ActionDurationType actionDurationType,
                                      Ability ability, int chanceToPerformAction, boolean canBeActionCriticalHit,
                                      ActionStatusEffect actionStatusEffect) {
        super(actionName, actionEffectOn, maxActionValue, maxActionTurns, actionMaxStacks,
                actionDurationType, chanceToPerformAction, canBeActionCriticalHit, actionStatusEffect);
        this.ability = ability;
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {

        int increaseAbilityWithStacksValue = this.getCurrentActionValue() * this.getActionCurrentStacks();
        gameCharacter.increaseCurrentAbilityValue(this.ability, increaseAbilityWithStacksValue);

        System.out.println("\t" + ConsoleColor.YELLOW + this.ability + ConsoleColor.RESET + " is increased by " + increaseAbilityWithStacksValue);
    }
}
