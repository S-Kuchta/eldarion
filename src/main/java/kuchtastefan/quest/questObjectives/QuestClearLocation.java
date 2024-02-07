package kuchtastefan.quest.questObjectives;

import kuchtastefan.characters.hero.Hero;
import lombok.Getter;

@Getter
public class QuestClearLocation extends QuestObjective {
    private final Integer locationId;

    public QuestClearLocation(String questObjectiveName, Integer locationId) {
        super(questObjectiveName);
        this.locationId = locationId;
    }

    @Override
    public void checkIfQuestObjectiveIsCompleted(Hero hero) {

    }

    @Override
    public void removeCompletedItemsOrEnemies(Hero hero) {

    }

    @Override
    public void printQuestObjectiveAssignment(Hero hero) {

    }
}
