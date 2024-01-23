package kuchtastefan.spell.warriorSpell;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.Action;
import kuchtastefan.spell.Spell;

import java.util.List;
import java.util.Map;

public class WarriorSpell extends Spell {

    public WarriorSpell(String spellName, String spellDescription, List<Action> spellActions, Map<Ability, Integer> bonusValueFromAbility, int spellLevel) {
        super(spellName, spellDescription, spellActions, bonusValueFromAbility, spellLevel);
    }
}
