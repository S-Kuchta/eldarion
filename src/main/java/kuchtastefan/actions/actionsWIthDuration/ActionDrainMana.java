package kuchtastefan.actions.actionsWIthDuration;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.character.GameCharacter;

public class ActionDrainMana extends ActionWithDuration {

    public ActionDrainMana(ActionName actionName, ActionEffectOn actionEffectOn, int maxActionValue,
                           int maxActionTurns, int actionMaxStacks, ActionDurationType actionDurationType,
                           int chanceToPerformAction, boolean canBeActionCriticalHit,
                           ActionStatusEffect actionStatusEffect) {

        super(actionName, actionEffectOn, maxActionValue, maxActionTurns, actionMaxStacks, actionDurationType, chanceToPerformAction, canBeActionCriticalHit, actionStatusEffect);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        int value = this.currentActionValue;
        if (gameCharacter.getCurrentAbilityValue(Ability.MANA) < this.currentActionValue) {
            value = gameCharacter.getCurrentAbilityValue(Ability.MANA);
        }

        System.out.println("\t" + gameCharacter.getName() + " lost " + value + " Mana");
        gameCharacter.decreaseCurrentAbility(this.currentActionValue, Ability.MANA);
    }
}
