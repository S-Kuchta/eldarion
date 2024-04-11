package kuchtastefan.service;

import kuchtastefan.actions.Action;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.actions.instantAction.ActionSummonCreature;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.spell.CharactersInvolvedInBattle;
import kuchtastefan.constant.Constant;
import kuchtastefan.utility.RandomNumberGenerator;

public class ActionService {

    public void applyActionToTarget(Action action, CharactersInvolvedInBattle charactersInvolvedInBattle, boolean criticalHit, boolean hitAllInvolvedCharacters) {
        GameCharacter actionTarget;

        if (action.willPerformAction()) {
            int totalActionValue = RandomNumberGenerator.getRandomNumber(
                    action.returnActionValueRange(charactersInvolvedInBattle.spellCaster()).minimumValue(),
                    action.returnActionValueRange(charactersInvolvedInBattle.spellCaster()).maximumValue());

            if (criticalHit && action.isCanBeActionCriticalHit()) {
                System.out.println("\t" + action.getActionName() + " Critical hit!");
                totalActionValue *= Constant.CRITICAL_HIT_MULTIPLIER;
            }

            action.setCurrentActionValue(totalActionValue);

            if (hitAllInvolvedCharacters) {
                if (action.getActionEffectOn().equals(ActionEffectOn.SPELL_CASTER)) {
                    for (GameCharacter aliesCharacter : charactersInvolvedInBattle.alliesCharacters()) {
                        performAction(action, aliesCharacter);
                    }
                }

                if (action.getActionEffectOn().equals(ActionEffectOn.SPELL_TARGET)) {
                    for (GameCharacter enemyCharacter : charactersInvolvedInBattle.alliesCharacters()) {
                        performAction(action, enemyCharacter);
                    }
                }

            } else {
                actionTarget = determineActionTarget(action, charactersInvolvedInBattle);
                performAction(action, actionTarget);
            }

            if (action instanceof ActionSummonCreature actionSummonCreature) {
                charactersInvolvedInBattle.tempCharacterList().add(actionSummonCreature.returnSummonedCharacter());
            }
        }
    }

    private void performAction(Action action, GameCharacter actionTarget) {
        if (action instanceof ActionWithDuration actionWithDuration) {
            if (actionTarget != null) {
                actionTarget.addActionWithDuration(actionWithDuration);
//                actionWithDuration.performAction(actionTarget);
            } else {
                System.out.println("\tYou don't have a target!");
            }
        } else {
            action.performAction(actionTarget);
        }
    }


    private GameCharacter determineActionTarget(Action action, CharactersInvolvedInBattle charactersInvolvedInBattle) {

        if (action.getActionEffectOn().equals(ActionEffectOn.PLAYER)) {
            return charactersInvolvedInBattle.hero();
        }

        if (action.getActionEffectOn().equals(ActionEffectOn.SPELL_TARGET)) {
            return charactersInvolvedInBattle.spellTarget();
        }

        if (action.getActionEffectOn().equals(ActionEffectOn.SPELL_CASTER)) {
            return charactersInvolvedInBattle.spellCaster();
        }

        return null;
    }
}
