package kuchtastefan.utility.printUtil;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.quest.Quest;
import kuchtastefan.quest.QuestStatus;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.QuestObjectiveDB;
import kuchtastefan.utility.ConsoleColor;

public class QuestPrint {

    public static void printCompleteQuestText(String questName) {
        System.out.println("\t" + "You have completed Quest " + ConsoleColor.YELLOW + questName + ConsoleColor.RESET);
    }

    public static void printQuestDetails(Quest quest, Hero hero) {
        PrintUtil.printLongDivider();
        System.out.println("\t" + ConsoleColor.MAGENTA + quest.getTitle() + ConsoleColor.RESET);
        PrintUtil.printTextWrap(quest.getDescription());

        if (quest.getStatus() != QuestStatus.TURNED_IN) {
            System.out.println("\n\tQuest Objective(s):");
            for (QuestObjective questObjective : QuestObjectiveDB.getQuestObjectiveListByIds(quest.getObjectivesIds())) {
                questObjective.printQuestObjectiveProgress(hero);
            }
        } else {
            System.out.println("\n\tQuest is completed!");
        }

        PrintUtil.printLongDivider();
    }
}
