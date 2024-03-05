package kuchtastefan.gameSettings;

import java.util.HashMap;
import java.util.Map;


public class GameSettingsService {

    public static Map<GameSetting, Boolean> gameSettings = new HashMap<>();

    public static void setTrueOrFalse(GameSetting gameSetting) {
        if (gameSettings.get(gameSetting)) {
            gameSettings.put(gameSetting, false);
        } else {
            gameSettings.put(gameSetting, true);
        }
    }

    public static void initializeGameSettings() {
        gameSettings.put(GameSetting.PRINT_STRING_SLOWLY, false);
        gameSettings.put(GameSetting.HIDE_SPELLS_ON_COOL_DOWN, false);
        gameSettings.put(GameSetting.SHOW_INFORMATION_ABOUT_ACTION_NAME, false);
    }
}
