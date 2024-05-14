package kuchtastefan.actions.instantAction;

import kuchtastefan.actions.Action;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.actionValue.ActionWithoutValue;
import kuchtastefan.character.GameCharacter;

public class ActionSkipTurn extends Action implements ActionWithoutValue {
    public ActionSkipTurn(ActionName actionName, ActionEffectOn actionEffectOn, int maxActionValue, int chanceToPerformAction) {
        super(actionName, actionEffectOn, maxActionValue, chanceToPerformAction);
    }

    @Override
    public void performAction(GameCharacter spellCaster, GameCharacter spellTarget) {

    }

    @Override
    public void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget) {
        System.out.print("Skip turn");
    }

    @Override
    public int returnPriorityPoints(GameCharacter spellCaster, GameCharacter spellTarget) {
        return 0;
    }
}
