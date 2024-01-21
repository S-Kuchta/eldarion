package kuchtastefan.actions.actionsWIthDuration;

import kuchtastefan.ability.Ability;
import kuchtastefan.characters.GameCharacter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionIncreaseAbilityPoint extends ActionWithDuration {

    private final Ability ability;


    public ActionIncreaseAbilityPoint(String actionName, int actionValue, int maxActionTurns, ActionDurationType actionDurationType, int actionMaxStacks, Ability ability) {
        super(actionName, actionValue, maxActionTurns, actionDurationType, actionMaxStacks);
        this.ability = ability;
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {

        int increaseAbilityWithStacks = this.getActionValue() * this.getActionCurrentStacks();
        gameCharacter.getCurrentAbilities().put(this.ability,
                gameCharacter.getCurrentAbilityValue(this.ability)
                        + (increaseAbilityWithStacks));

        System.out.println("\tAbility " + this.ability + " was increase by " + increaseAbilityWithStacks);
    }

}
