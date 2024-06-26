package kuchtastefan.utility.printUtil;

import kuchtastefan.gameSettings.GameSetting;
import kuchtastefan.gameSettings.GameSettingsDB;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.LetterToNumber;

public class PrintUtil {

    public static void printStringSlowly(String s) {
        char[] stringToCharArr = s.toCharArray();

        if (!GameSettingsDB.returnGameSettingValue(GameSetting.PRINT_STRING_SLOWLY)) {
            for (char c : stringToCharArr) {
                System.out.print(c);
            }
        } else {
            for (char c : stringToCharArr) {
                System.out.print(c);
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        System.out.println();
    }

    public static void printTextWrap(String text) {
        StringBuilder currentLine = new StringBuilder();
        for (String word : text.split("\\s")) {
            if (currentLine.length() + word.length() <= 80) {
                currentLine.append(word).append(" ");
            } else {
                PrintUtil.printStringSlowly("\t" + currentLine.toString().trim());
                currentLine.setLength(0);
                currentLine.append(word).append(" ");
            }
        }

        if (!currentLine.isEmpty()) {
            PrintUtil.printStringSlowly("\t" + currentLine.toString().trim());
        }
    }

    public static void printWhiteLine(int numberOfSpaces) {
        for (int i = 0; i < numberOfSpaces; i++) {
            System.out.print(ConsoleColor.WHITE_UNDERLINED + "\t" + ConsoleColor.RESET);
        }
        System.out.println();
    }

    public static void printDivider() {
        printWhiteLine(15);
    }

    public static void printLongDivider() {
        printWhiteLine(22);
    }

    public static void printExtraLongDivider() {
        printWhiteLine(30);
    }

    public static void printEnterValidInput() {
        System.out.println(ConsoleColor.RED + "\tEnter valid input" + ConsoleColor.RESET);
    }

    public static void printIndexAndText(String index, String text) {
        System.out.print(ConsoleColor.CYAN + "\t" + index + ". " + ConsoleColor.RESET + text);
    }

    public static void printMenuHeader(String header) {
        System.out.println(ConsoleColor.YELLOW_UNDERLINED + "\t\t\t\t\t" + ConsoleColor.YELLOW + header + ConsoleColor.YELLOW_UNDERLINED + "\t\t\t\t\t" + ConsoleColor.RESET);
    }

    public static void printMenuOptions(String... options) {
        for (int i = 0; i < options.length; i++) {
            PrintUtil.printIndexAndText(String.valueOf(i), options[i]);
            System.out.println();
        }
    }
}






