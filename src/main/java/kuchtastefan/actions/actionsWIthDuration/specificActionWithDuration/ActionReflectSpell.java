package kuchtastefan.actions.actionsWIthDuration.specificActionWithDuration;

import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.ActionStatusEffect;
import kuchtastefan.actions.actionValue.ActionValueRange;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.actions.actionValue.ActionWithoutValue;
import kuchtastefan.character.GameCharacter;

public class ActionReflectSpell extends ActionWithDuration implements ActionWithoutValue {
    public ActionReflectSpell(ActionName actionName, ActionEffectOn actionEffectOn, int baseActionValue, int maxActionTurns,
                              int actionMaxStacks, int chanceToPerformAction, ActionStatusEffect actionStatusEffect) {
        super(actionName, actionEffectOn, baseActionValue, maxActionTurns, actionMaxStacks, chanceToPerformAction, actionStatusEffect);
    }

    @Override
    public void performAction() {
        charactersInvolvedInBattle.getSpellTarget().setReflectSpell(true);
    }

    @Override
    public void printActionPerforming() {
        System.out.println("\t" + charactersInvolvedInBattle.getSpellTarget().getName() + " reflect next spell");
    }

    @Override
    public void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget) {
        System.out.print("Reflect next Spell");
    }

    @Override
    public void printActiveAction() {
        System.out.println("\t" + this.charactersInvolvedInBattle.getSpellTarget().getName() + " reflect next spell");
    }

    @Override
    public int returnPriorityPoints(GameCharacter spellCaster, GameCharacter spellTarget) {
        return 3;
    }
}
