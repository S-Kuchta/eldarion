package kuchtastefan.actions;

import kuchtastefan.characters.GameCharacter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Action {

    private String actionName;
    private int actionValue;

    public Action(String actionName, int actionValue) {
        this.actionName = actionName;
        this.actionValue = actionValue;
    }

    public void performAction(GameCharacter gameCharacter) {
        System.out.println("in actions");
    }
}
