package kuchtastefan.actions.instantActions;

import kuchtastefan.actions.Action;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.actionsWIthDuration.ActionStatusEffect;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.character.GameCharacter;
import lombok.Getter;

@Getter
public class ActionRemoveBuffOrDebuff extends Action {

    private final boolean removeAllStatusEffects;
    private final ActionStatusEffect actionStatusEffectToRemove;

    public ActionRemoveBuffOrDebuff(ActionName actionName, ActionEffectOn actionEffectOn, int maxActionValue,
                                    int chanceToPerformAction, boolean canBeActionCriticalHit,
                                    boolean removeAllStatusEffects, ActionStatusEffect actionStatusEffectToRemove) {

        super(actionName, actionEffectOn, maxActionValue, chanceToPerformAction, canBeActionCriticalHit);
        this.removeAllStatusEffects = removeAllStatusEffects;
        this.actionStatusEffectToRemove = actionStatusEffectToRemove;
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        if (this.removeAllStatusEffects) {
            gameCharacter.getBattleActionsWithDuration().removeIf(action -> action.getActionStatusEffect().equals(this.actionStatusEffectToRemove));
        } else {
            for (ActionWithDuration action : gameCharacter.getBattleActionsWithDuration()) {
                for (int i = 0; i < this.maxActionValue; i++) {
                    if (action.getActionStatusEffect().equals(this.actionStatusEffectToRemove)) {
                        gameCharacter.getBattleActionsWithDuration().remove(action);
                    }
                }
            }
        }
    }
}
