package kuchtastefan.actions.actionsWIthDuration;

import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.character.GameCharacter;

public class ActionReflectSpell extends ActionWithDuration {
    public ActionReflectSpell(ActionName actionName, ActionEffectOn actionEffectOn, int maxActionValue,
                              int maxActionTurns, int actionMaxStacks, ActionDurationType actionDurationType,
                              int chanceToPerformAction, boolean canBeActionCriticalHit, ActionStatusEffect actionStatusEffect) {
        super(actionName, actionEffectOn, maxActionValue, maxActionTurns, actionMaxStacks,
                actionDurationType, chanceToPerformAction, canBeActionCriticalHit, actionStatusEffect);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        gameCharacter.setReflectSpell(true);
    }
}
