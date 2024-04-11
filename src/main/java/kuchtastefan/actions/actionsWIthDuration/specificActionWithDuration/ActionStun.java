package kuchtastefan.actions.actionsWIthDuration.specificActionWithDuration;

import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.actionsWIthDuration.ActionDurationType;
import kuchtastefan.actions.ActionStatusEffect;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.actions.actionsWIthDuration.actionMarkerInterface.ActionWithoutValue;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.utility.ConsoleColor;

public class ActionStun extends ActionWithDuration implements ActionWithoutValue {

    public ActionStun(ActionName actionName, ActionEffectOn actionEffectOn, int maxActionValue,
                      int maxActionTurns, int actionMaxStacks, ActionDurationType actionDurationType,
                      int chanceToPerformAction, boolean canBeActionCriticalHit, ActionStatusEffect actionStatusEffect) {

        super(actionName, actionEffectOn, maxActionValue, maxActionTurns, actionMaxStacks,
                actionDurationType, chanceToPerformAction, canBeActionCriticalHit, actionStatusEffect, 3);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        gameCharacter.setCanPerformAction(false);
        System.out.println("\t" + ConsoleColor.YELLOW + gameCharacter.getName() + ConsoleColor.RESET + " is stunned!");
    }

    @Override
    public void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget) {
        System.out.println("Stun " + this.returnTargetName(spellCaster, spellTarget) + " for next " + this.getMaxActionTurns() + " turns" );
    }
}
