package kuchtastefan.actions.actionsWIthDuration;

import kuchtastefan.actions.Action;
import kuchtastefan.characters.GameCharacter;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class ActionWithDuration extends Action {

    private final int maxActionTurns;
    private int currentActionTurn;
    private final ActionDurationType actionDurationType;
    private final int actionMaxStacks;
    private int actionCurrentStacks;


    public ActionWithDuration(String actionName, int actionValue, int maxActionTurns, ActionDurationType actionDurationType, int actionMaxStacks) {
        super(actionName, actionValue);
        this.actionMaxStacks = actionMaxStacks;
        this.maxActionTurns = maxActionTurns;
        this.actionDurationType = actionDurationType;
        this.actionCurrentStacks = 0;
        this.currentActionTurn = 0;
    }

    public void addActionToGameCharacter(GameCharacter gameCharacter) {
        gameCharacter.addActionWithDuration(this);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        System.out.println("Vykonala sa tato akcia");
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
        return actionName.equals(that.actionName) && maxActionTurns == that.maxActionTurns && actionMaxStacks == that.actionMaxStacks && actionDurationType == that.actionDurationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxActionTurns, actionDurationType, actionMaxStacks);
    }
}
