package kuchtastefan.characters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kuchtastefan.ability.Ability;
import kuchtastefan.actions.actionsWIthDuration.ActionDurationType;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.utility.RuntimeTypeAdapterFactoryUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Setter
@Getter
public abstract class GameCharacter {
    protected String name;
    protected int level;
    protected Map<Ability, Integer> abilities;
    protected Map<Ability, Integer> maxAbilities;
    protected Map<Ability, Integer> currentAbilities;
    protected Set<ActionWithDuration> regionActionsWithDuration;
    protected Set<ActionWithDuration> battleActionsWithDuration;



    public GameCharacter(String name, Map<Ability, Integer> abilities) {
        this.name = name;
        this.abilities = abilities;
        this.level = 1;
        this.maxAbilities = new HashMap<>(abilities);
        this.currentAbilities = new HashMap<>(abilities);
        this.regionActionsWithDuration = new HashSet<>();
        this.battleActionsWithDuration = new HashSet<>();
    }

    public GameCharacter(String name, int level) {
        this.name = name;
        this.level = level;
        this.abilities = initializeAbilityForNonEnemyCharacters();
        this.maxAbilities = new HashMap<>(initializeAbilityForNonEnemyCharacters());
        this.currentAbilities = new HashMap<>(initializeAbilityForNonEnemyCharacters());
    }

    public void addActionWithDuration(ActionWithDuration actionWithDuration) {

        if (actionWithDuration.getActionDurationType().equals(ActionDurationType.REGION_ACTION)) {
            addActionOrIncreaseStack(actionWithDuration, this.regionActionsWithDuration);
            addActionOrIncreaseStack(actionWithDuration, this.battleActionsWithDuration);
        }

        if (actionWithDuration.getActionDurationType().equals(ActionDurationType.BATTLE_ACTION)) {
            addActionOrIncreaseStack(actionWithDuration, this.battleActionsWithDuration);
        }

        updateCurrentAbilitiesDependsOnActiveActions(null);
    }

    /**
     * Method is responsible for add action to action list and reset turn value to 0. If list does not contain action,
     * action will be added as a new object. If list contains action and does not already reach max action stack,
     * you will get a new stack.
     *
     * @param actionWithDuration action with duration
     * @param actions list where you want to add new action
     */
    private void addActionOrIncreaseStack(ActionWithDuration actionWithDuration, Set<ActionWithDuration> actions) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.actionsRuntimeTypeAdapterFactory).create();

        if (!actions.contains(actionWithDuration)) {
            ActionWithDuration actionWithDurationNewCopy = gson.fromJson(gson.toJson(actionWithDuration), actionWithDuration.getClass());
            actions.add(actionWithDurationNewCopy);
            actionWithDurationNewCopy.addActionStack();
            actionWithDurationNewCopy.actionCurrentTurnReset();

        } else {
            for (ActionWithDuration action : actions) {
                if (action.equals(actionWithDuration) && action.getActionCurrentStacks() < action.getActionMaxStacks()) {
                    System.out.println(action.equals(actionWithDuration));
                    action.addActionStack();
                    action.actionCurrentTurnReset();
                }

                if (action.equals(actionWithDuration) && action.getActionCurrentStacks() == action.getActionMaxStacks()) {
                    action.actionCurrentTurnReset();
                }
            }
        }

    }

    /**
     * Call this method when you want to update ability points depending on active actions (buff, de buff)
     * If actionDurationType is same as type from parameter, you will get turn for action
     * Method also check if you reach max turns. If yes, action is removed.
     *
     * @param actionDurationType from where you call method (BATTLE or REGION(EVENT)
     */
    public void updateCurrentAbilitiesDependsOnActiveActions(ActionDurationType actionDurationType) {
        updateCurrentAbilitiesWithMaxAbilities();

        for (ActionWithDuration actionWithDuration : this.regionActionsWithDuration) {
            actionWithDuration.performAction(this);
            if (actionWithDuration.getActionDurationType().equals(actionDurationType)) {
                actionWithDuration.actionAddTurn();
            }

            if (actionWithDuration.checkIfActionReachMaxActionTurns()) {
                this.regionActionsWithDuration.remove(actionWithDuration);
                this.battleActionsWithDuration.remove(actionWithDuration);
            }
        }
    }

    private void updateCurrentAbilitiesWithMaxAbilities() {
        for (Ability ability : Ability.values()) {
            if (!ability.equals(Ability.HEALTH)) {
                this.currentAbilities.put(ability, this.maxAbilities.get(ability));
            }
        }
    }

    public void receiveDamage(int damage) {
        this.currentAbilities.put(Ability.HEALTH, this.getCurrentAbilityValue(Ability.HEALTH) - damage);
    }

    public void restoreHealth(int valueOfRestore) {
        int maxCharacterHealth = this.getMaxAbilities().get(Ability.HEALTH);
        int currentCharacterHealth = this.getCurrentAbilityValue(Ability.HEALTH);

        if (maxCharacterHealth - currentCharacterHealth <= valueOfRestore) {
            this.getCurrentAbilities().put(Ability.HEALTH, maxCharacterHealth);
        } else {
            this.getCurrentAbilities().put(Ability.HEALTH, currentCharacterHealth + valueOfRestore);
        }

        System.out.println("\tYou have restored " + valueOfRestore + " health. Your healths are: "
                + this.getCurrentAbilityValue(Ability.HEALTH));
    }

    public Map<Ability, Integer> initializeAbilityForNonEnemyCharacters() {
        return new HashMap<>(Map.of(
                Ability.ATTACK, 15,
                Ability.DEFENCE, 15,
                Ability.DEXTERITY, 15,
                Ability.SKILL, 15,
                Ability.LUCK, 15,
                Ability.HEALTH, 250
        ));
    }

    public int getCurrentAbilityValue(Ability ability) {
        return this.currentAbilities.get(ability);
    }

}
