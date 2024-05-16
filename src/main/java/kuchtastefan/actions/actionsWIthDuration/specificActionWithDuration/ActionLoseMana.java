package kuchtastefan.actions.actionsWIthDuration.specificActionWithDuration;

import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.ActionStatusEffect;
import kuchtastefan.actions.actionValue.ActionValueRange;
import kuchtastefan.actions.actionValue.ActionWithBaseValue;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.character.GameCharacter;

public class ActionLoseMana extends ActionWithDuration implements ActionWithBaseValue {

    public ActionLoseMana(ActionName actionName, ActionEffectOn actionEffectOn, int maxActionValue, int maxActionTurns,
                          int actionMaxStacks, int chanceToPerformAction, ActionStatusEffect actionStatusEffect) {

        super(actionName, actionEffectOn, maxActionValue, maxActionTurns, actionMaxStacks, chanceToPerformAction, actionStatusEffect);
    }


    @Override
    public void performAction() {

    }

    @Override
    public void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget) {

    }

    @Override
    public int returnPriorityPoints(GameCharacter spellCaster, GameCharacter spellTarget) {
        return 0;
    }

    @Override
    public ActionValueRange actionValue(GameCharacter spellCaster, int valueIncreasedByLevel) {
        return null;
    }
}
