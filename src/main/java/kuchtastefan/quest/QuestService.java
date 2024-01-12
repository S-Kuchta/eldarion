package kuchtastefan.quest;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

import java.util.List;

public class QuestService {

    public Quest createNewQuest(Quest quest, List<? extends QuestObjective> questObjectives) {
        
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

    public void printAcceptedQuests(Hero hero) {
        PrintUtil.printLongDivider();
        System.out.println("\t-- Quest Details --");
        PrintUtil.printLongDivider();

        int index = 1;
        System.out.println("\t0. Go back");
        for (Quest quest : hero.getListOfAcceptedQuests()) {
            System.out.println("\t" + index + ". " + quest.getQuestName());
            index++;
        }

        while (true) {
            try {
                int choice = InputUtil.intScanner();
                if (choice == 0) {
                    break;
                }
                printQuestDetails(hero.getListOfAcceptedQuests().get(choice - 1), hero);
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("\tEnter valid input");
            }
        }
    }

    public void printQuestDetails(Quest quest, Hero hero) {
        PrintUtil.printLongDivider();
        System.out.println("\t\t\t\t------ " + quest.getQuestName() + " ------");
        System.out.println("\t" + quest.getQuestDescription());
        for (QuestObjective questObjective : quest.getQuestObjectives()) {
//            System.out.println("\t\t\t\t------ " + questObjective.getQuestObjectiveName() + " ------");
//            System.out.print("\t");
            questObjective.printQuestObjectiveAssignment(hero);
        }
    }
}
