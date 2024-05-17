package kuchtastefan.actions.actionValue;

import kuchtastefan.ability.Ability;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.npc.NonPlayerCharacter;
import kuchtastefan.constant.Constant;

public interface ActionWithIncreasedValueByPrimaryAbility extends ActionValue {

    @Override
    default ActionValueRange actionValue(GameCharacter spellCaster, int baseValue) {
        Ability primaryAbility = null;
        if (spellCaster instanceof Hero hero) {
            primaryAbility = hero.getCharacterClass().getPrimaryAbility();
        }

        if (spellCaster instanceof NonPlayerCharacter nonPlayerCharacter) {
            primaryAbility = nonPlayerCharacter.getNpcType().getPrimaryAbility();
        }

        int value = baseValue + spellCaster.getEffectiveAbilityValue(primaryAbility) / Constant.MAX_DAMAGE_FROM_ABILITY_DIVIDER;
        return new ActionValueRange((int) (value * 0.75), value);
    }
}
