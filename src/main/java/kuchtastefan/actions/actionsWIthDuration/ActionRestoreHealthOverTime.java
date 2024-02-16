package kuchtastefan.actions.actionsWIthDuration;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.characters.GameCharacter;

public class ActionRestoreHealthOverTime extends ActionWithDuration {

    public ActionRestoreHealthOverTime(ActionName actionName, ActionEffectOn actionEffectOn,
                                       int maxActionValue, int maxActionTurns, int actionMaxStacks,
                                       ActionDurationType actionDurationType, int chanceToPerformAction,
                                       boolean canBeActionCriticalHit) {

        super(actionName, actionEffectOn, maxActionValue, maxActionTurns, actionMaxStacks,
                actionDurationType, chanceToPerformAction, canBeActionCriticalHit);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        gameCharacter.restoreAbility(getCurrentActionValue(), Ability.HEALTH);
    }

}
