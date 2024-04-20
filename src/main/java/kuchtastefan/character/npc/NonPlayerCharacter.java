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
    protected NpcType npcType;

    public NonPlayerCharacter(String name, Map<Ability, Integer> abilities, int[] npcCharacterSpellsId, CharacterType characterType) {
        super(name, abilities);
        this.characterType = characterType;
        this.npcCharacterSpellsId = npcCharacterSpellsId;
        this.defeated = false;
    }

    public void convertSpellIdToSpellList() {
        for (int spellId : this.npcCharacterSpellsId) {
            this.characterSpellList.add(SpellDB.returnSpell(spellId));
        }
    }

    public void setMaxAbilitiesAndCurrentAbilities() {
        this.effectiveAbilities = new HashMap<>();
        this.enhancedAbilities = new HashMap<>();

        for (Ability ability : Ability.values()) {
            this.effectiveAbilities.putIfAbsent(ability, this.baseAbilities.get(ability));
            this.enhancedAbilities.putIfAbsent(ability, this.baseAbilities.get(ability));
        }
    }

    public void increaseAbilityPointsByMultiplier(double multiplier) {
        for (Ability ability : Ability.values()) {
            if (this.getBaseAbilities().containsKey(ability)) {
                this.getBaseAbilities().put(ability, (int) (this.getBaseAbilities().get(ability) * multiplier));
            }
        }
    }

    public void increaseAbilityPointsByAddition(double addition) {
        for (Ability ability : Ability.values()) {
            if (this.getBaseAbilities().containsKey(ability)) {
                this.getBaseAbilities().put(ability, (int) (this.getBaseAbilities().get(ability) * addition));
            }
        }
    }
}
