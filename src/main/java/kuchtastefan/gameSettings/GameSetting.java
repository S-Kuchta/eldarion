package kuchtastefan.gameSettings;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum GameSetting {
    PRINT_STRING_SLOWLY(false),
    HIDE_SPELLS_ON_COOL_DOWN(true),
    SHOW_INFORMATION_ABOUT_ACTION_NAME(false),
    AUTO_SAVE(true);

    private final Boolean InitialValue;

    GameSetting(Boolean InitialValue) {
        this.InitialValue = InitialValue;
    }

    @Override
    public String toString() {
        return StringUtils.capitalize(name().toLowerCase().replace("_", " "));
    }
}
