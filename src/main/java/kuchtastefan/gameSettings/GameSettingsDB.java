package kuchtastefan.gameSettings;

import com.fasterxml.jackson.databind.ser.impl.MapEntrySerializer;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;


public class GameSettingsDB {

    @Getter
    private static final Map<GameSetting, Boolean> GAME_SETTINGS_DB = new HashMap<>();

    public static void setTrueOrFalse(GameSetting gameSetting) {
        if (GAME_SETTINGS_DB.get(gameSetting)) {
            GAME_SETTINGS_DB.put(gameSetting, false);
        } else {
            GAME_SETTINGS_DB.put(gameSetting, true);
        }
    }

    public static void initializeGameSettings() {
        for (GameSetting gameSetting : GameSetting.values()) {
            GAME_SETTINGS_DB.put(gameSetting, gameSetting.getInitialValue());
        }
    }

    public static boolean returnGameSettingValue(GameSetting gameSetting) {
        return GAME_SETTINGS_DB.get(gameSetting);
    }
}
