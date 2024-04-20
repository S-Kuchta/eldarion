package kuchtastefan.character;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.actionsWIthDuration.ActionDurationType;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.actions.actionsWIthDuration.specificActionWithDuration.ActionAbsorbDamage;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.spell.Spell;
import kuchtastefan.constant.Constant;
import kuchtastefan.utility.ConsoleColor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Setter
@Getter
public abstract class GameCharacter {

    protected String name;
    protected int level;
    protected Map<Ability, Integer> baseAbilities;
    protected Map<Ability, Integer> enhancedAbilities;
    protected Map<Ability, Integer> effectiveAbilities;
    protected Set<ActionWithDuration> regionActionsWithDuration;
    protected Set<ActionWithDuration> battleActionsWithDuration;
    protected List<Spell> characterSpellList;
    protected boolean canPerformAction;
    protected boolean reflectSpell;


    public GameCharacter(String name, Map<Ability, Integer> baseAbilities) {
        this.name = name;
        this.baseAbilities = baseAbilities;
        this.enhancedAbilities = new HashMap<>(baseAbilities);
        this.effectiveAbilities = new HashMap<>(baseAbilities);
        this.regionActionsWithDuration = new HashSet<>();
        this.battleActionsWithDuration = new HashSet<>();
        this.characterSpellList = new ArrayList<>();
        this.canPerformAction = true;
    }

    /**
     * Call this method when you want to update ability points depending on active actions (buff, de buff)
     * If actionDurationType is same as type from parameter, you will get turn for action
     * Method also check if you reach max turns. If yes, action is removed.
     *
     * @param actionDurationType from where you call method (BATTLE or REGION(EVENT))
     */
    public void performActionsWithDuration(ActionDurationType actionDurationType) {
        resetAbilitiesToMaxValues(false);

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
        actions.clear();
    }

    public void checkAndRemoveActionTurns() {
        this.battleActionsWithDuration.removeIf(ActionWithDuration::checkIfActionReachMaxActionTurns);
        this.regionActionsWithDuration.removeIf(ActionWithDuration::checkIfActionReachMaxActionTurns);
    }

    public void resetAbilitiesToMaxValues(boolean setHealthOrManaToMaxValue) {
        this.canPerformAction = true;
        this.reflectSpell = false;

        for (Ability ability : Ability.values()) {
            if (ability.equals(Ability.HEALTH) || ability.equals(Ability.MANA)) {
                if (setHealthOrManaToMaxValue) {
                    this.effectiveAbilities.put(ability, this.enhancedAbilities.get(ability));
                } else {
                    this.effectiveAbilities.put(Ability.HEALTH, getCurrentAbilityValue(Ability.HEALTH));
                    this.effectiveAbilities.put(Ability.MANA, getCurrentAbilityValue(Ability.MANA));
                }
            } else {
                this.effectiveAbilities.put(ability, this.enhancedAbilities.get(ability));
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
     * @param incomingDamage - damage dealt by attacker
     */
    public void receiveDamage(int incomingDamage) {
        int damage = returnDamageAfterResistDamage(incomingDamage);

        // Handle absorb damage
        int absorbDamage = 0;
        Iterator<ActionWithDuration> iterator = this.battleActionsWithDuration.iterator();
        while (iterator.hasNext()) {

            ActionWithDuration action = iterator.next();
            if (action instanceof ActionAbsorbDamage) {
                if (action.getCurrentActionValue() >= damage) {
                    action.setCurrentActionValue(action.getCurrentActionValue() - damage);
                    damage = 0;
                } else {
                    damage -= action.getCurrentActionValue();
                    action.setCurrentActionValue(0);
                    iterator.remove();
                }

                absorbDamage += action.getCurrentActionValue();
            }
        }

        System.out.println(ConsoleColor.RED + "" + damage + ConsoleColor.RESET + " damage to " + this.name);
        this.effectiveAbilities.put(Ability.ABSORB_DAMAGE, absorbDamage);
        this.effectiveAbilities.put(Ability.HEALTH, this.getCurrentAbilityValue(Ability.HEALTH) - damage);
    }

    public int returnDamageAfterResistDamage(int incomingDamage) {
        if (this.getCurrentAbilityValue(Ability.RESIST_DAMAGE) >= incomingDamage) {
            return 0;
        } else {
            return incomingDamage - this.getCurrentAbilityValue(Ability.RESIST_DAMAGE);
        }
    }

    public void restoreAbilityValue(int amountToRestore, Ability ability) {
        int maxCharacterAbility = this.getEnhancedAbilities().get(ability);
        int currentCharacterAbility = this.getCurrentAbilityValue(ability);

        if (maxCharacterAbility - currentCharacterAbility <= amountToRestore) {
            this.effectiveAbilities.put(ability, maxCharacterAbility);
        } else {
            this.effectiveAbilities.put(ability, currentCharacterAbility + amountToRestore);
        }

        ConsoleColor consoleColor = ConsoleColor.RED;
        if (ability.equals(Ability.MANA)) {
            consoleColor = ConsoleColor.BLUE;
        }

        if (ability.equals(Ability.HEALTH) || (ability.equals(Ability.MANA) && this.getCurrentAbilityValue(Ability.MANA) != 0)) {
            System.out.println("\t" + this.name + " have restored " + consoleColor + amountToRestore + ConsoleColor.RESET
                    + " " + ability.name()
                    + ". " + this.name + " " + ability.name() + " is "
                    + consoleColor + this.getCurrentAbilityValue(ability) + ConsoleColor.RESET);
        }
    }

    public void restoreHealthAndManaAfterTurn() {
        if (this instanceof Hero hero && !hero.isInCombat()) {
            this.restoreAbilityValue(this.getCurrentAbilityValue(Ability.HASTE)
                    * Constant.RESTORE_HEALTH_PER_ONE_HASTE, Ability.HEALTH);
        }

        this.restoreAbilityValue(this.getCurrentAbilityValue(Ability.HASTE)
                * Constant.RESTORE_MANA_PER_ONE_HASTE, Ability.MANA);
    }

    public void decreaseCurrentAbilityValue(int valueOfLower, Ability ability) {
        int currentAbility = getCurrentAbilityValue(ability);
        this.effectiveAbilities.put(ability, Math.max(currentAbility - valueOfLower, 0));
    }

    public int getCurrentAbilityValue(Ability ability) {
        return this.effectiveAbilities.get(ability);
    }

    public void increaseCurrentAbilityValue(Ability ability, int valueToIncrease) {
        this.effectiveAbilities.put(ability, this.getCurrentAbilityValue(ability) + valueToIncrease);
    }
}
