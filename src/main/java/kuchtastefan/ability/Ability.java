package kuchtastefan.ability;

import kuchtastefan.constant.Constant;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum Ability {
    ATTACK("Attack is the ability to deal damage. Every 5 points of attack increase you 1 point of critical hit chance. Final damage is also affected by Strength or Intellect."),
    RESIST_DAMAGE("Resist damage is the ability to reduce incoming damage."),
    STRENGTH("Strength increase Warrior damage. One point of Strength also increase you " + Constant.HEALTH_PER_POINT_OF_STRENGTH + " points of Health."),
    INTELLECT("Intellect Increase Mage and Warlock damage. One point of Intellect also increase you " + Constant.MANA_PER_POINT_OF_INTELLECT + " points of Mana."),
    HASTE("Haste is used for chance to hit target and to determine order attacking characters."),
    SPIRIT("Spirit is used for restore health and mana out of combat and mana during combat."),
    CRITICAL_HIT_CHANCE("Critical hit chance is responsible for chance of critical hit. One point of Critical hit chance is equals to 1% chance."),
    HEALTH("Health is the amount of damage you can take before you die."),
    MANA("Mana is used for casting spells. After every turn you will restore" + Constant.RESTORE_MANA_PER_ONE_SPIRIT + " points of mana per 1 point of Spirit."),
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
