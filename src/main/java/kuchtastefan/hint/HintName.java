package kuchtastefan.hint;

import org.apache.commons.lang3.StringUtils;

public enum HintName {
    BLACKSMITH_HINT;

    @Override
    public String toString() {
        return StringUtils.capitalize(name().toLowerCase().replace("_", " "));
    }
}
