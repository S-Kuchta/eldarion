package kuchtastefan.character.hero;

import kuchtastefan.quest.Quest;
import kuchtastefan.quest.questObjectives.QuestObjective;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class HeroQuests {

    private final Map<Integer, Quest> heroAcceptedQuest;
    private final Map<Integer, QuestObjective> heroQuestObjectives;

    public HeroQuests() {
        this.heroAcceptedQuest = new HashMap<>();
        this.heroQuestObjectives = new HashMap<>();
    }

    public void addQuestToHeroAcceptedQuest(Quest quest) {
        this.heroAcceptedQuest.put(quest.getQuestId(), quest);
        for (QuestObjective questObjective : quest.getQuestObjectives()) {
            addQuestObjectiveToHeroQuestObjectives(questObjective);
        }
    }

    public boolean containsQuest(int questId) {
        return this.heroAcceptedQuest.containsKey(questId);
    }

    public Quest getQuest(int questId) {
        return this.heroAcceptedQuest.get(questId);
    }

    public void addQuestObjectiveToHeroQuestObjectives(QuestObjective questObjective) {
        this.heroQuestObjectives.put(questObjective.getQuestObjectiveId(), questObjective);
    }

    public boolean containsQuestObjective(int questObjectiveId) {
        return this.heroQuestObjectives.containsKey(questObjectiveId);
    }

    public QuestObjective getQuestObjective(int questObjectiveId) {
        return this.heroQuestObjectives.get(questObjectiveId);
    }
}
