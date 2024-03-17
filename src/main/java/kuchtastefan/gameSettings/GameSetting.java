package kuchtastefan.gameSettings;

import org.apache.commons.lang3.StringUtils;

public enum GameSetting {
    PRINT_STRING_SLOWLY,
    HIDE_SPELLS_ON_COOL_DOWN,
    SHOW_INFORMATION_ABOUT_ACTION_NAME,
    AUTO_SAVE;

    @Override
    public String toString() {
        return StringUtils.capitalize(name().toLowerCase().replace("_", " "));
    }
}
