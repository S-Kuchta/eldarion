package kuchtastefan.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kuchtastefan.actions.Action;
import kuchtastefan.actions.ActionEffectOn;
import kuchtastefan.actions.actionsWIthDuration.ActionDurationType;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.actions.instantAction.ActionSummonCreature;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.spell.CharactersInvolvedInBattle;
import kuchtastefan.constant.Constant;
import kuchtastefan.utility.RandomNumberGenerator;
import kuchtastefan.utility.RuntimeTypeAdapterFactoryUtil;

import java.util.Set;

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

            this.performActionWithSpecificNeeds(action, charactersInvolvedInBattle);
        }
    }

    public void applyActionToTarget(Action action, GameCharacter hero) {
        performAction(action, hero);
    }

    private void performAction(Action action, GameCharacter actionTarget) {
        if (action instanceof ActionWithDuration actionWithDuration) {
            if (actionTarget != null) {
                addActionWithDurationToCharacter(actionWithDuration, actionTarget);
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

    public void addActionWithDurationToCharacter(ActionWithDuration actionWithDuration, GameCharacter actionTarget) {
        if (actionWithDuration.getActionDurationType().equals(ActionDurationType.REGION_ACTION)) {
            setNewActionOrAddStackToExistingAction(actionWithDuration, actionTarget.getRegionActionsWithDuration());
        }

        if (actionWithDuration.getActionDurationType().equals(ActionDurationType.BATTLE_ACTION)) {
            setNewActionOrAddStackToExistingAction(actionWithDuration, actionTarget.getBattleActionsWithDuration());
        }
    }

    /**
     * Method is responsible for add action to action list and reset turn value to 0. If list does not contain action,
     * action will be added as a new object. If list contains action and does not already reach max action stack,
     * you will get a new stack.
     *
     * @param actionWithDuration action with duration
     * @param actions            list where you want to add new action
     */
    private void setNewActionOrAddStackToExistingAction(ActionWithDuration actionWithDuration, Set<ActionWithDuration> actions) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.actionsRuntimeTypeAdapterFactory).create();

        if (!actions.contains(actionWithDuration)) {
            ActionWithDuration actionWithDurationNewCopy = gson.fromJson(gson.toJson(actionWithDuration), actionWithDuration.getClass());
            actions.add(actionWithDurationNewCopy);
            actionWithDurationNewCopy.addActionStack();
            actionWithDurationNewCopy.actionCurrentTurnReset();

        } else {
            for (ActionWithDuration action : actions) {
                if (action.equals(actionWithDuration) && action.getActionCurrentStacks() < action.getActionMaxStacks()) {
                    action.setCurrentActionValue(actionWithDuration.getCurrentActionValue());
                    action.addActionStack();
                    action.actionCurrentTurnReset();
                }

                if (action.equals(actionWithDuration) && action.getActionCurrentStacks() == action.getActionMaxStacks()) {
                    action.setCurrentActionValue(actionWithDuration.getCurrentActionValue());
                    action.actionCurrentTurnReset();
                }
            }
        }
    }

    /**
     * Actions that cannot be performed in the 'perform action' method of the respective action due to various reasons
     *
     * @param action to perform
     */
    private void performActionWithSpecificNeeds(Action action, CharactersInvolvedInBattle charactersInvolvedInBattle) {
        if (action instanceof ActionSummonCreature actionSummonCreature) {
            charactersInvolvedInBattle.tempCharacterList().add(actionSummonCreature.returnSummonedCharacter());
        }
    }
}
