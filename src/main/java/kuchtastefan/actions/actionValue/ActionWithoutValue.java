package kuchtastefan.actions.actionValue;

import kuchtastefan.character.GameCharacter;

public interface ActionWithoutValue extends ActionValue {

    @Override
    default ActionValueRange actionValue(GameCharacter spellCaster, int baseValue) {
        return new ActionValueRange(0, 0);
    }
}
