package kuchtastefan.character;

import kuchtastefan.ability.Ability;
import kuchtastefan.character.spell.SpellsList;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class NpcCharacter extends GameCharacter {

    protected int[] npcCharacterSpellsId;
    protected boolean defeated;
    protected CharacterRarity characterRarity;

    public NpcCharacter(String name, Map<Ability, Integer> abilities, int[] npcCharacterSpellsId) {
        super(name, abilities);
        this.npcCharacterSpellsId = npcCharacterSpellsId;
        this.defeated = false;
    }

    public void convertSpellIdToSpellList() {
        for (int enemySpell : this.npcCharacterSpellsId) {
            this.characterSpellList.add(SpellsList.spellMap.get(enemySpell));
        }
    }

    public void setMaxAbilitiesAndCurrentAbilities() {
        this.currentAbilities = new HashMap<>();
        this.maxAbilities = new HashMap<>();

        for (Ability ability : Ability.values()) {
            this.currentAbilities.putIfAbsent(ability, this.abilities.get(ability));
            this.maxAbilities.putIfAbsent(ability, this.abilities.get(ability));
        }
    }
}
