package kuchtastefan.actions.instantAction;

import kuchtastefan.actions.Action;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.ActionStatusEffect;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.actions.actionsWIthDuration.actionMarkerInterface.ActionWithBaseValue;
import kuchtastefan.character.GameCharacter;
import lombok.Getter;

@Getter
public class ActionRemoveBuffOrDebuff extends Action implements ActionWithBaseValue {

    private final boolean removeAllStatusEffects;
    private final ActionStatusEffect actionStatusEffectToRemove;

    public ActionRemoveBuffOrDebuff(ActionName actionName, ActionEffectOn actionEffectOn, int maxActionValue,
                                    int chanceToPerformAction, boolean canBeActionCriticalHit,
                                    boolean removeAllStatusEffects, ActionStatusEffect actionStatusEffectToRemove) {

        super(actionName, actionEffectOn, maxActionValue, chanceToPerformAction, canBeActionCriticalHit, 0);
        this.removeAllStatusEffects = removeAllStatusEffects;
        this.actionStatusEffectToRemove = actionStatusEffectToRemove;
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        if (this.removeAllStatusEffects) {
            gameCharacter.getBattleActionsWithDuration().removeIf(action -> action.getActionStatusEffect().equals(this.actionStatusEffectToRemove));
        } else {
            for (ActionWithDuration action : gameCharacter.getBattleActionsWithDuration()) {
                for (int i = 0; i < this.baseActionValue; i++) {
                    if (action.getActionStatusEffect().equals(this.actionStatusEffectToRemove)) {
                        gameCharacter.getBattleActionsWithDuration().remove(action);
                    }
                }
            }
        }
    }

    @Override
    public void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget) {
        String countBuffRemove = removeAllStatusEffects ? "All" : "1";
        System.out.print("Remove " + countBuffRemove + " " + actionStatusEffectToRemove.toString() + " from " + this.returnTargetName(spellCaster, spellTarget));
    }

    @Override
    public int returnPriorityPoints(GameCharacter spellCaster, GameCharacter spellTarget) {
        for (ActionWithDuration actionWithDuration : spellCaster.getBattleActionsWithDuration()) {
            if (actionWithDuration.getActionStatusEffect().equals(ActionStatusEffect.DEBUFF)
                    && this.getActionStatusEffectToRemove().equals(ActionStatusEffect.DEBUFF)) {

                priorityPoints += 2;
            }
        }

        for (ActionWithDuration actionWithDuration : spellTarget.getBattleActionsWithDuration()) {
            if (actionWithDuration.getActionStatusEffect().equals(ActionStatusEffect.BUFF)
                    && this.getActionStatusEffectToRemove().equals(ActionStatusEffect.BUFF)) {

                priorityPoints += 2;
            }
        }

        return this.priorityPoints;
    }
}
