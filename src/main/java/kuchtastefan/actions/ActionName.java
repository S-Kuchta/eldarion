package kuchtastefan.actions;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum ActionName {
    SKIP(""),
    DAMAGE("Deal instant damage."),
    DAMAGE_OVER_TIME("Damage Over Time. The damage is dealt in every turn the action lasts."),
    HEAL("Instant heal. Instant heal the target."),
    HEAL_OVER_TIME("Heal Over Time. Healths are restored every turn the action lasts."),
    RESTORE_MANA_OVER_TIME("Restore mana over Time. It restore mana per one turn."),
    RESTORE_MANA("Restore mana."),
    LOSE_MANA("Lose mana."),
    INCREASE_ABILITY_POINTS("Increase Ability Points. It increases the ability points for the duration of the action."),
    DECREASE_ABILITY_POINTS("Decrease Ability Points. It decrease the ability points for the duration of the action."),
    ABSORB_DAMAGE("Absorb Damage Taken. It absorbs damage up to the specified value. The action lasts for a certain number of rounds."),
    INVULNERABILITY("Invulnerability. It makes the player character invulnerable for a certain number of rounds. It is not dependent on the damage received."),
    STUN("Stun. Target of action will not be able to perform any action. Battle turn of stunned character will be skipped."),
    REFLECT_SPELL("Reflect spell. Reflect next casted spell back to caster."),
    REMOVE_BUFF("Remove buff. Remove buff from target of the spell."),
    REMOVE_DEBUFF("Remove debuff. Remove buff from target of the spell."),
    DRAIN_MANA("Drain mana. Burn Mana from target."),
    SUMMON_CREATURE("Summon Creature. Summon a creature that will fight on your side.");

    private final String description;

    ActionName(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return StringUtils.capitalize(name().toLowerCase().replace("_", " "));
    }
}
