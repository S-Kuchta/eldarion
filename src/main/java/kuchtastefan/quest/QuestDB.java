package kuchtastefan.quest;

import java.util.HashMap;
import java.util.Map;

public class QuestDB {
    private static final Map<Integer, Quest> QUEST_DB = new HashMap<>();

    public static Quest returnQuest(int questID) {
        return QUEST_DB.get(questID);
    }

    public static void addQuestToDB(Quest quest) {
        QUEST_DB.put(quest.getQuestId(), quest);
    }
}
