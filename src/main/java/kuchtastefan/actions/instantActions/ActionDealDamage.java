package kuchtastefan.actions.instantActions;

import kuchtastefan.actions.Action;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.characters.GameCharacter;

public class ActionDealDamage extends Action {
    public ActionDealDamage(String actionName, ActionEffectOn actionEffectOn, int maxActionValue) {
        super(actionName, actionEffectOn, maxActionValue);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        System.out.println("\t" + this.getActionName() + " deal " + getCurrentActionValue() + " damage!");
        gameCharacter.receiveDamage(this.currentActionValue);
    }
}
