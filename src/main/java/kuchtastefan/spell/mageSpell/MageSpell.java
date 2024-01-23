package kuchtastefan.spell.mageSpell;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.Action;
import kuchtastefan.spell.Spell;

import java.util.List;
import java.util.Map;

public class MageSpell extends Spell {

    public MageSpell(String spellName, String spellDescription, List<Action> spellActions, Map<Ability, Integer> bonusValueFromAbility, int spellLevel) {
        super(spellName, spellDescription, spellActions, bonusValueFromAbility, spellLevel);
    }
}
