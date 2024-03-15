package kuchtastefan.gameSettings;

import java.util.HashMap;
import java.util.Map;


public class GameSettingsDB {

    public static final Map<GameSetting, Boolean> GAME_SETTINGS_DB = new HashMap<>();

    public static void setTrueOrFalse(GameSetting gameSetting) {
        if (GAME_SETTINGS_DB.get(gameSetting)) {
            GAME_SETTINGS_DB.put(gameSetting, false);
        } else {
            GAME_SETTINGS_DB.put(gameSetting, true);
        }
    }

    public static void initializeGameSettings() {
        GAME_SETTINGS_DB.put(GameSetting.PRINT_STRING_SLOWLY, false);
        GAME_SETTINGS_DB.put(GameSetting.HIDE_SPELLS_ON_COOL_DOWN, true);
        GAME_SETTINGS_DB.put(GameSetting.SHOW_INFORMATION_ABOUT_ACTION_NAME, false);
    }
}
