package kuchtastefan.actions.instantActions;

import kuchtastefan.actions.Action;
import kuchtastefan.characters.GameCharacter;

public class ActionRestoreHealth extends Action {

    public ActionRestoreHealth(String actionName, int actionValue) {
        super(actionName, actionValue);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        gameCharacter.restoreHealth(this.getActionValue());
    }
}
