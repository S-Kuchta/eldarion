package kuchtastefan.service;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.quest.Quest;
import kuchtastefan.quest.QuestDB;
import kuchtastefan.quest.QuestStatus;
import kuchtastefan.quest.questGiver.QuestGiverCharacter;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.printUtil.PrintUtil;
import kuchtastefan.utility.printUtil.QuestPrint;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class QuestMenuService {

    private QuestGiverCharacter questGiverCharacter;


    public void questGiverMenu(Hero hero, List<Quest> quests) {
        while (true) {
            QuestDB.setInitialQuestsStatusFromGivenList(hero, quests);
            printQuestsMenu(quests);
            int choice = InputUtil.intScanner();
            if (choice == 0) {
                break;
            } else if (choice > 0 && choice <= quests.size()) {
                this.questMenu(quests.get(choice - 1), hero);
            } else {
                PrintUtil.printEnterValidInput();
            }
        }
    }

    public void heroAcceptedQuestMenu(Hero hero) {
        List<Quest> quests = QuestDB.getQuestListByIds(hero.getSaveGameEntities().getHeroQuests().getEntitiesIds());

        while (true) {
            printQuestsMenu(quests);
            int choice = InputUtil.intScanner();
            if (choice == 0) {
                break;
            } else if (choice > 0 && choice <= quests.size()) {
                QuestPrint.printQuestDetails(quests.get(choice - 1), hero);
            } else {
                PrintUtil.printEnterValidInput();
            }
        }
    }

    private void printQuestsMenu(List<Quest> quests) {
        String questGiverName = questGiverCharacter == null ? "Accepted" : "Available";
        System.out.println("\t" + questGiverName + " Quests:");

        PrintUtil.printIndexAndText("0", "Go back\n");
        int index = 1;
        for (Quest quest : quests) {
            PrintUtil.printIndexAndText(String.valueOf(index++), quest.getTitle() + " " + quest.getStatusIcon());
            System.out.println();
        }
    }

    /**
     * Method is responsible for Accepting or Completing selected quest.
     * If hero does not contain selected quest you can to take it
     * If you have quest completed but not turned it yet, you can turn in
     *
     * @param quest quest which come dynamically depending on you choice
     */
    private void questMenu(Quest quest, Hero hero) {
        QuestPrint.printQuestDetails(quest, hero);
        if (printQuestOptions(hero, quest)) {
            while (true) {
                int choice = InputUtil.intScanner();
                if (choice == 0) {
                    break;
                } else if (choice == 1) {
                    if (quest.getStatus().equals(QuestStatus.AVAILABLE)) {
                        quest.startTheQuest(hero);
                        System.out.println(ConsoleColor.YELLOW + "\tQuest Accepted\n" + ConsoleColor.RESET);
                    }

                    if (quest.getStatus().equals(QuestStatus.COMPLETED)) {
                        quest.turnInTheQuest(hero);
                        System.out.println(ConsoleColor.YELLOW + "\tQuest Completed\n" + ConsoleColor.RESET);
                    }

                    break;
                } else {
                    PrintUtil.printEnterValidInput();
                }
            }
        }
    }

    private boolean printQuestOptions(Hero hero, Quest quest) {
        switch (quest.getStatus()) {
            case QuestStatus.AVAILABLE -> {
                PrintUtil.printIndexAndText("0", "Go back");
                PrintUtil.printIndexAndText("1", "Accept quest\n");
                return true;
            }
            case QuestStatus.COMPLETED -> {
                PrintUtil.printIndexAndText("0", "Go back");
                PrintUtil.printIndexAndText("1", "Complete quest\n");
                return true;
            }
            case QuestStatus.ACCEPTED -> QuestPrint.printQuestDetails(quest, hero);
            case QuestStatus.TURNED_IN -> System.out.println("\tQuest " + quest.getTitle() + " is Completed");
            case QuestStatus.UNAVAILABLE ->
                    System.out.println(ConsoleColor.RED + "\tQuest can not be accepted yet." + ConsoleColor.RESET);
        }

        return false;
    }
}
