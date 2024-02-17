package kuchtastefan.actions.actionsWIthDuration;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.characters.GameCharacter;
import lombok.Getter;

@Getter
public class ActionDecreaseAbilityPoint extends ActionWithDuration {

    private final Ability ability;

    public ActionDecreaseAbilityPoint(ActionName actionName, ActionEffectOn actionEffectOn, int maxActionValue,
                                      int maxActionTurns, int actionMaxStacks, ActionDurationType actionDurationType,
                                      int chanceToPerformAction, boolean canBeActionCriticalHit, Ability ability) {
        super(actionName, actionEffectOn, maxActionValue, maxActionTurns, actionMaxStacks,
                actionDurationType, chanceToPerformAction, canBeActionCriticalHit);
        this.ability = ability;
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        gameCharacter.decreaseCurrentAbility(this.currentActionValue, this.ability);
    }
}
