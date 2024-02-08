package kuchtastefan.gameSettings;

import lombok.Getter;
import lombok.Setter;

public class GameSettings {
    @Getter
    private static boolean printStringSlowly = false;
    @Getter
    private static boolean showInformationAboutActionName = false;
    @Getter
    private static boolean showSpellsOnCoolDown = true;

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
