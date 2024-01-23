package kuchtastefan.actions.actionsWIthDuration;

import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.characters.GameCharacter;

public class ActionRestoreHealthOverTime extends ActionWithDuration {


    public ActionRestoreHealthOverTime(String actionName, ActionEffectOn actionEffectOn,
                                       int maxActionValue, int maxActionTurns, int actionMaxStacks,
                                       ActionDurationType actionDurationType, int chanceToPerformAction) {
        super(actionName, actionEffectOn, maxActionValue, maxActionTurns,
                actionMaxStacks, actionDurationType, chanceToPerformAction);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        gameCharacter.restoreHealth(getCurrentActionValue());
    }

}
