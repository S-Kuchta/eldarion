package kuchtastefan.quest;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

import java.util.List;

public class QuestService {

    public void printQuestsMenu(Hero hero, List<Quest> quests) {
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

    public void questGiverMenu(Hero hero, List<Quest> quests) {
        printQuestsMenu(hero, quests);

        while (true) {
            try {
                int choice = InputUtil.intScanner();
                if (choice == 0) {
                    break;
                }
                this.selectedQuestForQuestGiverMenu(quests.get(choice - 1), hero, quests);
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("\tEnter valid input");
            }
        }
    }

    public void acceptedQuestMenu(Hero hero, List<Quest> quests) {
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

    private void selectedQuestForQuestGiverMenu(Quest quest, Hero hero, List<Quest> quests) {

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
            case 0 -> this.questGiverMenu(hero, quests);
            case 1 -> {
                if (!quest.isCompleted() && !hero.getListOfAcceptedQuests().contains(quest)) {
                    System.out.println("\t\t --> Quest accepted <--");
                    this.startQuest(quest, hero);
                    this.questGiverMenu(hero, quests);
                } else if (!quest.isTurnedIn() && quest.isCompleted()) {
                    this.completeTheQuest(quest, hero);
                    this.questGiverMenu(hero, quests);
                }
            }
            default -> System.out.println("\tEnter valid input");
        }
    }

    public void startQuest(Quest quest, Hero hero) {
        if (!hero.getListOfAcceptedQuests().contains(quest)) {
            hero.getListOfAcceptedQuests().add(quest);
        }
    }

    public void completeTheQuest(Quest quest, Hero hero) {
        quest.completeTheQuest(hero);
        quest.setTurnedIn(true);
        for (QuestObjective questObjective : quest.getQuestObjectives()) {
            questObjective.removeCompletedItemsOrEnemies(hero);
        }
    }

    public void printQuestDetails(Quest quest, Hero hero) {
        PrintUtil.printLongDivider();
        System.out.println("\t\t\t\t------ " + quest.getQuestName() + " ------");
        System.out.println("\t" + quest.getQuestDescription());
        for (QuestObjective questObjective : quest.getQuestObjectives()) {
            questObjective.printQuestObjectiveAssignment(hero);
        }
    }
}
