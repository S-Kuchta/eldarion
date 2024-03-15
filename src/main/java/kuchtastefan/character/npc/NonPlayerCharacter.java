package kuchtastefan.character.npc;

import kuchtastefan.ability.Ability;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.spell.SpellDB;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class NonPlayerCharacter extends GameCharacter {

    protected int npcId;
    protected int[] npcCharacterSpellsId;
    protected boolean defeated;
    protected CharacterRarity characterRarity;
    protected CharacterType characterType;

    public NonPlayerCharacter(String name, Map<Ability, Integer> abilities, int[] npcCharacterSpellsId, CharacterType characterType) {
        super(name, abilities);
        this.characterType = characterType;
        this.npcCharacterSpellsId = npcCharacterSpellsId;
        this.defeated = false;
    }

    public void convertSpellIdToSpellList() {
        for (int enemySpell : this.npcCharacterSpellsId) {
            this.characterSpellList.add(SpellDB.SPELL_DB.get(enemySpell));
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

    public void increaseAbilityPointsByMultiplier(double multiplier) {
        for (Ability ability : Ability.values()) {
            if (this.getAbilities().containsKey(ability)) {
                this.getAbilities().put(ability, (int) (this.getAbilities().get(ability) * multiplier));
            }
        }
    }

    public void increaseAbilityPointsByAddition(double addition) {
        for (Ability ability : Ability.values()) {
            if (this.getAbilities().containsKey(ability)) {
                this.getAbilities().put(ability, (int) (this.getAbilities().get(ability) * addition));
            }
        }
    }
}
