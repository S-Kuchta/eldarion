package kuchtastefan.quest;

import kuchtastefan.character.hero.Hero;

import java.util.HashMap;
import java.util.Map;

public class QuestDB {
    private static final Map<Integer, Quest> QUEST_DB = new HashMap<>();

    public static Quest returnQuestFromDB(int questID) {
        return QUEST_DB.get(questID);
    }

    public static void addQuestToDB(Quest quest) {
        quest.getQuestReward().calculateExperiencePointsReward(quest.getQuestLevel());
        QUEST_DB.put(quest.getQuestId(), quest);
    }

    public static void setInitialQuestsStatus(Hero hero) {
        for (Quest quest : QUEST_DB.values()) {
            if (quest instanceof QuestChain questChain) {
                if (questChain.canBeQuestAccepted(hero)) {
                    quest.setQuestStatus(QuestStatus.AVAILABLE);
                } else {
                    quest.setQuestStatus(QuestStatus.UNAVAILABLE);
                }
            } else {
                if (quest.getQuestLevel() <= hero.getLevel()) {
                    quest.setQuestStatus(QuestStatus.AVAILABLE);
                } else {
                    quest.setQuestStatus(QuestStatus.UNAVAILABLE);
                }
            }
        }
    }

    public static void loadQuests(Hero hero) {
        for (Quest quest : QUEST_DB.values()) {
            if (hero.getHeroQuests().getHeroAcceptedQuest().containsValue(quest)) {
                quest.setQuestStatus(hero.getHeroQuests().getHeroAcceptedQuest().get(quest.getQuestId()).getQuestStatus());
            }
        }
    }
}
