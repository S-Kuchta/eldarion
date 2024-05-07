package kuchtastefan.service;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.npc.enemy.Enemy;
import kuchtastefan.item.ItemDB;
import kuchtastefan.quest.Quest;
import kuchtastefan.quest.QuestStatus;
import kuchtastefan.quest.questGiver.QuestGiverCharacter;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.specificQuestObjectives.QuestBringItemFromEnemyObjective;
import kuchtastefan.quest.questObjectives.specificQuestObjectives.QuestKillObjective;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

// TODO problem with menu

@Setter
@Getter
public class QuestService {

    private QuestGiverCharacter questGiverCharacter;

    public void questGiverMenu(Hero hero, List<Quest> quests) {
        System.out.println("quest giver menu");
        printQuestsMenu(quests);
        try {
            int choice = InputUtil.intScanner();
            if (choice == 0) {
                return;
            }

            if (hero.getHeroAcceptedQuest().containsValue(quests.get(choice - 1))) {
                this.selectedQuestMenu(hero.getHeroAcceptedQuest().get(quests.get(choice - 1).getQuestId()), hero, quests);
            } else {
                this.selectedQuestMenu(quests.get(choice - 1), hero, quests);
            }

            this.questGiverCharacter.setNameBasedOnQuestsAvailable(hero);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("\tEnter valid input");
        }
    }

    public void heroAcceptedQuestMenu(Hero hero) {
        List<Quest> quests = new ArrayList<>(hero.getHeroAcceptedQuest().values());

        PrintUtil.printLongDivider();
        System.out.println("\t-- Quest Details --");
        PrintUtil.printLongDivider();

        while (true) {
            try {
                PrintUtil.printLongDivider();
                printQuestsMenu(quests);
                int choice = InputUtil.intScanner();
                if (choice == 0) {
                    break;
                }

                if (!quests.get(choice - 1).getQuestStatus().equals(QuestStatus.TURNED_IN)) {
                    PrintUtil.printQuestDetails(quests.get(choice - 1), hero);
                } else {
                    System.out.println("\t-- Quest " + quests.get(choice - 1).getQuestName() + " Completed --");
                }

            } catch (IndexOutOfBoundsException e) {
                System.out.println("\tEnter valid input");
            }
        }
    }

    private void printQuestsMenu(List<Quest> quests) {
        System.out.println("print quests menu");
        PrintUtil.printIndexAndText("0", "Go back");
        System.out.println();

        int index = 1;
        for (Quest quest : quests) {
            PrintUtil.printIndexAndText(String.valueOf(index), quest.getQuestName() + " " + PrintUtil.returnQuestSuffix(quest));
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
     */
    private void selectedQuestMenu(Quest quest, Hero hero, List<Quest> quests) {
        System.out.println("som tu ked sa to deje? ");
        PrintUtil.printQuestDetails(quest, hero);
        System.out.println();

        switch (quest.getQuestStatus()) {
            case QuestStatus.AVAILABLE -> {
                PrintUtil.printIndexAndText("0", "Go back\n");
                PrintUtil.printIndexAndText("1", "Accept quest\n");
            }
            case QuestStatus.UNAVAILABLE -> {
                System.out.println(ConsoleColor.RED + "\tQuest can not be accepted yet." + ConsoleColor.RESET);
                PrintUtil.printIndexAndText("0", "Go back\n");
            }
            case QuestStatus.ACCEPTED -> {
                PrintUtil.printQuestDetails(quest, hero);
                this.questGiverMenu(hero, quests);
            }
            case QuestStatus.COMPLETED -> {
                PrintUtil.printIndexAndText("0", "Go back\n");
                PrintUtil.printIndexAndText("1", "Complete quest\n");
            }
            case QuestStatus.TURNED_IN -> {
                System.out.println("\t-- Quest " + quest.getQuestName() + " Completed --");
                PrintUtil.printIndexAndText("0", "Go back\n");
            }
        }

        while (true) {
            int choice = InputUtil.intScanner();
            if (choice == 0) {
                break;
            } else if (choice == 1) {
                if (quest.getQuestStatus().equals(QuestStatus.AVAILABLE)) {
                    System.out.println("\t\t --> Quest accepted <--");
                    quest.startTheQuest(hero);
                    this.questGiverMenu(hero, quests);
                }

                if (quest.getQuestStatus().equals(QuestStatus.COMPLETED)) {
                    quest.turnInTheQuest(hero);
                    this.questGiverMenu(hero, quests);
                }
            } else {
                PrintUtil.printEnterValidInput();
                this.selectedQuestMenu(quest, hero, quests);
            }
        }



//        while (true) {
//            int choice = InputUtil.intScanner();
//            switch (choice) {
////            case 0 -> this.questGiverMenu(hero, quests);
//                case 0 -> {
//                    return;
//                }
//                case 1 -> {
//                    if (quest.getQuestStatus().equals(QuestStatus.AVAILABLE)) {
//                        System.out.println("\t\t --> Quest accepted <--");
//                        quest.startTheQuest(hero);
//                        this.questGiverMenu(hero, quests);
//                    }
//
//                    if (quest.getQuestStatus().equals(QuestStatus.COMPLETED)) {
//                        quest.turnInTheQuest(hero);
//                        this.questGiverMenu(hero, quests);
//                    }
//                }
//                default -> {
//                    PrintUtil.printEnterValidInput();
//                    this.selectedQuestMenu(quest, hero, quests);
//                }
//            }
//        }

    }

    public void updateQuestProgressFromEnemyActions(Hero hero, Enemy enemy) {
        for (Quest quest : hero.getHeroAcceptedQuest().values()) {
            for (QuestObjective questObjective : quest.getQuestObjectives()) {
                if (!questObjective.isCompleted()) {
                    if (questObjective instanceof QuestBringItemFromEnemyObjective questBringItemFromEnemyObjective) {
                        if (questBringItemFromEnemyObjective.checkEnemy(enemy.getNpcId())) {
                            enemy.addItemToItemDrop(ItemDB.returnItemFromDB(questBringItemFromEnemyObjective.getObjectiveItemId()));
                        }
                    }

                    if (questObjective instanceof QuestKillObjective questKillObjective) {
                        if (enemy.getNpcId() == questKillObjective.getQuestEnemyId()) {
                            questKillObjective.increaseCurrentCountEnemyProgress();
                        }
                    }
                }
            }
        }
    }
}
