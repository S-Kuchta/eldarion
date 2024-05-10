package kuchtastefan.quest.questObjectives;

import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import kuchtastefan.item.specificItems.questItem.QuestItem;

import java.util.HashMap;
import java.util.Map;

public class QuestObjectiveDB {

    private final static Map<Integer, QuestObjective> QUEST_OBJECTIVE_DB = new HashMap<>();

    public static void addQuestObjectiveToDB(QuestObjective questObjective) {
        if (questObjective instanceof ConnectedWithItem connectedWithItem) {
            Item item = ItemDB.returnItemFromDB(connectedWithItem.getItemId());
            if (item instanceof QuestItem questItem) {
                questItem.setQuestObjectiveId(connectedWithItem.getQuestObjectiveId());
            }
        }

        QUEST_OBJECTIVE_DB.put(questObjective.getQuestObjectiveId(), questObjective);
    }

    public static QuestObjective returnQuestObjectiveFromDb(int questObjectiveId) {
        return QUEST_OBJECTIVE_DB.get(questObjectiveId);
    }
}
