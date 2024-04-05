package kuchtastefan.actions.actionsWIthDuration.specificActionsWithDuration;

import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.actionsWIthDuration.ActionDurationType;
import kuchtastefan.actions.actionsWIthDuration.ActionStatusEffect;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.utility.ConsoleColor;

public class ActionStun extends ActionWithDuration {

    public ActionStun(ActionName actionName, ActionEffectOn actionEffectOn, int maxActionValue,
                      int maxActionTurns, int actionMaxStacks, ActionDurationType actionDurationType,
                      int chanceToPerformAction, boolean canBeActionCriticalHit, ActionStatusEffect actionStatusEffect) {

        super(actionName, actionEffectOn, maxActionValue, maxActionTurns, actionMaxStacks,
                actionDurationType, chanceToPerformAction, canBeActionCriticalHit, actionStatusEffect);
    }

    @Override
    public void performAction(GameCharacter gameCharacter) {
        gameCharacter.setCanPerformAction(false);
        System.out.println("\t" + ConsoleColor.YELLOW + gameCharacter.getName() + ConsoleColor.RESET + " is stunned!");
    }
}
