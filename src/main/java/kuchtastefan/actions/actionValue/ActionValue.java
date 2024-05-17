package kuchtastefan.actions.actionValue;

import kuchtastefan.character.GameCharacter;

public interface ActionValue {

    ActionValueRange actionValue(GameCharacter spellCaster, int baseValue);
}
