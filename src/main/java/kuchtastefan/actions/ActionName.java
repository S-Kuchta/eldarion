package kuchtastefan.actions;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum ActionName {
    DAMAGE("Deal instant damage."),
    DAMAGE_OVER_TIME("Damage Over Time. The damage is dealt in every turn the action lasts."),
    HEAL("Instant heal. Instant heal the target."),
    HEAL_OVER_TIME("Heal Over Time. Healths are restored every turn the action lasts."),
    INCREASE_ABILITY_POINTS("Increase Ability Point. It increases the ability points for the duration of the action."),
    ABSORB_DAMAGE("Absorb Damage Taken. It absorbs damage up to the specified value. The action lasts for a certain number of rounds."),
    INVULNERABILITY("Invulnerability. It makes the player character invulnerable for a certain number of rounds. It is not dependent on the damage received.");

    private final String description;

    ActionName(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return StringUtils.capitalize(name().toLowerCase().replace("_", " "));
    }
}
