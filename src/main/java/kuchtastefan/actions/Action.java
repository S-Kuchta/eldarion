package kuchtastefan.actions;

import kuchtastefan.characters.GameCharacter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Action {

    protected String actionName;
    protected int actionValue;

    public Action(String actionName, int actionValue) {
        this.actionName = actionName;
        this.actionValue = actionValue;
    }

    public abstract void performAction(GameCharacter gameCharacter);
}
