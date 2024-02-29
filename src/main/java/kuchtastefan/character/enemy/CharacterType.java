package kuchtastefan.character.enemy;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum CharacterType {

    HUMANOID("Humanoid creatures, bearing semblance to humans, vary in appearance and temperament. Some are noble and wise, while others, shrouded in mystery, wield arcane powers or possess unmatched strength"),
    BEAST("Beasts, creatures of primal instinct, roam untamed lands. From majestic dragons ruling the skies to feral wolves prowling the forests, these creatures embody raw power and untamed ferocity"),
    UNDEAD("Undead beings, once mortal, now wander in eternal unrest. Embraced by death's grasp, these spectral figures shroud themselves in darkness, driven by an unending hunger for souls"),
    ELEMENTAL("Elemental character harnesses the power of natural elements like fire, water, earth, and air to cast devastating spells and manipulate the environment");

    private final String description;

    CharacterType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return StringUtils.capitalize(name().toLowerCase().replace("_", " "));
    }

}
