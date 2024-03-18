package kuchtastefan.quest.questGiver;

import kuchtastefan.character.hero.Hero;

import java.util.HashMap;
import java.util.Map;

public class QuestGiverCharacterDB {

    private static final Map<Integer, QuestGiverCharacter> QUEST_GIVER_CHARACTER_DB = new HashMap<>();

    public static String returnQuestGiverName(int questGiverId) {
        return QUEST_GIVER_CHARACTER_DB.get(questGiverId).getName();
    }

    public static void returnQuestGiverMenu(int questGiverId, Hero hero) {
        QUEST_GIVER_CHARACTER_DB.get(questGiverId).questGiverMenu(hero);
    }

    public static QuestGiverCharacter returnQuestGiverFromDB(int questGiverId) {
        return QUEST_GIVER_CHARACTER_DB.get(questGiverId);
    }

    public static void addQuestGiverToDB(QuestGiverCharacter questGiverCharacter) {
        QUEST_GIVER_CHARACTER_DB.put(questGiverCharacter.getQuestGiverId(), questGiverCharacter);
    }
}
