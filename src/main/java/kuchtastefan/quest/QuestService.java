package kuchtastefan.quest;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

import java.util.List;

public class QuestService {

    public void questGiverMenu(Hero hero, List<Quest> quests, String name) {
        PrintUtil.printDivider();
        System.out.println("\t\t\t" + name);
        PrintUtil.printDivider();

        printQuestsMenu(hero, quests);
        while (true) {
            try {
                int choice = InputUtil.intScanner();
                if (choice == 0) {
                    break;
                }

                if (canBeQuestAccepted(quests.get(choice - 1), hero)) {
                    this.selectedQuestForQuestGiverMenu(quests.get(choice - 1), hero, quests, name);
                }

                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("\tEnter valid input");
            }
        }
    }

    public void heroAcceptedQuestMenu(Hero hero, List<Quest> quests) {
        PrintUtil.printLongDivider();
        System.out.println("\t-- Quest Details --");
        PrintUtil.printLongDivider();

        while (true) {
            try {
                PrintUtil.printLongDivider();
                printQuestsMenu(hero, quests);
                int choice = InputUtil.intScanner();
                if (choice == 0) {
                    break;
                }

                if (!quests.get(choice - 1).isTurnedIn()) {
                    printQuestDetails(quests.get(choice - 1), hero);
                } else {
                    System.out.println("\t-- Quest " + quests.get(choice - 1).getQuestName() + " Completed --");
                }

            } catch (IndexOutOfBoundsException e) {
                System.out.println("\tEnter valid input");
            }
        }
    }

    private void printQuestsMenu(Hero hero, List<Quest> quests) {
        PrintUtil.printIndexAndText("0", "Go back");
        System.out.println();

        int index = 1;
        for (Quest quest : quests) {
//            if ((quest.getQuestLevel() - 1) <= hero.getLevel()) {
            if (canBeQuestAccepted(quest, hero)) {

                PrintUtil.printIndexAndText(String.valueOf(index),
                        quest.getQuestName() + " (recommended level: " + quest.getQuestLevel() + ")");
                if (quest.isTurnedIn()) {
                    System.out.print(" -- Completed --");
                } else if (!hero.getListOfAcceptedQuests().contains(quest)) {
                    System.out.print(" - " + ConsoleColor.YELLOW + "!" + ConsoleColor.RESET + " - ");
                } else if (hero.getListOfAcceptedQuests().contains(quest) && quest.isQuestCompleted()) {
                    System.out.print(" - " + ConsoleColor.YELLOW + "?" + ConsoleColor.RESET + " - ");
                }
            } else {
                PrintUtil.printIndexAndText(String.valueOf(index),
                        quest.getQuestName() + " (recommended level: " + quest.getQuestLevel() + ")");
                System.out.print(ConsoleColor.WHITE + " - ! - " + ConsoleColor.RESET);
            }

            index++;
            System.out.println();
        }
    }

    /**
     * Method is responsible for Accepting or Completing selected quest.
     * If hero does not contain selected quest you can to take it
     * If you have quest completed but not turned it yet, you can turn in
     *
     * @param quest  quest which come dynamically depending on you choice
     * @param quests only needed for switching between menus
     * @param name   same as quests
     */
    private void selectedQuestForQuestGiverMenu(Quest quest, Hero hero, List<Quest> quests, String name) {

        if (!quest.isTurnedIn()) {
            this.printQuestDetails(quest, hero);
            PrintUtil.printIndexAndText("0", "Go back");
            System.out.println();
            if (!quest.isQuestCompleted() && !hero.getListOfAcceptedQuests().contains(quest)) {
                PrintUtil.printIndexAndText("1", "Accept quest");
                System.out.println();
            } else if (!quest.isTurnedIn() && quest.isQuestCompleted()) {
                PrintUtil.printIndexAndText("1", "Complete quest");
                System.out.println();
            }
        } else {
            System.out.println("\t-- Quest " + quest.getQuestName() + " Completed --");
            PrintUtil.printIndexAndText("0", "Go back");
            System.out.println();
        }

        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> this.questGiverMenu(hero, quests, name);
            case 1 -> {
                if (!quest.isQuestCompleted() && !hero.getListOfAcceptedQuests().contains(quest)) {
                    System.out.println("\t\t --> Quest accepted <--");
                    this.startTheQuest(quest, hero);
                    this.questGiverMenu(hero, quests, name);
                } else if (!quest.isTurnedIn() && quest.isQuestCompleted()) {
                    this.turnInTheQuest(quest, hero);
                    this.questGiverMenu(hero, quests, name);
                }
            }
            default -> System.out.println("\tEnter valid input");
        }
    }

    private void startTheQuest(Quest quest, Hero hero) {
        if (!hero.getListOfAcceptedQuests().contains(quest)) {
            hero.getListOfAcceptedQuests().add(quest);
            hero.checkIfQuestObjectivesAndQuestIsCompleted();
        }
    }

    /**
     * Turn in the quest, give quest reward to hero and remove items/killed enemy etc. from hero
     *
     * @param quest quest to turn in
     */
    private void turnInTheQuest(Quest quest, Hero hero) {
        quest.turnInTheQuestAndGiveReward(hero);
        quest.setTurnedIn(true);
        for (QuestObjective questObjective : quest.getQuestObjectives()) {
            questObjective.removeCompletedItemsOrEnemies(hero);
        }
    }

    private void printQuestDetails(Quest quest, Hero hero) {
        PrintUtil.printLongDivider();
        System.out.println("\t\t\t\t------ " + quest.getQuestName() + " ------");
        System.out.print("\t");
        PrintUtil.printTextWrap(quest.getQuestDescription());
        System.out.println();
        for (QuestObjective questObjective : quest.getQuestObjectives()) {
            questObjective.printQuestObjectiveAssignment(hero);
        }
    }

    private boolean canBeQuestAccepted(Quest quest, Hero hero) {

        if (quest instanceof QuestChain) {
            if (((QuestChain) quest).canBeQuestAccepted(hero)) {
                return true;
            } else {
                System.out.println("\tQuest can not be accepted yet!");
                return false;
            }
        }

        if ((quest.getQuestLevel() - 1) <= hero.getLevel()) {
            return true;
        } else {
            System.out.println("\tYou do not meet minimal level requirements for this Quest!");
            return false;
        }
    }
}
