package kuchtastefan.quest.questObjectives;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.hero.save.SaveGameEntityList;
import kuchtastefan.character.hero.save.quest.HeroQuestObjective;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import kuchtastefan.item.specificItems.questItem.QuestItem;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestObjectiveDB {

    @Getter
    private final static Map<Integer, QuestObjective> QUEST_OBJECTIVE_DB = new HashMap<>();

    public static void addQuestObjectiveToDB(QuestObjective questObjective) {
        if (questObjective instanceof ConnectedWithItem connectedWithItem) {
            Item item = ItemDB.returnItemFromDB(connectedWithItem.getItemId());
            if (item instanceof QuestItem questItem) {
                questItem.setQuestObjectiveId(connectedWithItem.getId());
            }
        }

        QUEST_OBJECTIVE_DB.put(questObjective.getId(), questObjective);
    }

    public static List<QuestObjective> getQuestObjectiveListByIds(int[] questObjectiveIds) {
        List<QuestObjective> questObjectives = new ArrayList<>();
        for (int questId : questObjectiveIds) {
            questObjectives.add(getQuestObjectiveById(questId));
        }

        return questObjectives;
    }

    public static QuestObjective getQuestObjectiveById(int id) {
        QuestObjective questObjective = QUEST_OBJECTIVE_DB.get(id);
        if (questObjective == null) {
            throw new IllegalArgumentException("Quest Objective with id: " + id + " not found");
        }

        return questObjective;
    }

    public static void syncWithSaveGame(SaveGameEntityList<HeroQuestObjective> saveGameEntityList) {
        for (HeroQuestObjective heroQuestObjective : saveGameEntityList.getSaveEntities().values()) {
            QuestObjective questObjective = getQuestObjectiveById(heroQuestObjective.getId());
            questObjective.setCompleted(heroQuestObjective.isCompleted());
        }
    }

    public static void saveDatabase(Hero hero) {
        for (QuestObjective questObjective : getQuestObjectiveListByIds(hero.getSaveGameEntities().getHeroQuestObjectives().getEntitiesIds())) {
            hero.getSaveGameEntities().getHeroQuestObjectives().addEntity(new HeroQuestObjective(questObjective.getId(), questObjective.isCompleted()));
        }
    }
}
