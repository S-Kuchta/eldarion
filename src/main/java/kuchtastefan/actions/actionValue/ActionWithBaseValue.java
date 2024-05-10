package kuchtastefan.actions.actionValue;

import kuchtastefan.character.GameCharacter;

public interface ActionWithBaseValue extends ActionValue {

    @Override
    default int actionValue(GameCharacter spellCaster, int valueIncreasedByLevel) {
        return valueIncreasedByLevel;
    }
}
