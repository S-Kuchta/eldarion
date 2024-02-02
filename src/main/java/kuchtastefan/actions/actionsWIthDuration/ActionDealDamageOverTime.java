package kuchtastefan.actions.actionsWIthDuration;

import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.characters.GameCharacter;

public class ActionDealDamageOverTime extends ActionWithDuration {
    public ActionDealDamageOverTime(ActionName actionName, ActionEffectOn actionEffectOn,
                                    int maxActionValue, int maxActionTurns, int actionMaxStacks,
                                    ActionDurationType actionDurationType, int chanceToPerformAction,
                                    boolean canBeActionCriticalHit) {
        super(actionName, actionEffectOn, maxActionValue, maxActionTurns,
                actionMaxStacks, actionDurationType, chanceToPerformAction, canBeActionCriticalHit);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        int damageWithStacks = this.getCurrentActionValue() * this.getActionCurrentStacks();

//        System.out.println("\t" + this.getActionName() + " deal " + damageWithStacks + " damage to " + gameCharacter.getName() + "!" );

        System.out.print("\t"+ this.getActionName() + ", action deal: ");
        gameCharacter.receiveDamage(damageWithStacks);
    }
}
