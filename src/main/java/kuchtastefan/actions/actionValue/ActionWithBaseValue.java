package kuchtastefan.actions.actionValue;

import kuchtastefan.character.GameCharacter;

public interface ActionWithBaseValue extends ActionValue {

    @Override
    default ActionValueRange actionValue(GameCharacter spellCaster, int valueIncreasedByLevel) {
        return new ActionValueRange(valueIncreasedByLevel, valueIncreasedByLevel);
    }
}
