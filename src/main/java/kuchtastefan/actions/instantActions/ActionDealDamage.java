package kuchtastefan.actions.instantActions;

import kuchtastefan.actions.Action;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.characters.GameCharacter;

public class ActionDealDamage extends Action {
    public ActionDealDamage(String actionName, ActionEffectOn actionEffectOn,
                            int maxActionValue, int chanceToPerformAction, boolean canBeActionCriticalHit) {
        super(actionName, actionEffectOn, maxActionValue, chanceToPerformAction, canBeActionCriticalHit);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
//            System.out.println("\t" + this.getActionName() + " deal " + getCurrentActionValue() + " damage!");
        System.out.print("\t"+ this.getActionName() + ", action deal: ");
        gameCharacter.receiveDamage(this.currentActionValue);
    }
}
