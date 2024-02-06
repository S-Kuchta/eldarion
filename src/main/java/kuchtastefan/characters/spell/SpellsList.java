package kuchtastefan.characters.spell;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpellsList {

    @Getter
    private static final List<Spell> spellList = new ArrayList<>();
    @Getter
    private static final Map<Integer, Spell> spellMap = new HashMap<>();
}
