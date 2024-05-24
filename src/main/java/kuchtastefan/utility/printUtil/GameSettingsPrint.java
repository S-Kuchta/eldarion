package kuchtastefan.utility.printUtil;

import kuchtastefan.gameSettings.GameSetting;
import kuchtastefan.gameSettings.GameSettingsDB;
import kuchtastefan.utility.ConsoleColor;

public class GameSettingsPrint {

    public static void printYesNoSelection(boolean gameSetting) {
        String yes = gameSetting ? "Yes" : ConsoleColor.WHITE + "Yes" + ConsoleColor.RESET;
        String no = gameSetting ? ConsoleColor.WHITE + "No" + ConsoleColor.RESET : "No";
        System.out.print(yes + " / " + no);
    }

    public static void printSpellGameSettings() {
        System.out.println();
        PrintUtil.printIndexAndText("X", "Hide action description - ");
        printYesNoSelection(GameSettingsDB.returnGameSettingValue(GameSetting.SHOW_INFORMATION_ABOUT_ACTION_NAME));

        System.out.print("\t");
        PrintUtil.printIndexAndText("Y", "Hide spells on CoolDown - ");
        printYesNoSelection(GameSettingsDB.returnGameSettingValue(GameSetting.HIDE_SPELLS_ON_COOL_DOWN));
    }

}
