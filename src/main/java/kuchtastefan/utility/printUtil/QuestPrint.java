package kuchtastefan.utility.printUtil;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.constant.ConstantSymbol;
import kuchtastefan.quest.Quest;
import kuchtastefan.quest.QuestStatus;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.utility.ConsoleColor;

public class QuestPrint {

    public static void printCompleteQuestText(String questName) {
        System.out.println("\t" + ConstantSymbol.QUEST_SYMBOL
                + " You have completed Quest " + ConsoleColor.YELLOW + questName + ConsoleColor.RESET + " "
                + ConstantSymbol.QUEST_SYMBOL);
    }

    public static void printQuestDetails(Quest quest, Hero hero) {
        PrintUtil.printLongDivider();
        System.out.println("\t" + ConsoleColor.MAGENTA + quest.getTitle() + ConsoleColor.RESET);
        PrintUtil.printTextWrap(quest.getDescription());

        System.out.println("\n\tQuest Objective(s):");
        for (QuestObjective questObjective : quest.getObjectives().values()) {
            questObjective.printQuestObjectiveProgress(hero);
        }

        PrintUtil.printLongDivider();
    }

    public static String returnQuestSuffix(Quest quest) {
        switch (quest.getStatus()) {
            case QuestStatus.UNAVAILABLE -> {
                return "-" + ConsoleColor.WHITE + "!" + ConsoleColor.RESET + "-";
            }
            case QuestStatus.AVAILABLE -> {
                return "-" + ConsoleColor.YELLOW_BOLD_BRIGHT + "!" + ConsoleColor.RESET + "-";
            }
            case QuestStatus.COMPLETED -> {
                return "-" + ConsoleColor.YELLOW_BOLD_BRIGHT + "?" + ConsoleColor.RESET + "-";
            }
            case QuestStatus.TURNED_IN -> {
                return " -- Completed --";
            }
            default -> {
                return "";
            }
        }
    }


}
