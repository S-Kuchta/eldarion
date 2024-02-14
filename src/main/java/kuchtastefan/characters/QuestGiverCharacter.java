package kuchtastefan.characters;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.quest.Quest;
import kuchtastefan.quest.QuestService;
import kuchtastefan.utility.ConsoleColor;

import java.util.*;

public class QuestGiverCharacter extends GameCharacter {

    private final List<Quest> quests;
    private final QuestService questService;
    private final String baseName;


    public QuestGiverCharacter(String name, int level) {
        super(name, level);
        this.quests = new ArrayList<>();
        this.questService = new QuestService();
        this.baseName = name;
    }

//    private void connectHeroQuestListWithCharacterQuestList(Hero hero) {
////        for (Quest heroQuest : hero.getListOfAcceptedQuests()) {
//        for (Map.Entry<Integer, Quest> questMap : hero.getListOfAcceptedQuests().entrySet()) {
//            for (Quest quest : this.quests) {
//                if (quest.equals(heroQuest)) {
//                    this.quests.remove(quest);
//                    this.quests.add(heroQuest);
//                }
//            }
//        }
//    }

    private void connectHeroQuestListWithCharacterQuestList(Hero hero) {
//        for (Quest heroQuest : hero.getListOfAcceptedQuests()) {
//        for (Map.Entry<Integer, Quest> questMap : hero.getListOfAcceptedQuests().entrySet()) {
//            for (Quest quest : this.quests) {
//                if (quest.equals(questMap.getValue())) {
//                    this.quests.remove(quest);
//                    this.quests.add(questMap.getValue());
//                }
//            }
//        }

    }


    public void questGiverMenu(Hero hero) {
        connectHeroQuestListWithCharacterQuestList(hero);
        setNameBasedOnQuestsAvailable(hero);

        this.questService.questGiverMenu(hero, this.quests, this.name);
    }

    /**
     * Set name based on Quest progress
     * If quest is not accepted there will appear after name - ! -
     * If quest is completed but not turned in yet, there will appear after name - ? -
     * If quest is turned in there will appear after name - Completed -
     */
    public void setNameBasedOnQuestsAvailable(Hero hero) {
        connectHeroQuestListWithCharacterQuestList(hero);
        boolean haveQuestAvailable = false;
        boolean haveQuestComplete = false;

        int numberOfCompletedQuests = 0;

        for (Quest quest : this.quests) {
            if (hero.getListOfAcceptedQuests().containsValue(quest)) {
                haveQuestAvailable = true;
            }

            if (hero.getListOfAcceptedQuests().containsValue(quest) && quest.isQuestCompleted() && !quest.isTurnedIn()) {
                haveQuestComplete = true;
            }

            if (quest.isTurnedIn()) {
                numberOfCompletedQuests++;
            }
        }

        if (!haveQuestAvailable) {
            this.setName(this.baseName + " - " + ConsoleColor.YELLOW + "!" + ConsoleColor.RESET + " - ");
        } else {
            this.setName(this.baseName);
        }

        if (haveQuestComplete) {
            this.setName(this.baseName + " - " + ConsoleColor.YELLOW + "?" + ConsoleColor.RESET + " - ");
        }

        if (this.quests.size() == numberOfCompletedQuests) {
            this.setName(this.baseName);
        }
    }

    public void addQuest(Quest quest) {
        this.quests.add(quest);
    }

    public boolean checkIfAllAcceptedQuestsAreCompleted(Hero hero) {
        boolean questCompleted = true;
//        for (Quest quest : hero.getListOfAcceptedQuests()) {
        for (Map.Entry<Integer, Quest> questMap : hero.getListOfAcceptedQuests().entrySet()) {
            if (this.quests.contains(questMap.getValue()) && !questMap.getValue().isTurnedIn()) {
                questCompleted = false;
                break;
            }
        }

        for (Quest quest : this.quests) {
            if (!hero.getListOfAcceptedQuests().containsValue(quest)) {
                questCompleted = false;
                break;
            }
        }

//        if (!new HashSet<>(hero.getListOfAcceptedQuests()).containsAll(this.quests)) {
//            questCompleted = false;
//        }

        return questCompleted;
    }
}















