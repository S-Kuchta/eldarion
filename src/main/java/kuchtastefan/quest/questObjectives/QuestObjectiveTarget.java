package kuchtastefan.quest.questObjectives;

import kuchtastefan.character.hero.Hero;

public interface QuestObjectiveTarget {

//    void makeProgressInQuestObjective(Hero hero);

    static void makeProgressInQuestObjective(int questObjectiveId, Hero hero) {
        if (hero.getHeroQuests().containsQuestObjective(questObjectiveId)) {
            hero.getHeroQuests().getQuestObjective(questObjectiveId).questObjectiveAssignment(hero);
        }
    }
}
