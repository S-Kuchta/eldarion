package kuchtastefan.gameSettings;

import lombok.Getter;
import lombok.Setter;

public class GameSettings {
    @Getter
    @Setter
    private static boolean printStringSlowly = true;

    public static void setPrintStringSlowly() {
        printStringSlowly = !printStringSlowly;
    }

}
