package kuchtastefan.actions.actionsWIthDuration;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.characters.GameCharacter;

public class ActionAbsorbDamage extends ActionWithDuration {

    public ActionAbsorbDamage(String actionName, ActionEffectOn actionEffectOn, int maxActionValue,
                              int maxActionTurns, int actionMaxStacks,
                              ActionDurationType actionDurationType, int chanceToPerformAction) {
        super(actionName, actionEffectOn, maxActionValue, maxActionTurns,
                actionMaxStacks, actionDurationType, chanceToPerformAction);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        if (this.getCurrentActionTurn() == 0) {
            int increaseAbilityWithStacks = this.getCurrentActionValue() * this.getActionCurrentStacks();
            gameCharacter.getCurrentAbilities().put(Ability.ABSORB_DAMAGE,
                    gameCharacter.getCurrentAbilityValue(Ability.ABSORB_DAMAGE) + increaseAbilityWithStacks);
        }/* else {
            gameCharacter.getCurrentAbilities().put(Ability.ABSORB_DAMAGE, gameCharacter.getCurrentAbilityValue(Ability.ABSORB_DAMAGE));
        }*/
    }
}
