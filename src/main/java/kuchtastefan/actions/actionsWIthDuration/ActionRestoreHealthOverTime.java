package kuchtastefan.actions.actionsWIthDuration;

import kuchtastefan.characters.GameCharacter;

public class ActionRestoreHealthOverTime extends ActionWithDuration {

    public ActionRestoreHealthOverTime(String actionName, int actionValue, int maxActionTurns, ActionDurationType actionDurationType, int actionMaxStacks) {
        super(actionName, actionValue, maxActionTurns, actionDurationType, actionMaxStacks);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        gameCharacter.restoreHealth(getActionValue());
    }

}
