package kuchtastefan.gameSettings;

public class GameSettings {
    private static boolean printStringSlowly = true;

    public static void setPrintStringSlowly() {
        printStringSlowly = !printStringSlowly;
    }

    public static boolean isPrintStringSlowly() {
        return printStringSlowly;
    }
}
