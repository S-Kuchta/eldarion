package kuchtastefan.actions.actionsWIthDuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kuchtastefan.actions.Action;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.ActionStatusEffect;
import kuchtastefan.actions.actionsWIthDuration.specificActionWithDuration.ActionDealDamageOverTime;
import kuchtastefan.actions.actionsWIthDuration.specificActionWithDuration.ActionStun;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.spell.CharactersInvolvedInBattle;
import kuchtastefan.utility.RuntimeTypeAdapterFactoryUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public abstract class ActionWithDuration extends Action {

    private final int maxActionTurns;
    private int currentActionTurn;
    private final int actionMaxStacks;
    private int actionCurrentStacks;
    private final ActionStatusEffect actionStatusEffect;


    public ActionWithDuration(ActionName actionName, ActionEffectOn actionEffectOn, int maxActionValue, int maxActionTurns,
                              int actionMaxStacks, int chanceToPerformAction, ActionStatusEffect actionStatusEffect) {
        super(actionName, actionEffectOn, maxActionValue, chanceToPerformAction);
        this.maxActionTurns = maxActionTurns;
        this.actionStatusEffect = actionStatusEffect;
        this.currentActionTurn = 0;
        this.actionMaxStacks = actionMaxStacks;
        this.actionCurrentStacks = 0;
    }

    public abstract void printActionPerforming();

    @Override
    public abstract void performAction();

    @Override
    public void handleAction(CharactersInvolvedInBattle charactersInvolvedInBattle) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.actionsRuntimeTypeAdapterFactory).create();
        ActionWithDuration action = gson.fromJson(gson.toJson(this), this.getClass());
        action.setNewCharactersInvolvedInBattle(charactersInvolvedInBattle);
        action.getCharactersInvolvedInBattle().getSpellTarget().setNewActionOrAddStackToExistingAction(action);
    }

    public void addActionStack() {
        this.actionCurrentStacks++;
    }

    public void actionAddTurn() {
        this.currentActionTurn++;
    }

    public void actionCurrentTurnReset() {
        this.currentActionTurn = 0;
    }

    public boolean checkIfActionReachMaxActionTurns() {
        return this.maxActionTurns == this.currentActionTurn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionWithDuration that = (ActionWithDuration) o;
        return actionName.equals(that.actionName) && maxActionTurns == that.maxActionTurns && actionMaxStacks == that.actionMaxStacks;
    }

    @Override
    public int hashCode() {
        return Objects.hash(actionName, maxActionTurns, actionMaxStacks);
    }
}
