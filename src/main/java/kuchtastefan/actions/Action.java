package kuchtastefan.actions;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.actionValue.ActionValue;
import kuchtastefan.actions.actionValue.ActionValueRange;
import kuchtastefan.actions.actionsWIthDuration.specificActionWithDuration.ActionDealDamageOverTime;
import kuchtastefan.actions.criticalHit.CanBeCriticalHit;
import kuchtastefan.actions.instantAction.ActionDealDamage;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.spell.CharactersInvolvedInBattle;
import kuchtastefan.constant.Constant;
import kuchtastefan.utility.RandomNumberGenerator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Action implements ActionValue {

    protected ActionName actionName;
    protected final int baseActionValue;
    protected int currentActionValue;
    protected final ActionEffectOn actionEffectOn;
    protected final int chanceToPerformAction;
    protected CharactersInvolvedInBattle charactersInvolvedInBattle;


    public Action(ActionName actionName, ActionEffectOn actionEffectOn, int baseActionValue, int chanceToPerformAction) {
        this.actionName = actionName;
        this.baseActionValue = baseActionValue;
        this.currentActionValue = baseActionValue;
        this.actionEffectOn = actionEffectOn;
        this.chanceToPerformAction = chanceToPerformAction;
    }

    public abstract void performAction(GameCharacter spellCaster, GameCharacter spellTarget);

    public abstract void printActionDescription(GameCharacter spellCaster, GameCharacter spellTarget);

    public abstract int returnPriorityPoints(GameCharacter spellCaster, GameCharacter spellTarget);

    public boolean willPerformAction() {
        return RandomNumberGenerator.getRandomNumber(0, 100) <= this.chanceToPerformAction;
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
        int valueIncreasedByLevel = this.baseActionValue + (spellCaster.getLevel() * Constant.BONUS_VALUE_PER_LEVEL);

        if (this instanceof ActionDealDamage || this instanceof ActionDealDamageOverTime) {
            valueIncreasedByLevel += spellCaster.getEffectiveAbilityValue(Ability.ATTACK);
        }

        return this.actionValue(spellCaster, valueIncreasedByLevel);
    }

    public int returnFinalValue(GameCharacter spellCaster) {
        int value = RandomNumberGenerator.getRandomNumber(this.returnActionValueRange(spellCaster).minimumValue(),
                this.returnActionValueRange(spellCaster).maximumValue());

        boolean criticalHit = RandomNumberGenerator.getRandomNumber(1, 100) <= spellCaster.getEffectiveAbilityValue(Ability.CRITICAL_HIT_CHANCE);
        if (this instanceof CanBeCriticalHit && criticalHit) {
            value *= Constant.CRITICAL_HIT_MULTIPLIER;
        }

        return value;
    }

    protected String returnTargetName(GameCharacter spellCaster, GameCharacter spellTarget) {
        if (this.actionEffectOn.equals(ActionEffectOn.SPELL_CASTER)) {
            return spellCaster.getName();
        } else {
            return spellTarget.getName();
        }
    }
}
