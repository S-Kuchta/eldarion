package kuchtastefan.actions.instantActions;

import kuchtastefan.actions.Action;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.character.GameCharacter;

public class ActionSkipTurn extends Action {
    public ActionSkipTurn(ActionName actionName, ActionEffectOn actionEffectOn, int maxActionValue,
                          int chanceToPerformAction, boolean canBeActionCriticalHit) {

        super(actionName, actionEffectOn, maxActionValue, chanceToPerformAction, canBeActionCriticalHit);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {

    }
}
