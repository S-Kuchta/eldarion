package kuchtastefan.actions.actionsWithDuration;

import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.ActionStatusEffect;
import kuchtastefan.character.GameCharacter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ActionWithDurationPerformedOnce extends ActionWithDuration {

    protected boolean actionPerformed;

    public ActionWithDurationPerformedOnce(ActionName actionName, ActionEffectOn actionEffectOn, int maxActionValue, int maxActionTurns,
                                           int actionMaxStacks, int chanceToPerformAction, ActionStatusEffect actionStatusEffect) {
        super(actionName, actionEffectOn, maxActionValue, maxActionTurns, actionMaxStacks, chanceToPerformAction, actionStatusEffect);
        this.actionPerformed = false;
    }

    @Override
    public boolean checkIfActionReachMaxActionTurns() {
        if (super.checkIfActionReachMaxActionTurns()) {
            this.returnToDefaultValues();
            return true;
        }

        return false;
    }

    public abstract void returnToDefaultValues();

    @Override
    public abstract void printActionPerforming();

    @Override
    public abstract void printActiveAction();

    @Override
    public abstract void performAction();

    @Override
    public abstract void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget);

    @Override
    public abstract int returnPriorityPoints(GameCharacter spellCaster, GameCharacter spellTarget);
}
