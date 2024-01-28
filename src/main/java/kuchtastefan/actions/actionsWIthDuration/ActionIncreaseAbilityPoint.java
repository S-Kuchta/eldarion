package kuchtastefan.actions.actionsWIthDuration;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.characters.GameCharacter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionIncreaseAbilityPoint extends ActionWithDuration {

    private final Ability ability;


    public ActionIncreaseAbilityPoint(String actionName, ActionEffectOn actionEffectOn, int maxActionValue,
                                      int maxActionTurns, int actionMaxStacks, ActionDurationType actionDurationType,
                                      Ability ability, int chanceToPerformAction, boolean canBeActionCriticalHit) {
        super(actionName, actionEffectOn, maxActionValue, maxActionTurns,
                actionMaxStacks, actionDurationType, chanceToPerformAction, canBeActionCriticalHit);
        this.ability = ability;
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {

        int increaseAbilityWithStacks = this.getCurrentActionValue() * this.getActionCurrentStacks();
        gameCharacter.getCurrentAbilities().put(this.ability,
                gameCharacter.getCurrentAbilityValue(this.ability)
                        + (increaseAbilityWithStacks));

//        System.out.println("\tAbility " + this.ability + " was increase by " + increaseAbilityWithStacks);
    }

}
