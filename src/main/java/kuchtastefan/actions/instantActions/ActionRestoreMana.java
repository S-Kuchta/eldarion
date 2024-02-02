package kuchtastefan.actions.instantActions;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.Action;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.characters.GameCharacter;


public class ActionRestoreMana extends Action {
    public ActionRestoreMana(ActionName actionName, ActionEffectOn actionEffectOn,
                             int maxActionValue, int chanceToPerformAction, boolean canBeActionCriticalHit) {
        super(actionName, actionEffectOn, maxActionValue, chanceToPerformAction, canBeActionCriticalHit);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        gameCharacter.restoreAbility(this.currentActionValue, Ability.MANA);
    }
}
