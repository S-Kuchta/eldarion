package kuchtastefan.actions.instantActions;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.Action;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.characters.GameCharacter;

public class ActionRestoreHealth extends Action {

    public ActionRestoreHealth(String actionName, ActionEffectOn actionEffectOn,
                               int maxActionValue, int chanceToPerformAction, boolean canBeActionCriticalHit) {
        super(actionName, actionEffectOn, maxActionValue, chanceToPerformAction, canBeActionCriticalHit);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        gameCharacter.restoreAbility(this.getCurrentActionValue(), Ability.HEALTH);
    }
}
