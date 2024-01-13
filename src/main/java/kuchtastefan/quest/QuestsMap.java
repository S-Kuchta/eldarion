package kuchtastefan.quest;

import kuchtastefan.quest.questObjectives.QuestBringItemObjective;
import kuchtastefan.quest.questObjectives.QuestKillObjective;
import kuchtastefan.quest.questObjectives.QuestObjective;

import java.util.HashMap;
import java.util.Map;

public class QuestsMap {
    public static Map<String, Quest> questsMap = new HashMap<>();
    public static Map<String, QuestKillObjective> questKillObjectiveMap = new HashMap<>();
    public static Map<String, QuestBringItemObjective> questBringItemObjectiveMap = new HashMap<>();


    public static QuestObjective returnQuestObjective(String questObjectiveKey) {
        Map<String, QuestObjective> questObjectiveMap = new HashMap<>();
        questObjectiveMap.putAll(questKillObjectiveMap);
        questObjectiveMap.putAll(questBringItemObjectiveMap);

        return questObjectiveMap.get(questObjectiveKey);
    }
}
