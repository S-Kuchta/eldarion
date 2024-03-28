package kuchtastefan.quest.questGiver;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.hint.HintName;
import kuchtastefan.hint.HintDB;
import kuchtastefan.quest.Quest;
import kuchtastefan.quest.QuestDB;
import kuchtastefan.quest.QuestService;
import kuchtastefan.quest.QuestStatus;
import kuchtastefan.utility.ConsoleColor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class QuestGiverCharacter {

    private int questGiverId;
    private String name;
    private final String baseName;
    private int[] questsId;
    private List<Quest> quests;


    public QuestGiverCharacter(String name) {
        this.name = name;
        this.quests = new ArrayList<>();
        this.baseName = name;
    }

    /**
     * Connects the hero's accepted quest list with the character's quest list.
     * Iterates through each quest in the quest list and checks if it exists in the hero's accepted quest list.
     * If found, updates the quest in the character's quest list with the one from the hero's accepted quest list.
     *
     * @param hero The hero whose accepted quest list is being connected.
     */
    private void connectHeroQuestListWithCharacterQuestList(Hero hero) {
        for (Quest quest : this.quests) {
            for (Map.Entry<Integer, Quest> questMap : hero.getHeroAcceptedQuest().entrySet()) {
                if (quest.equals(questMap.getValue())) {
                    int position = this.quests.indexOf(questMap.getValue());
                    this.quests.set(position, questMap.getValue());
                }
            }
        }
    }

    public void questGiverMenu(Hero hero) {
        QuestService questService = new QuestService();
        questService.setQuestGiverCharacter(this);
        HintDB.printHint(HintName.QUEST_HINT);

        connectHeroQuestListWithCharacterQuestList(hero);
        setNameBasedOnQuestsAvailable(hero);
        questService.questGiverMenu(hero, this.quests, this.name);
    }

    /**
     * Set name based on Quest progress
     * If quest is not accepted there will appear after name: - ! -
     * If quest is completed but not turned in yet, there will appear after name: - ? -
     * If quest is turned in there will appear after name: - Completed -
     */
//    public void setNameBasedOnQuestsAvailable(Hero hero) {
//        connectHeroQuestListWithCharacterQuestList(hero);
//        boolean haveQuestAvailable = false;
//        boolean haveQuestComplete = false;
//
//        int numberOfCompletedQuests = 0;
//        for (Quest quest : this.quests) {
//
//            haveQuestAvailable = hero.getHeroAcceptedQuest().containsValue(quest);
//
//            if (hero.getHeroAcceptedQuest().containsValue(quest) && quest.isQuestCompleted() && !quest.isTurnedIn()) {
//                haveQuestComplete = true;
//                break;
//            }
//
//            if (quest.isTurnedIn()) {
//                numberOfCompletedQuests++;
//            }
//        }
//
//        if (!haveQuestAvailable) {
//            this.setName(this.baseName + " - " + ConsoleColor.YELLOW_BOLD_BRIGHT + "!" + ConsoleColor.RESET + " - ");
//        } else {
//            this.setName(this.baseName);
//        }
//
//        if (haveQuestComplete) {
//            this.setName(this.baseName + " - " + ConsoleColor.YELLOW_BOLD_BRIGHT + "?" + ConsoleColor.RESET + " - ");
//        }
//
//        if (this.quests.size() == numberOfCompletedQuests) {
//            this.setName(this.baseName);
//        }
//    }
    public void setNameBasedOnQuestsAvailable(Hero hero) {
        this.name = this.baseName + returnNameSuffix(hero);

//        connectHeroQuestListWithCharacterQuestList(hero);
//        boolean haveQuestAvailable = false;
//        boolean haveQuestUnavailable = false;
//        boolean haveQuestComplete = false;
//        int numberOfCompletedQuests = 0;

//        for (Quest quest : this.quests) {
//            if (hero.getHeroAcceptedQuest().containsValue(quest)) {
//                if (quest.getQuestStatus().equals(QuestStatus.TURNED_IN)) {
//                    this.setName(this.baseName);
//                    numberOfCompletedQuests++;
//                }
//
//                if (quest.getQuestStatus().equals(QuestStatus.UNAVAILABLE)) {
//                    this.setName(this.baseName + " - " + ConsoleColor.WHITE + "!" + ConsoleColor.RESET + " - ");
//                }
//
//                if (quest.getQuestStatus().equals(QuestStatus.AVAILABLE)) {
////                    this.setName(this.baseName + " - " + ConsoleColor.YELLOW_BOLD_BRIGHT + "!" + ConsoleColor.RESET + " - ");
//                    haveQuestAvailable = true;
//                }
//
//                if (quest.getQuestStatus().equals(QuestStatus.COMPLETED)) {
//                    this.setName(this.baseName + " - " + ConsoleColor.YELLOW_BOLD_BRIGHT + "?" + ConsoleColor.RESET + " - ");
//                    break;
//                }
//            }

//            if (haveQuestAvailable && !haveQuestComplete) {
//                this.setName(this.baseName + " - " + ConsoleColor.YELLOW_BOLD_BRIGHT + "!" + ConsoleColor.RESET + " - ");
//            }
//
//            if (haveQuestAvailable && haveQuestComplete) {
//                this.setName(this.baseName + " - " + ConsoleColor.YELLOW_BOLD_BRIGHT + "?" + ConsoleColor.RESET + " - ");
//            }
//
//            if (this.quests.size() == numberOfCompletedQuests) {
//                this.setName(this.baseName + ConsoleColor.YELLOW + " -- COMPLETED -- " + ConsoleColor.RESET);
//            }
    }

    private String returnNameSuffix(Hero hero) {
        connectHeroQuestListWithCharacterQuestList(hero);
        int numberOfTurnedInQuests = 0;
        boolean haveQuestAvailable = false;
        boolean haveQuestUnavailable = false;

        for (Quest quest : this.quests) {

            if (quest.getQuestStatus().equals(QuestStatus.TURNED_IN)) {
                numberOfTurnedInQuests++;
            }
//            if (hero.getHeroAcceptedQuest().containsValue(quest)) {
            if (quest.getQuestStatus().equals(QuestStatus.UNAVAILABLE)) {
                haveQuestUnavailable = true;
            }

            if (quest.getQuestStatus().equals(QuestStatus.AVAILABLE)) {
                haveQuestAvailable = true;
            }

            if (quest.getQuestStatus().equals(QuestStatus.COMPLETED)) {
                return " - " + ConsoleColor.YELLOW_BOLD_BRIGHT + "?" + ConsoleColor.RESET + " - ";
            }
//            }
        }

        if (haveQuestAvailable && haveQuestUnavailable) {
            return " - " + ConsoleColor.YELLOW + "!" + ConsoleColor.RESET + " - ";
        }

        if (!haveQuestAvailable && haveQuestUnavailable) {
            return " - " + ConsoleColor.WHITE + "!" + ConsoleColor.RESET + " - ";
        }

        if (haveQuestAvailable) {
            return " - " + ConsoleColor.YELLOW + "!" + ConsoleColor.RESET + " - ";
        }

        return numberOfTurnedInQuests == this.quests.size() ? " -- " + ConsoleColor.YELLOW + "COMPLETED" + ConsoleColor.RESET + " -- " : "";
    }

    public void addQuest(Quest quest) {
        this.quests.add(quest);
    }

    public boolean checkIfAllAcceptedQuestsAreCompleted(Hero hero) {
        boolean questCompleted = true;

//        for (Map.Entry<Integer, Quest> questMap : hero.getHeroAcceptedQuest().entrySet()) {
        for (Quest quest : hero.getHeroAcceptedQuest().values()) {
//            if (this.quests.contains(questMap.getValue()) && !questMap.getValue().isTurnedIn()) {
//                questCompleted = false;
//                break;
//            }

            if (this.quests.contains(quest) && !quest.getQuestStatus().equals(QuestStatus.TURNED_IN)) {
                questCompleted = false;
                break;
            }
        }

        for (Quest quest : this.quests) {
            if (!hero.getHeroAcceptedQuest().containsValue(quest)) {
                questCompleted = false;
                break;
            }
        }

        return questCompleted;
    }

    public void convertQuestIdToQuest() {
        this.quests = new ArrayList<>();

        for (int questId : this.questsId) {
            this.quests.add(QuestDB.returnQuestFromDB(questId));
        }
    }
}















