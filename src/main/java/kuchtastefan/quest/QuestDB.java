package kuchtastefan.quest;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.hero.save.SaveGameEntityList;
import kuchtastefan.character.hero.save.quest.HeroQuest;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.QuestObjectiveDB;
import lombok.Getter;

import java.util.*;

public class QuestDB {

    @Getter
    private static final Map<Integer, Quest> QUEST_DB = new HashMap<>();


    public static void addQuestToDB(Quest quest) {
        quest.getReward().calculateExperiencePointsReward(quest.getLevel());
        initializeQuestObjectives(quest);
        QUEST_DB.put(quest.getId(), quest);
    }

    private static void initializeQuestObjectives(Quest quest) {
        if (quest.getObjectives() == null) {
            quest.setObjectives(new HashMap<>());
        }

        for (int questObjectiveId : quest.getObjectivesIds()) {
            quest.getObjectives().put(questObjectiveId, QuestObjectiveDB.getQuestObjectiveById(questObjectiveId));
        }
    }

    public static List<Quest> getQuestListByIds(int[] questIds) {
        List<Quest> quests = new ArrayList<>();
        for (int questId : questIds) {
            quests.add(getQuestById(questId));
        }

        return quests;
    }

    public static Quest getQuestById(int id) {
        Quest quest = QUEST_DB.get(id);
        if (quest == null) {
            throw new IllegalArgumentException("Quest with id: " + id + " not found");
        }

        return quest;
    }

    public static Quest findQuestByObjectiveId(int questObjectiveId) {
        for (Quest quest : QUEST_DB.values()) {
            if (quest.containsQuestObjective(questObjectiveId)) {
                return quest;
            }
        }

        throw new NoSuchElementException("\n\tQuest with Quest Objective id: " + questObjectiveId + " not found");
    }

    public static void setInitialQuestsStatusFromGivenList(Hero hero, List<Quest> quests) {
        for (Quest quest : quests) {
            quest.setInitialQuestStatus(hero);
            quest.setStatusIcon();
        }
    }

    public static void syncWithSaveGame(SaveGameEntityList<HeroQuest> saveGameEntityList) {
        for (HeroQuest heroQuest : saveGameEntityList.getSaveEntities().values()) {
            Quest quest = getQuestById(heroQuest.getId());
            quest.setStatus(heroQuest.getQuestStatus());
        }
    }

    public static void saveDatabase(Hero hero) {
        for (Quest quest : getQuestListByIds(hero.getSaveGameEntities().getHeroQuests().getEntitiesIds())) {
            hero.getSaveGameEntities().getHeroQuests().addEntity(new HeroQuest(quest.getId(), quest.getStatus()));
        }
    }

}
