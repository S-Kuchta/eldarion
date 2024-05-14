package kuchtastefan.actions.actionsWIthDuration.specificActionWithDuration;

import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.ActionName;
import kuchtastefan.actions.ActionStatusEffect;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.actions.actionValue.ActionWithoutValue;
import kuchtastefan.character.GameCharacter;

public class ActionReflectSpell extends ActionWithDuration implements ActionWithoutValue {
    public ActionReflectSpell(ActionName actionName, ActionEffectOn actionEffectOn, int baseActionValue, int maxActionTurns,
                              int actionMaxStacks, int chanceToPerformAction, ActionStatusEffect actionStatusEffect) {
        super(actionName, actionEffectOn, baseActionValue, maxActionTurns, actionMaxStacks, chanceToPerformAction, actionStatusEffect);
    }

    @Override
    public void performAction(GameCharacter spellCaster, GameCharacter spellTarget) {
        spellCaster.setReflectSpell(true);
    }

    @Override
    public void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget) {
        System.out.print("Reflect next Spell");
    }

    @Override
    public int returnPriorityPoints(GameCharacter spellCaster, GameCharacter spellTarget) {
        return 3;
    }
}
