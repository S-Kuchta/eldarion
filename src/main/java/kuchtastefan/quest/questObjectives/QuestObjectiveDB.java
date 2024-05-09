package kuchtastefan.quest.questObjectives;

import java.util.HashMap;
import java.util.Map;

public class QuestObjectiveDB {

    private final static Map<Integer, QuestObjective> QUEST_OBJECTIVE_DB = new HashMap<>();

    public static void addQuestObjectiveToDB(QuestObjective questObjective) {
        QUEST_OBJECTIVE_DB.put(questObjective.getQuestObjectiveId(), questObjective);
    }

    public static QuestObjective returnQuestObjectiveFromDb(int questObjectiveId) {
        return QUEST_OBJECTIVE_DB.get(questObjectiveId);
    }
}
