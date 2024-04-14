package kuchtastefan.character.npc;

import kuchtastefan.ability.Ability;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum NpcType {
    MAGE(Ability.INTELLECT),
    WARRIOR(Ability.STRENGTH),
    ROGUE(Ability.HASTE),
    DEFENDER(Ability.ABSORB_DAMAGE);

    private final Ability primaryAbility;

    NpcType(Ability primaryAbility) {
        this.primaryAbility = primaryAbility;
    }

    @Override
    public String toString() {
        return StringUtils.capitalize(name().toLowerCase().replace("_", " "));
    }
}
