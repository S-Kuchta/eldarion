package kuchtastefan.characters;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.quest.Quest;
import kuchtastefan.quest.QuestService;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

import java.util.ArrayList;
import java.util.List;

public class QuestGiverCharacter extends GameCharacter {

    private List<Quest> quests;
    private final QuestService questService;
    private final String baseName;


    public QuestGiverCharacter(String name, int level, List<Quest> quests) {
        super(name, level);
        this.quests = quests;
        this.questService = new QuestService();
        this.baseName = name;
    }

    private void connectHeroQuestListWithCharacterQuestList(Hero hero) {
        for (Quest heroQuest : hero.getListOfAcceptedQuests()) {
            for (Quest quest : this.quests) {
                if (quest.equals(heroQuest)) {
                    this.quests.remove(quest);
                    this.quests.add(heroQuest);
                }
            }
        }
    }

    public void questGiverMenu(Hero hero) {
        connectHeroQuestListWithCharacterQuestList(hero);
        setNameBasedOnQuestsAvailable(hero);

        PrintUtil.printDivider();
        System.out.println("\t\t\t" + getName());
        PrintUtil.printDivider();
        System.out.println("\t0. Go back");
        int index = 1;
        for (Quest quest : this.quests) {
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
        System.out.println();

        while (true) {
            try {
                int choice = InputUtil.intScanner();
                if (choice == 0) {
                    break;
                }
                this.selectedQuestMenu(this.quests.get(choice - 1), hero);
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("\tEnter valid input");
            }
        }
    }

    private void selectedQuestMenu(Quest quest, Hero hero) {

        this.questService.printQuestDetails(quest, hero);

        System.out.println("\t0. Go back");
        if (!quest.isCompleted() && !hero.getListOfAcceptedQuests().contains(quest)) {
            System.out.println("\t1. Accept quest");
        } else if (!quest.isTurnedIn() && quest.isCompleted()) {
            System.out.println("\t1. Complete quest");
        }

        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> this.questGiverMenu(hero);
            case 1 -> {
                if (!quest.isCompleted() && !hero.getListOfAcceptedQuests().contains(quest)) {
                    System.out.println("\t\t --> Quest accepted <--");
                    this.questService.startQuest(quest, hero);
                    this.questGiverMenu(hero);
                } else if (!quest.isTurnedIn() && quest.isCompleted()) {
                    this.questService.completeTheQuest(quest, hero);
                    this.questGiverMenu(hero);
                }
            }
            default -> System.out.println("\tEnter valid input");
        }
    }

    public void setNameBasedOnQuestsAvailable(Hero hero) {
        connectHeroQuestListWithCharacterQuestList(hero);
        boolean haveQuestAvailable = false;
        boolean haveQuestComplete = false;

        int numberOfCompletedQuests = 0;

        for (Quest quest : this.quests) {
            if (hero.getListOfAcceptedQuests().contains(quest)) {
                haveQuestAvailable = true;
            }

            if (hero.getListOfAcceptedQuests().contains(quest) && quest.isCompleted() && !quest.isTurnedIn()) {
                haveQuestComplete = true;
            }

            if (quest.isTurnedIn()) {
                numberOfCompletedQuests++;
            }
        }

        if (!haveQuestAvailable) {
            this.setName(this.baseName + " - ! -");
        } else {
            this.setName(this.baseName);
        }

        if (haveQuestComplete) {
            this.setName(this.baseName + " - ? -");
        }

        System.out.println(this.quests.size() + " / " + numberOfCompletedQuests);
        if (this.quests.size() == numberOfCompletedQuests) {
            this.setName(this.baseName);
        }

    }

    public List<Quest> getQuests() {
        return quests;
    }

    public void setQuests(List<Quest> quests) {
        this.quests = quests;
    }
}
