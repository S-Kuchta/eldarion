package kuchtastefan.actions;

import org.apache.commons.lang3.StringUtils;

public enum ActionEffectOn {
    SPELL_CASTER,
    SPELL_TARGET;

    @Override
    public String toString() {
        return StringUtils.capitalize(name().toLowerCase().replace("_", " "));
    }
}
