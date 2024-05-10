package kuchtastefan.quest.questObjectives.specificQuestObjectives;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.QuestObjectiveTarget;
import kuchtastefan.world.location.LocationDB;
import kuchtastefan.utility.ConsoleColor;
import lombok.Getter;

@Getter
public class QuestClearLocationObjective extends QuestObjective {
    private final Integer locationId;

    public QuestClearLocationObjective(String questObjectiveName, Integer locationId, int questObjectiveId) {
        super(questObjectiveId, questObjectiveName);
        this.locationId = locationId;
    }

    @Override
    public void questObjectiveAssignment(Hero hero) {
        String cleared = this.isCompleted() ? "Cleared" : "Not Cleared Yet";
        System.out.println("\tClear " + LocationDB.returnLocation(this.locationId).getLocationName() + " -> " + cleared);
        checkIfQuestObjectiveIsCompleted(hero);
    }

    @Override
    public void checkIfQuestObjectiveIsCompleted(Hero hero) {
        if (!hero.getDiscoveredLocationList().isEmpty() && hero.getDiscoveredLocationList().get(this.locationId) != null) {
            if (!this.completed && hero.getDiscoveredLocationList().get(this.locationId).isCleared()) {
                this.setCompleted(hero, true);
            }
        }
    }
}
