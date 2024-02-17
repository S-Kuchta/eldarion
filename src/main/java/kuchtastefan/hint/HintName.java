package kuchtastefan.hint;

import org.apache.commons.lang3.StringUtils;

public enum HintName {
    WELCOME,
    HERO_MENU,
    QUEST_HINT,
    REGION_HINT,
    LOCATION_HINT,
    NEW_SPELL_HINT,
    BATTLE_HINT,
    BLACKSMITH_HINT;

    @Override
    public String toString() {
        return StringUtils.capitalize(name().toLowerCase().replace("_", " "));
    }
}
