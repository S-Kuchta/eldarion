package kuchtastefan.actions.actionValue;

import kuchtastefan.character.GameCharacter;

public interface ActionWithBaseValue extends ActionValue {

    @Override
    default ActionValueRange actionValue(GameCharacter spellCaster, int baseValue) {
        return new ActionValueRange(baseValue, baseValue);
    }
}
