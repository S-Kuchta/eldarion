package kuchtastefan.actions;

import kuchtastefan.characters.GameCharacter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Action {

    protected String actionName;
    protected final int maxActionValue;
    protected int currentActionValue;
    protected final ActionEffectOn actionEffectOn;


    public Action(String actionName, ActionEffectOn actionEffectOn, int maxActionValue) {
        this.actionName = actionName;
        this.maxActionValue = maxActionValue;
        this.currentActionValue = maxActionValue;
        this.actionEffectOn = actionEffectOn;
    }

    public abstract void performAction(GameCharacter gameCharacter);

    public void setNewActionValue(int value) {
        this.currentActionValue += value;
    }
}
