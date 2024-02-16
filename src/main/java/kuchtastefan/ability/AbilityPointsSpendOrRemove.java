package kuchtastefan.ability;

import org.apache.commons.lang3.StringUtils;

public enum AbilityPointsSpendOrRemove {
    SPEND,
    REMOVE;

    public String toString() {
        return StringUtils.capitalize(name().toLowerCase().replace("_", " "));
    }
}
