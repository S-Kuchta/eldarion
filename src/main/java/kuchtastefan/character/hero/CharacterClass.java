package kuchtastefan.character.hero;

import org.apache.commons.lang3.StringUtils;

public enum CharacterClass {
    NPC,
    WARRIOR,
    WARLOCK,
    SHAMAN,
    MAGE;

    public String toString() {
        return StringUtils.capitalize(name().toLowerCase().replace("_", " "));
    }
}
