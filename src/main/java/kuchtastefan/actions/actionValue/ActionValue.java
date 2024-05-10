package kuchtastefan.actions.actionValue;

import kuchtastefan.character.GameCharacter;

public interface ActionValue {

    int actionValue(GameCharacter spellCaster, int valueIncreasedByLevel);
}
