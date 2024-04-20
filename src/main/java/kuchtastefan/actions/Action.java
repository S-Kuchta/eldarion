package kuchtastefan.actions;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.actionsWIthDuration.actionMarkerInterface.ActionWithBaseValue;
import kuchtastefan.actions.actionsWIthDuration.actionMarkerInterface.ActionWithIncreasedValueByPrimaryAbility;
import kuchtastefan.actions.actionsWIthDuration.actionMarkerInterface.ActionWithoutValue;
import kuchtastefan.actions.actionsWIthDuration.specificActionWithDuration.ActionDealDamageOverTimePrimary;
import kuchtastefan.actions.instantAction.ActionDealDamagePrimary;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.npc.NonPlayerCharacter;
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


    public Action(ActionName actionName, ActionEffectOn actionEffectOn, int baseActionValue, int chanceToPerformAction, boolean canBeActionCriticalHit) {
        this.actionName = actionName;
        this.baseActionValue = baseActionValue;
        this.currentActionValue = baseActionValue;
        this.actionEffectOn = actionEffectOn;
        this.chanceToPerformAction = chanceToPerformAction;
        this.canBeActionCriticalHit = canBeActionCriticalHit;
    }

    public abstract void performAction(GameCharacter gameCharacter);

    public abstract void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget);

    public abstract int returnPriorityPoints(GameCharacter spellCaster, GameCharacter spellTarget);

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
        // Value of action increase by multiplier per level -> min. ability value
        int valueIncreasedByLevel = this.baseActionValue + (spellCaster.getLevel() * Constant.BONUS_VALUE_PER_LEVEL);
        // max. ability value
        int valueIncreasedByPrimaryAbility = 0;

        if (this instanceof ActionDealDamagePrimary || this instanceof ActionDealDamageOverTimePrimary) {
            valueIncreasedByLevel += spellCaster.getCurrentAbilityValue(Ability.ATTACK);
        }

        if (this instanceof ActionWithBaseValue) {
            valueIncreasedByPrimaryAbility = valueIncreasedByLevel;
        }

        if (this instanceof ActionWithIncreasedValueByPrimaryAbility) {
            if (spellCaster instanceof Hero hero) {
                valueIncreasedByPrimaryAbility = valueIncreasedByLevel +
                        spellCaster.getCurrentAbilityValue(hero.getCharacterClass().getPrimaryAbility()) / Constant.MAX_DAMAGE_FROM_ABILITY_DIVIDER;
            }

            if (spellCaster instanceof NonPlayerCharacter nonPlayerCharacter) {
                valueIncreasedByPrimaryAbility = valueIncreasedByLevel + spellCaster.getCurrentAbilityValue(
                        nonPlayerCharacter.getNpcType().getPrimaryAbility()) / Constant.MAX_DAMAGE_FROM_ABILITY_DIVIDER;
            }
        }

        if (this instanceof ActionWithoutValue) {
            valueIncreasedByLevel = 0;
        }

        return new ActionValueRange(valueIncreasedByLevel, valueIncreasedByPrimaryAbility, valueIncreasedByLevel);
    }

    protected String returnTargetName(GameCharacter spellCaster, GameCharacter spellTarget) {
        if (this.actionEffectOn.equals(ActionEffectOn.SPELL_CASTER)) {
            return spellCaster.getName();
        } else {
            return spellTarget.getName();
        }
    }
}
