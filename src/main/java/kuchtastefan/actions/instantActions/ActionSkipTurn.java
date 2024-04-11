package kuchtastefan.actions.instantActions;

import kuchtastefan.actions.Action;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.actionsWIthDuration.actionMarkerInterface.ActionWithoutValue;
import kuchtastefan.character.GameCharacter;

public class ActionSkipTurn extends Action implements ActionWithoutValue {
    public ActionSkipTurn(ActionName actionName, ActionEffectOn actionEffectOn, int maxActionValue,
                          int chanceToPerformAction, boolean canBeActionCriticalHit) {

        super(actionName, actionEffectOn, maxActionValue, chanceToPerformAction, canBeActionCriticalHit, 0);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {

    }

    @Override
    public void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget) {
        System.out.print("Skip turn");
    }
}
