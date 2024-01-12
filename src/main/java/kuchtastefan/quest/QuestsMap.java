package kuchtastefan.quest;

import kuchtastefan.quest.questObjectives.QuestBringItemObjective;
import kuchtastefan.quest.questObjectives.QuestKillObjective;

import java.util.HashMap;
import java.util.Map;

public class QuestsMap {
    public static Map<String, Quest> questsMap = new HashMap<>();
    public static Map<String, QuestKillObjective> questKillObjectiveMap = new HashMap<>();
    public static Map<String, QuestBringItemObjective> questBringItemObjectiveMap = new HashMap<>();
}
