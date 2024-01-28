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
    protected final boolean canBeActionCriticalHit;


    public Action(String actionName, ActionEffectOn actionEffectOn, int maxActionValue, int chanceToPerformAction, boolean canBeActionCriticalHit) {
        this.actionName = actionName;
        this.maxActionValue = maxActionValue;
        this.currentActionValue = maxActionValue;
        this.actionEffectOn = actionEffectOn;
        this.chanceToPerformAction = chanceToPerformAction;
        this.canBeActionCriticalHit = canBeActionCriticalHit;
    }

    public abstract void performAction(GameCharacter gameCharacter);

    public boolean willPerformAction() {
        return RandomNumberGenerator.getRandomNumber(0, 100) <= this.chanceToPerformAction;
    }

    public void setNewCurrentActionValue(int value) {
        this.currentActionValue = value;
    }
}
