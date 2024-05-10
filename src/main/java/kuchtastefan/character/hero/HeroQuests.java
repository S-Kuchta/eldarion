package kuchtastefan.character.hero;

import kuchtastefan.quest.Quest;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.specificQuestObjectives.QuestClearLocationObjective;
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

    private void addQuestObjectiveToHeroQuestObjectives(QuestObjective questObjective) {
        this.heroQuestObjectives.put(questObjective.getQuestObjectiveId(), questObjective);
    }

    public boolean containsQuestObjective(int questObjectiveId) {
        return this.heroQuestObjectives.containsKey(questObjectiveId);
    }

    public QuestObjective getQuestObjective(int questObjectiveId) {
        return this.heroQuestObjectives.get(questObjectiveId);
    }

    public QuestObjective getQuestObjectiveContainsLocationNeeded(int locationId) {
        for (QuestObjective questObjective : this.getHeroQuestObjectives().values()) {
            if (questObjective instanceof QuestClearLocationObjective questClearLocationObjective) {
                if (questClearLocationObjective.getLocationId().equals(locationId)) {
                    return questClearLocationObjective;
                }
            }
        }

        return null;
    }

    private Quest findQuestByObjective(int questObjectiveId) {
        for (Quest quest : this.getHeroAcceptedQuest().values()) {
            for (QuestObjective questObjective : quest.getQuestObjectives()) {
                if (questObjective.getQuestObjectiveId() == questObjectiveId) {
                    return quest;
                }
            }
        }

        return null;
    }

    public void checkQuestCompletion(Hero hero, int questObjectiveId) {
        Quest quest = findQuestByObjective(questObjectiveId);
        if (quest != null) {
            quest.checkIfQuestIsCompleted(hero);
        }
    }

    public void checkIfQuestsAreCompleted(Hero hero) {
        for (Quest quest : this.getHeroAcceptedQuest().values()) {
            quest.checkIfQuestIsCompleted(hero);
        }
    }

    public void makeProgressInQuestObjective(Hero hero, int questObjectiveId) {
        QuestObjective questObjective = this.getQuestObjective(questObjectiveId);
        questObjective.printQuestObjectiveProgress(hero);
        questObjective.verifyQuestObjectiveCompletion(hero);
    }
}
