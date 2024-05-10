package kuchtastefan.actions.actionValue;

import kuchtastefan.character.GameCharacter;

public interface ActionWithoutValue extends ActionValue {

    @Override
    default int actionValue(GameCharacter spellCaster, int valueIncreasedByLevel) {
        return 0;
    }
}
