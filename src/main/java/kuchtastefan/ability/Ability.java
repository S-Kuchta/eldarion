package kuchtastefan.ability;

import kuchtastefan.constant.Constant;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum Ability {
    ATTACK("Attack is the ability to deal damage. Every 5 points of attack increase you 1 point of critical hit chance. Final damage is also affected by Strength or Intellect."),
    RESIST_DAMAGE("Resist damage is the ability to reduce incoming damage."),
    STRENGTH("Strength is ability mostly used by warriors. One point of Strength also increase you 2 points of Health. On strength depends damage of some your spells."),
    INTELLECT("Intellect is important mostly by mages. One point of Intellect also increase you 2 points of Mana Is responsible for value of some spells."),
    HASTE("Haste is used for chance to hit target, restore mana during combat and also out of the combat and restore health when hero is out of combat."),
    CRITICAL_HIT_CHANCE("Critical hit chance is responsible for chance of critical hit. One point of Critical hit chance is equals to 1% chance."),
    HEALTH("Health is the amount of damage you can take before you die."),
    MANA("Mana is used for casting spells. After every turn you will restore" + Constant.RESTORE_MANA_PER_ONE_HASTE + " points of mana per 1 point of Haste."),
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
