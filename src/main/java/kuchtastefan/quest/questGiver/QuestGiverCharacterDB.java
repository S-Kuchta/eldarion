package kuchtastefan.quest.questGiver;

import kuchtastefan.character.hero.Hero;

import java.util.HashMap;
import java.util.Map;

public class QuestGiverCharacterDB {

    private static final Map<Integer, QuestGiverCharacter> QUEST_GIVER_CHARACTER_DB = new HashMap<>();

    public static void questGiverMenu(int questGiverId, Hero hero) {
        QUEST_GIVER_CHARACTER_DB.get(questGiverId).questGiverMenu(hero);
    }

    public static QuestGiverCharacter returnQuestGiverFromDB(int questGiverId) {
        return QUEST_GIVER_CHARACTER_DB.get(questGiverId);
    }

    public static void addQuestGiverToDB(QuestGiverCharacter questGiverCharacter) {
        questGiverCharacter.setName(questGiverCharacter.getBaseName());
        QUEST_GIVER_CHARACTER_DB.put(questGiverCharacter.getQuestGiverId(), questGiverCharacter);
    }

    public static void setAllQuestGiversName() {
        for (QuestGiverCharacter questGiverCharacter : QUEST_GIVER_CHARACTER_DB.values()) {
            questGiverCharacter.setQuests();
            questGiverCharacter.setNameBasedOnQuestsAvailable();
        }
    }
}
