package kuchtastefan.quest;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.hero.save.SaveGameEntityList;
import kuchtastefan.character.hero.save.quest.HeroQuest;
import kuchtastefan.quest.questObjectives.QuestObjective;

import java.util.*;

public class QuestDB {

    private static final Map<Integer, Quest> QUEST_DB = new HashMap<>();

    public static Quest returnQuestFromDB(int questID) {
        return QUEST_DB.get(questID);
    }


    public static void addQuestToDB(Quest quest) {
        quest.getQuestReward().calculateExperiencePointsReward(quest.getQuestLevel());
        quest.convertIdToQuestObjectiveMap();
        QUEST_DB.put(quest.getQuestId(), quest);
    }

    public static List<Quest> getQuestListByIds(int[] questIds) {
        List<Quest> quests = new ArrayList<>();
        for (int questId : questIds) {
            quests.add(returnQuestFromDB(questId));
        }

        return quests;
    }

    public static Quest getQuestContainsQuestObjectiveId(int questObjectiveId) {
        for (Quest quest : QUEST_DB.values()) {
            if (quest.containsQuestObjective(questObjectiveId)) {
                return quest;
            }
        }

        throw new NoSuchElementException("\n\tQuest with Quest Objective id: " + questObjectiveId + " not found");
    }

    public static boolean isContainingQuestObjectiveByIds(int questObjectiveId, int[] questIds) {
        for (Quest quest : getQuestListByIds(questIds)) {
            if (quest.getQuestObjectives().containsKey(questObjectiveId)) {
                return true;
            }
        }

        return false;
    }

    public static QuestObjective getQuestObjectiveById(int questObjectiveId, int[] questIds) {
        for (Quest quest : getQuestListByIds(questIds)) {
            if (quest.getQuestObjectives().containsKey(questObjectiveId)) {
                return quest.getQuestObjectives().get(questObjectiveId);
            }
        }

        throw new NoSuchElementException("\n\tQuest Objective with id: " + questObjectiveId + " not found");
    }

    public static void setInitialQuestsStatus(Hero hero) {
        for (Quest quest : QUEST_DB.values()) {
            setQuestStatus(hero, quest);
        }
    }

    public static void setQuestStatus(Hero hero, Quest quest) {
        if (quest.canBeQuestAccepted(hero)) {
            quest.setQuestStatus(QuestStatus.AVAILABLE);
        } else {
            quest.setQuestStatus(QuestStatus.UNAVAILABLE);
        }
    }

    public static void syncQuestsWithSaveGame(SaveGameEntityList<HeroQuest> saveGameEntityList) {
        Quest quest;
        for (HeroQuest heroQuest : saveGameEntityList.getSaveEntities().values()) {
            quest = returnQuestFromDB(heroQuest.getId());

            if (quest != null) {
                syncQuestStatus(quest, heroQuest);
                syncQuestObjectives(quest, heroQuest);
            }
        }
    }

    private static void syncQuestStatus(Quest quest, HeroQuest heroQuest) {
        quest.setQuestStatus(heroQuest.getQuestStatus());
    }

    private static void syncQuestObjectives(Quest quest, HeroQuest heroQuest) {
        for (QuestObjective questObjective : quest.getQuestObjectivesList()) {
            int objectiveId = questObjective.getQuestObjectiveId();

            if (heroQuest.containsObjectiveById(objectiveId)) {
                questObjective.setCompleted(heroQuest.getObjectives().get(objectiveId).isCompleted());
            }
        }
    }

    public static void saveDatabase(Hero hero) {
        for (Quest quest : getQuestListByIds(hero.getHeroQuestList().getEntitiesIds())) {
            hero.getHeroQuestList().addEntity(new HeroQuest(quest.getQuestId(), quest.getQuestStatus(), quest.getHeroQuestObjectiveMap()));
        }
    }

}
