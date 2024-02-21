package kuchtastefan.actions.actionsWIthDuration;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.character.GameCharacter;

public class ActionAbsorbDamage extends ActionWithDuration {

    public ActionAbsorbDamage(ActionName actionName, ActionEffectOn actionEffectOn, int maxActionValue,
                              int maxActionTurns, int actionMaxStacks,
                              ActionDurationType actionDurationType, int chanceToPerformAction,
                              boolean canBeActionCriticalHit, ActionStatusEffect actionStatusEffect) {
        super(actionName, actionEffectOn, maxActionValue, maxActionTurns, actionMaxStacks,
                actionDurationType, chanceToPerformAction, canBeActionCriticalHit, actionStatusEffect);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        int increaseAbilityWithStacks = this.getCurrentActionValue() * this.getActionCurrentStacks();
        gameCharacter.increaseCurrentAbilityValue(Ability.ABSORB_DAMAGE, increaseAbilityWithStacks);
    }
}
