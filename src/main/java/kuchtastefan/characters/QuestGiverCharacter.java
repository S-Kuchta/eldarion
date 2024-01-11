package kuchtastefan.characters;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.quest.Quest;
import kuchtastefan.quest.QuestService;
import kuchtastefan.utility.InputUtil;

import java.util.List;

public class QuestGiverCharacter extends GameCharacter {

    private List<Quest> quests;
    private final QuestService questService;

    public QuestGiverCharacter(String name, int level, List<Quest> quests) {
        super(name, level);
        this.quests = quests;
        this.questService = new QuestService();
    }

    public void questGiverMenu(Hero hero) {
        System.out.println("\tHello, do you want a quest?\n");
        System.out.println("0. Go back");
        int index = 1;
        for (Quest quest : this.quests) {
            System.out.print(index + ". " + quest.getQuestName());
            if (quest.isCompleted()) {
                System.out.print(" -- Completed --");
            }
        }

        while (true) {
            try {
                int choice = InputUtil.intScanner();
                this.selectedQuestMenu(this.quests.get(choice - 1), hero);
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Enter valid input");
            }
        }

//        switch (choice) {
//            case 0 -> {
//            }
//            default -> selectedQuestMenu(this.quests.get(choice - 1), hero);
//        }

    }

    private void selectedQuestMenu(Quest quest, Hero hero) {
        System.out.println("\t" + quest.getQuestName());
        this.questService.printQuests(hero);
        System.out.println("0. Go back");

        if (!quest.isCompleted()) {
            System.out.println("\t1. Accept quest");
        }

        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
                this.questGiverMenu(hero);
            }
            case 1 -> {
                if (quest.isCompleted()) {
                    System.out.println("\tYou already complete this quest");
                    this.questGiverMenu(hero);
                } else {
                    this.questService.startQuest(quest);
                    this.questGiverMenu(hero);
                }
            }
            default -> System.out.println("Enter valid input");
        }

    }

    public List<Quest> getQuests() {
        return quests;
    }

    public void setQuests(List<Quest> quests) {
        this.quests = quests;
    }

    public QuestService getQuestService() {
        return questService;
    }
}
