package kuchtastefan.ability;

import kuchtastefan.constant.Constant;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum Ability {
    ATTACK("Attack is the ability to deal damage. Final damage is also affected by Strength or Intellect."),
    RESIST_DAMAGE("Resist damage is the ability to reduce incoming damage."),
    STRENGTH("Strength is ability mostly used by warriors. On strength depends damage of some your spells."),
    INTELLECT("Intellect is important mostly by mages. Is responsible for value of some spells and mana regeneration."),
    HASTE("Haste is used for frequency of attacks or used by some spells."),
    CRITICAL_HIT_CHANCE("Critical hit chance is responsible for chance of critical hit."),
    HEALTH("Health is the amount of damage you can take before you die."),
    MANA("Mana is used for casting spells. After every turn you will restore" + Constant.RESTORE_MANA_PER_ONE_INTELLECT + " points of mana per 1 point of Intellect."),
    ABSORB_DAMAGE("Absorb damage is used as a shield. Attacker must first destroy Absorb damage before target will take damage.");

    private final String description;

    Ability(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return StringUtils.capitalize(name().toLowerCase().replace("_", " "));
    }
}
