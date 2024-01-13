package kuchtastefan.quest;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.quest.questObjectives.QuestObjective;
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
                this.selectedQuestForQuestGiverMenu(quests.get(choice - 1), hero, quests, name);
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
                printQuestDetails(quests.get(choice - 1), hero);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("\tEnter valid input");
            }
        }
    }

    private void printQuestsMenu(Hero hero, List<Quest> quests) {
        System.out.println("\t0. Go back");
        int index = 1;
        for (Quest quest : quests) {
            if ((quest.getQuestLevel() - 1) <= hero.getLevel()) {
                System.out.print("\t" + index + ". " + quest.getQuestName() + " (recommended level: " + quest.getQuestLevel() + ")");
                if (quest.isTurnedIn()) {
                    System.out.print(" -- Completed --");
                } else if (!hero.getListOfAcceptedQuests().contains(quest)) {
                    System.out.print(" - ! -");
                } else if (hero.getListOfAcceptedQuests().contains(quest) && quest.isCompleted() && !quest.isTurnedIn()) {
                    System.out.print(" - ? -");
                }
                index++;
                System.out.println();
            }
        }
    }

    private void selectedQuestForQuestGiverMenu(Quest quest, Hero hero, List<Quest> quests, String name) {

        if (!quest.isTurnedIn()) {
            this.printQuestDetails(quest, hero);
            System.out.println("\t0. Go back");
            if (!quest.isCompleted() && !hero.getListOfAcceptedQuests().contains(quest)) {
                System.out.println("\t1. Accept quest");
            } else if (!quest.isTurnedIn() && quest.isCompleted()) {
                System.out.println("\t1. Complete quest");
            }
        } else {
            System.out.println("\t--- Completed ---");
            System.out.println("\t0. Go back");
        }

        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> this.questGiverMenu(hero, quests, name);
            case 1 -> {
                if (!quest.isCompleted() && !hero.getListOfAcceptedQuests().contains(quest)) {
                    System.out.println("\t\t --> Quest accepted <--");
                    this.startTheQuest(quest, hero);
                    this.questGiverMenu(hero, quests, name);
                } else if (!quest.isTurnedIn() && quest.isCompleted()) {
                    this.completeTheQuest(quest, hero);
                    this.questGiverMenu(hero, quests, name);
                }
            }
            default -> System.out.println("\tEnter valid input");
        }
    }

    private void startTheQuest(Quest quest, Hero hero) {
        if (!hero.getListOfAcceptedQuests().contains(quest)) {
            hero.getListOfAcceptedQuests().add(quest);
        }
    }

    private void completeTheQuest(Quest quest, Hero hero) {
        quest.completeTheQuest(hero);
        quest.setTurnedIn(true);
        for (QuestObjective questObjective : quest.getQuestObjectives()) {
            questObjective.removeCompletedItemsOrEnemies(hero);
        }
    }

    private void printQuestDetails(Quest quest, Hero hero) {
        PrintUtil.printLongDivider();
        System.out.println("\t\t\t\t------ " + quest.getQuestName() + " ------");
        System.out.println("\t" + quest.getQuestDescription());
        for (QuestObjective questObjective : quest.getQuestObjectives()) {
            questObjective.printQuestObjectiveAssignment(hero);
        }
    }
}
