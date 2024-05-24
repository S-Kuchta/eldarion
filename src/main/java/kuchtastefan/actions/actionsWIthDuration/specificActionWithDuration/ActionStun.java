package kuchtastefan.actions.actionsWIthDuration.specificActionWithDuration;

import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.ActionStatusEffect;
import kuchtastefan.actions.actionsWIthDuration.PerformActionBeforeTurn;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.actions.actionValue.ActionWithoutValue;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.utility.ConsoleColor;

public class ActionStun extends ActionWithDuration implements ActionWithoutValue, PerformActionBeforeTurn {

    public ActionStun(ActionName actionName, ActionEffectOn actionEffectOn, int baseActionValue, int maxActionTurns,
                      int actionMaxStacks, int chanceToPerformAction, ActionStatusEffect actionStatusEffect) {
        super(actionName, actionEffectOn, baseActionValue, maxActionTurns, actionMaxStacks,
                chanceToPerformAction, actionStatusEffect);
    }

    @Override
    public void performAction() {
        charactersInvolvedInBattle.getSpellTarget().setCanPerformAction(false);
    }

    @Override
    public void printActionPerforming() {
        System.out.println("\t" + ConsoleColor.YELLOW + charactersInvolvedInBattle.getSpellTarget().getName() + ConsoleColor.RESET + " is stunned!");
    }

    @Override
    public void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget) {
        System.out.println("Stun " + this.returnTargetName(spellCaster, spellTarget) + " for next " + this.getMaxActionTurns() + " turns");
    }

    @Override
    public void printActiveAction() {
        System.out.println("\t" + charactersInvolvedInBattle.getSpellTarget().getName() + " is stunned!");
        }

    @Override
    public int returnPriorityPoints(GameCharacter spellCaster, GameCharacter spellTarget) {
        return 2 + getMaxActionTurns();
    }
}
