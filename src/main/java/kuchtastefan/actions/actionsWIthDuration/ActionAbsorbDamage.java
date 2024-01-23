package kuchtastefan.actions.actionsWIthDuration;

import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.characters.GameCharacter;

public class ActionAbsorbDamage extends ActionWithDuration {

    public ActionAbsorbDamage(String actionName, ActionEffectOn actionEffectOn, int maxActionValue,
                              int maxActionTurns, int actionMaxStacks, ActionDurationType actionDurationType) {
        super(actionName, actionEffectOn, maxActionValue, maxActionTurns, actionMaxStacks, actionDurationType);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        // TODO absorb damage
    }
}
