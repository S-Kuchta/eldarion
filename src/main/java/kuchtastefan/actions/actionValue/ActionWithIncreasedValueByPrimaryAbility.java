package kuchtastefan.actions.actionValue;

import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.npc.NonPlayerCharacter;
import kuchtastefan.constant.Constant;

public interface ActionWithIncreasedValueByPrimaryAbility extends ActionValue {

    @Override
    default int actionValue(GameCharacter spellCaster, int valueIncreasedByLevel) {
        if (spellCaster instanceof Hero hero) {
            return valueIncreasedByLevel + spellCaster.getEffectiveAbilityValue(hero.getCharacterClass().getPrimaryAbility()) / Constant.MAX_DAMAGE_FROM_ABILITY_DIVIDER;
        }

        if (spellCaster instanceof NonPlayerCharacter nonPlayerCharacter) {
            return valueIncreasedByLevel + spellCaster.getEffectiveAbilityValue(nonPlayerCharacter.getNpcType().getPrimaryAbility()) / Constant.MAX_DAMAGE_FROM_ABILITY_DIVIDER;
        }

        return 0;
    }
}
