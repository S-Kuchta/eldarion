package kuchtastefan.gameSettings;

import lombok.Getter;
import lombok.Setter;

public class GameSettings {
    @Getter
    @Setter
    private static boolean printStringSlowly = false;
    @Getter
    private static boolean showInformationAboutActionName = true;

    public static void setPrintStringSlowly() {
        printStringSlowly = !printStringSlowly;
    }

    public static void setShowInformationAboutActionName() {
        showInformationAboutActionName = !showInformationAboutActionName;
    }
}
