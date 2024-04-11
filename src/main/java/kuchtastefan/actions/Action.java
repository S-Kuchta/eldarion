package kuchtastefan.actions;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.actionsWIthDuration.actionMarkerInterface.ActionWithBaseValue;
import kuchtastefan.actions.actionsWIthDuration.actionMarkerInterface.ActionWithIncreasedValueByAbility;
import kuchtastefan.actions.actionsWIthDuration.actionMarkerInterface.ActionWithoutValue;
import kuchtastefan.actions.actionsWIthDuration.specificActionWithDuration.ActionDealDamageOverTime;
import kuchtastefan.actions.instantActions.ActionDealDamage;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.constant.Constant;
import kuchtastefan.utility.RandomNumberGenerator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Action {

    protected ActionName actionName;
    protected final int baseActionValue;
    protected int currentActionValue;
    protected final ActionEffectOn actionEffectOn;
    protected final int chanceToPerformAction;
    protected final boolean canBeActionCriticalHit;
    protected int priorityPoints;


    public Action(ActionName actionName, ActionEffectOn actionEffectOn, int baseActionValue, int chanceToPerformAction, boolean canBeActionCriticalHit, int priorityPoints) {
        this.actionName = actionName;
        this.baseActionValue = baseActionValue;
        this.currentActionValue = baseActionValue;
        this.actionEffectOn = actionEffectOn;
        this.chanceToPerformAction = chanceToPerformAction;
        this.canBeActionCriticalHit = canBeActionCriticalHit;
        this.priorityPoints = priorityPoints;
    }

    public abstract void performAction(GameCharacter gameCharacter);

    public abstract void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget);

    public boolean willPerformAction() {
        return RandomNumberGenerator.getRandomNumber(0, 100) < this.chanceToPerformAction;
    }

    /**
     * Calculates the range of action values for a given spell caster.
     * This method calculates the range of action values based on the attributes
     * of the spell caster and the type of action. It considers factors such as
     * the base action value, the spell caster's level, and any additional
     * bonuses from abilities.
     *
     * @param spellCaster The character casting the spell.
     * @return An ActionValueRange object representing the range of action values.
     */
    public ActionValueRange returnActionValueRange(GameCharacter spellCaster) {
        // Value of action increase by multiplier per level - min. ability value
        int valueIncreasedByLevel = this.baseActionValue + (spellCaster.getLevel() * Constant.BONUS_VALUE_PER_LEVEL);
        // max. ability value
        int valueIncreasedByPrimaryAbility = 0;

        if (this instanceof ActionDealDamage || this instanceof ActionDealDamageOverTime) {
            valueIncreasedByLevel += spellCaster.getCurrentAbilityValue(Ability.ATTACK);
        }

        if (this instanceof ActionWithBaseValue) {
            valueIncreasedByPrimaryAbility = valueIncreasedByLevel;
        } else if (this instanceof ActionWithIncreasedValueByAbility) {
            if (spellCaster instanceof Hero hero) {
                valueIncreasedByPrimaryAbility = valueIncreasedByLevel + spellCaster.getCurrentAbilityValue(hero.getCharacterClass().getPrimaryAbility()) / 2;
            } else {
                valueIncreasedByPrimaryAbility = valueIncreasedByLevel + spellCaster.getCurrentAbilityValue(Ability.ATTACK);
            }
        }

        if (this instanceof ActionWithoutValue) {
            valueIncreasedByLevel = 0;
        }

        return new ActionValueRange(valueIncreasedByLevel, valueIncreasedByPrimaryAbility, valueIncreasedByLevel);
    }

    public int returnPriorityPoints(GameCharacter spellCaster, GameCharacter spellTarget) {
        return priorityPoints;
    }

    protected String returnTargetName(GameCharacter spellCaster, GameCharacter spellTarget) {
        if (this.actionEffectOn.equals(ActionEffectOn.SPELL_CASTER)) {
            return spellCaster.getName();
        } else {
            return spellTarget.getName();
        }
    }
}
