package kuchtastefan.gameSettings;

import lombok.Getter;
import lombok.Setter;

public class GameSettings {
    @Getter
    private static boolean printStringSlowly = false;
    @Getter
    private static boolean showInformationAboutActionName = true;
    @Getter
    private static boolean showSpellsOnCoolDown = false;

    public static void setPrintStringSlowly() {
        printStringSlowly = !printStringSlowly;
    }

    public static void setShowInformationAboutActionName() {
        showInformationAboutActionName = !showInformationAboutActionName;
    }

    public static void setShowSpellsOnCoolDown() {
        showSpellsOnCoolDown = !showSpellsOnCoolDown;
    }
}
