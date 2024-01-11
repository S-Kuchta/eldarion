package kuchtastefan.quest;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

public class QuestService {
//    private final QuestList questList;

//    public QuestService(QuestList questList) {
//        this.questList = questList;
//    }

    public void startQuest(Quest quest) {
        QuestList.acceptedQuest.add(quest);
    }

    public void completeTheQuest(Quest quest, Hero hero) {
        quest.completeTheQuest(hero);
        for (QuestObjective questObjective : quest.getQuestObjectives()) {
            questObjective.removeCompletedItemsOrEnemies(hero);
        }
    }

    public void printQuests(Hero hero) {
        PrintUtil.printLongDivider();
        System.out.println("\t-- Quest Details --");
        PrintUtil.printLongDivider();
        int index = 1;
        for (Quest quest : QuestList.acceptedQuest) {
            System.out.println(index + ". " + quest.getQuestName());
            index++;
        }

        while (true) {
            try {
                int choice = InputUtil.intScanner();
                printQuestDetails(QuestList.acceptedQuest.get(choice - 1), hero);
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Enter valid input");
            }
        }
    }

    public void printQuestDetails(Quest quest, Hero hero) {
        System.out.println("\t0. Go back");
        PrintUtil.printLongDivider();
        System.out.println("\t\t\t\t------ " + quest.getQuestName() + " ------");
        System.out.println("\t" + quest.getQuestDescription());
        for (QuestObjective questObjective : quest.getQuestObjectives()) {
            System.out.println("\t\t\t--- " + questObjective.getQuestObjectiveName() + " ---");
            System.out.print("\t");
            questObjective.printQuestObjectiveAssignment(hero);
            System.out.println("\tCompleted: " + questObjective.isCompleted());
        }
    }
}
