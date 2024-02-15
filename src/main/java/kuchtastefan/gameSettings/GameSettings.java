package kuchtastefan.gameSettings;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class GameSettings {
    @Getter
    private static boolean printStringSlowly = false;
    @Getter
    private static boolean showInformationAboutActionName = false;
    @Getter
    private static boolean hideSpellsOnCoolDown = true;
    @Getter
    private static List<Boolean> gameSettingsList = new ArrayList<>();

    public static void setPrintStringSlowly() {
        printStringSlowly = !printStringSlowly;
    }

    public static void setShowInformationAboutActionName() {
        showInformationAboutActionName = !showInformationAboutActionName;
    }

    public static void setHideSpellsOnCoolDown() {
        hideSpellsOnCoolDown = !hideSpellsOnCoolDown;
    }

    public static void addGameSettingsToGameSettingsList() {
        gameSettingsList.add(printStringSlowly);
        gameSettingsList.add(showInformationAboutActionName);
        gameSettingsList.add(hideSpellsOnCoolDown);
    }
}
