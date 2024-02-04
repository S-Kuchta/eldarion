package kuchtastefan.actions.actionsWIthDuration;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.characters.GameCharacter;
import kuchtastefan.utility.ConsoleColor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionIncreaseAbilityPoint extends ActionWithDuration {

    private final Ability ability;


    public ActionIncreaseAbilityPoint(ActionName actionName, ActionEffectOn actionEffectOn, int maxActionValue,
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

        System.out.println("\t" + ConsoleColor.YELLOW + this.ability + ConsoleColor.RESET + " is increased by " + increaseAbilityWithStacks);
    }

}
