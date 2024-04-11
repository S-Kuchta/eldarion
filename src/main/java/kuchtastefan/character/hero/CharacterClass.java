package kuchtastefan.character.hero;

import kuchtastefan.ability.Ability;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum CharacterClass {
    NPC(Ability.ATTACK),
    WARRIOR(Ability.STRENGTH),
    WARLOCK(Ability.INTELLECT),
    SHAMAN(Ability.HASTE),
    MAGE(Ability.INTELLECT);

    private final Ability primaryAbility;

    CharacterClass(Ability primaryAbility) {
        this.primaryAbility = primaryAbility;
    }

    public String toString() {
        return StringUtils.capitalize(name().toLowerCase().replace("_", " "));
    }
}
