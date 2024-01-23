package kuchtastefan.actions.actionsWIthDuration;

import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.characters.GameCharacter;

public class ActionDealDamageOverTime extends ActionWithDuration {
    public ActionDealDamageOverTime(String actionName, ActionEffectOn actionEffectOn, int maxActionValue,
                                    int maxActionTurns, int actionMaxStacks, ActionDurationType actionDurationType) {
        super(actionName, actionEffectOn, maxActionValue, maxActionTurns, actionMaxStacks, actionDurationType);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        int damageWithStacks = this.getCurrentActionValue() * this.getActionCurrentStacks();

        System.out.println("\t" + this.getActionName() + " deal " + damageWithStacks + " damage to " + gameCharacter.getName() + "!" );

        gameCharacter.receiveDamage(damageWithStacks);
    }
}
