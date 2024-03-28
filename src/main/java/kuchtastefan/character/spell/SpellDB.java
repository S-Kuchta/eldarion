package kuchtastefan.character.spell;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpellDB {


    public static final List<Spell> SPELL_LIST = new ArrayList<>();

    private static final Map<Integer, Spell> SPELL_DB = new HashMap<>();

    public static void addSpellToDB(Spell spell) {
        spell.setCanSpellBeCasted(true);
        spell.setCurrentTurnCoolDown(spell.getTurnCoolDown() + 1);

        SPELL_DB.put(spell.getSpellId(), spell);
    }

    public static Spell returnSpell(int spellId) {
        return SPELL_DB.get(spellId);
    }
}
