package kuchtastefan.gameSettings;

import lombok.Getter;

public class GameSettings {
    @Getter
    private static boolean printStringSlowly = false;
    @Getter
    private static boolean showInformationAboutActionName = false;
    @Getter
    private static boolean hideSpellsOnCoolDown = true;

    public static void setPrintStringSlowly() {
        printStringSlowly = !printStringSlowly;
    }

    public static void setShowInformationAboutActionName() {
        showInformationAboutActionName = !showInformationAboutActionName;
    }

    public static void setHideSpellsOnCoolDown() {
        hideSpellsOnCoolDown = !hideSpellsOnCoolDown;
    }
}
