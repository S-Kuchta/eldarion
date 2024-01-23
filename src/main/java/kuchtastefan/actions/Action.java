package kuchtastefan.actions;

import kuchtastefan.characters.GameCharacter;
import kuchtastefan.utility.RandomNumberGenerator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Action {

    protected String actionName;
    protected final int maxActionValue;
    protected int currentActionValue;
    protected final ActionEffectOn actionEffectOn;
    protected final int chanceToPerformAction;


    public Action(String actionName, ActionEffectOn actionEffectOn, int maxActionValue, int chanceToPerformAction) {
        this.actionName = actionName;
        this.maxActionValue = maxActionValue;
        this.currentActionValue = maxActionValue;
        this.actionEffectOn = actionEffectOn;
        this.chanceToPerformAction = chanceToPerformAction;
    }

    public abstract void performAction(GameCharacter gameCharacter);

    public boolean isPossibleToPerformAction() {
        return RandomNumberGenerator.getRandomNumber(0, 100) <= this.chanceToPerformAction;
    }

    public void setNewActionValue(int value) {
        this.currentActionValue += value;
    }
}
