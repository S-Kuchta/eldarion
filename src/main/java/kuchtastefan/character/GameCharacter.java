package kuchtastefan.character;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kuchtastefan.ability.Ability;
import kuchtastefan.actions.actionsWIthDuration.ActionAbsorbDamage;
import kuchtastefan.actions.actionsWIthDuration.ActionDurationType;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.character.spell.Spell;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.RuntimeTypeAdapterFactoryUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

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
    protected List<Spell> characterSpellList;
    protected boolean canPerformAction;
    protected boolean reflectSpell;


    public GameCharacter(String name, Map<Ability, Integer> abilities) {
        this.name = name;
        this.abilities = abilities;
        this.maxAbilities = new HashMap<>(abilities);
        this.currentAbilities = new HashMap<>(abilities);
        this.regionActionsWithDuration = new HashSet<>();
        this.battleActionsWithDuration = new HashSet<>();
        this.characterSpellList = new ArrayList<>();
        this.canPerformAction = true;
    }

    public GameCharacter(String name, int level) {
        this.name = name;
        this.level = level;
        this.abilities = initializeAbilityForNonEnemyCharacters();
        this.maxAbilities = new HashMap<>(initializeAbilityForNonEnemyCharacters());
        this.currentAbilities = new HashMap<>(initializeAbilityForNonEnemyCharacters());
        this.characterSpellList = new ArrayList<>();
    }

    public void addActionWithDuration(ActionWithDuration actionWithDuration) {
        if (actionWithDuration.getActionDurationType().equals(ActionDurationType.REGION_ACTION)) {
            setNewActionOrAddStackToExistingAction(actionWithDuration, this.regionActionsWithDuration);
            setNewActionOrAddStackToExistingAction(actionWithDuration, this.battleActionsWithDuration);
        }

        if (actionWithDuration.getActionDurationType().equals(ActionDurationType.BATTLE_ACTION)) {
            setNewActionOrAddStackToExistingAction(actionWithDuration, this.battleActionsWithDuration);
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
                    action.addActionStack();
                    action.actionCurrentTurnReset();
                }

                if (action.equals(actionWithDuration) && action.getActionCurrentStacks() == action.getActionMaxStacks()) {
                    action.setNewCurrentActionValue(actionWithDuration.getCurrentActionValue());
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
     * @param actionDurationType from where you call method (BATTLE or REGION(EVENT))
     */
    public void performActionsWithDuration(ActionDurationType actionDurationType) {
        resetCurrentAbilitiesToMaxAbilities(false);

        Set<ActionWithDuration> actions = new HashSet<>();
        actions.addAll(this.regionActionsWithDuration);
        actions.addAll(this.battleActionsWithDuration);

        for (ActionWithDuration actionWithDuration : actions) {
            actionWithDuration.performAction(this);
            if (actionWithDuration.getActionDurationType().equals(actionDurationType)) {
                actionWithDuration.actionAddTurn();
            }
        }

        checkAndRemoveActionTurns();
    }

    public void checkAndRemoveActionTurns() {
        this.battleActionsWithDuration.removeIf(ActionWithDuration::checkIfActionReachMaxActionTurns);
        this.regionActionsWithDuration.removeIf(ActionWithDuration::checkIfActionReachMaxActionTurns);
    }

    protected void resetCurrentAbilitiesToMaxAbilities(boolean setHealthOrManaToMaxValue) {
        this.canPerformAction = true;
        this.reflectSpell = false;

        for (Ability ability : Ability.values()) {
            if (ability.equals(Ability.HEALTH) || ability.equals(Ability.MANA)) {
                if (setHealthOrManaToMaxValue) {
                    this.currentAbilities.put(ability, this.maxAbilities.get(ability));
                } else {
                    this.currentAbilities.put(Ability.HEALTH, getCurrentAbilityValue(Ability.HEALTH));
                    this.currentAbilities.put(Ability.MANA, getCurrentAbilityValue(Ability.MANA));
                }
            } else {
                this.currentAbilities.put(ability, this.maxAbilities.get(ability));
            }
        }
    }

    /**
     * Method is responsible for receiving character damage.
     * Damage is calculated damage - character resist damage
     * If character have active absorb damage, absorb damage will take corresponding amount of damage instead of health
     * If damage is higher than absorb damage, then absorb damage drop to 0 and left amount of damage will be dealt to health
     * If Absorb damage is 0, all actions with duration will be removed from battleActionsWithDuration.
     *
     * @param damage dealt
     */
    public void receiveDamage(int damage) {
        if (getCurrentAbilityValue(Ability.RESIST_DAMAGE) > damage) {
            damage = 0;
        } else {
            damage -= getCurrentAbilityValue(Ability.RESIST_DAMAGE);
        }

        int absorbDamage = 0;
        Iterator<ActionWithDuration> iterator = this.battleActionsWithDuration.iterator();
        while (iterator.hasNext()) {

            ActionWithDuration actionAbsorbDamage = iterator.next();
            if (actionAbsorbDamage instanceof ActionAbsorbDamage) {
                if (actionAbsorbDamage.getCurrentActionValue() > damage) {
                    actionAbsorbDamage.setNewCurrentActionValue(actionAbsorbDamage.getCurrentActionValue() - damage);
                    damage = 0;
                } else {
                    damage -= actionAbsorbDamage.getCurrentActionValue();
                    actionAbsorbDamage.setNewCurrentActionValue(0);
                    iterator.remove();
                }
                absorbDamage += actionAbsorbDamage.getCurrentActionValue();
            }
        }

        System.out.println(ConsoleColor.RED + "" + damage + ConsoleColor.RESET + " damage to " + this.name);
        this.currentAbilities.put(Ability.ABSORB_DAMAGE, absorbDamage);
        this.currentAbilities.put(Ability.HEALTH, this.getCurrentAbilityValue(Ability.HEALTH) - damage);
    }

    public void restoreAbility(int valueOfRestore, Ability ability) {
        int maxCharacterAbility = this.getMaxAbilities().get(ability);
        int currentCharacterAbility = this.getCurrentAbilityValue(ability);

        if (maxCharacterAbility - currentCharacterAbility <= valueOfRestore) {
            this.currentAbilities.put(ability, maxCharacterAbility);
        } else {
            this.currentAbilities.put(ability, currentCharacterAbility + valueOfRestore);
        }

        ConsoleColor consoleColor = ConsoleColor.RED;
        if (ability.equals(Ability.MANA)) {
            consoleColor = ConsoleColor.BLUE;
        }

        if (ability.equals(Ability.HEALTH) || (ability.equals(Ability.MANA) && this.getCurrentAbilityValue(Ability.MANA) != 0)) {
            System.out.println("\t" + this.name + " have restored " + consoleColor + valueOfRestore + ConsoleColor.RESET
                    + " " + ability.name()
                    + ". " + this.name + " " + ability.name() + " is "
                    + consoleColor + this.getCurrentAbilityValue(ability) + ConsoleColor.RESET);
        }
    }

    public void decreaseCurrentAbility(int valueOfLower, Ability ability) {
        int currentAbility = getCurrentAbilityValue(ability);
        this.currentAbilities.put(ability, Math.max(currentAbility - valueOfLower, 0));
    }

    public int getCurrentAbilityValue(Ability ability) {
        return this.currentAbilities.get(ability);
    }

    public void increaseCurrentAbilityValue(Ability ability, int valueToIncrease) {
        this.currentAbilities.put(ability, this.getCurrentAbilityValue(ability) + valueToIncrease);
    }

    public Map<Ability, Integer> initializeAbilityForNonEnemyCharacters() {
        return new HashMap<>(Map.of(
                Ability.ATTACK, 15,
                Ability.RESIST_DAMAGE, 15,
                Ability.STRENGTH, 15,
                Ability.INTELLECT, 15,
                Ability.HASTE, 10,
                Ability.HEALTH, 250,
                Ability.MANA, 150,
                Ability.ABSORB_DAMAGE, 0
        ));
    }
}
