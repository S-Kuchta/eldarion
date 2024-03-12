package kuchtastefan.quest;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.npc.QuestGiverCharacter;

import java.util.HashMap;
import java.util.Map;

public class QuestGiverCharacterDB {

    public static final Map<Integer, QuestGiverCharacter> QUEST_GIVER_CHARACTER_MAP = new HashMap<>();

    public static String getQuestGiverName(int questGiverId) {
        return QUEST_GIVER_CHARACTER_MAP.get(questGiverId).getName();
    }

    public static void getQuestGiverMenu(int questGiverId, Hero hero) {
        QUEST_GIVER_CHARACTER_MAP.get(questGiverId).questGiverMenu(hero);
    }
}
