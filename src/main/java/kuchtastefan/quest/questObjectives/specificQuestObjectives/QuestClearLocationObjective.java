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

    public QuestClearLocationObjective(String questObjectiveName, Integer locationId) {
        super(questObjectiveName);
        this.locationId = locationId;
    }

    @Override
    public void checkIfQuestObjectiveIsCompleted(Hero hero) {
        if (!hero.getDiscoveredLocationList().isEmpty()
                && hero.getDiscoveredLocationList().get(this.locationId) != null) {

            if (hero.getDiscoveredLocationList().get(this.locationId).isCleared()) {
                System.out.println("\t" + " You completed " + getQuestObjectiveName() + " quest objective");
                this.setCompleted(true);
            }
        }
    }

    @Override
    public boolean makeProgressInQuestObjective(QuestObjectiveTarget questObjectiveTarget, Hero hero) {
        return false;
    }

    @Override
    public void questObjectiveAssignment(Hero hero) {
        String cleared = this.isCompleted() ? "Cleared" : "Not Cleared Yet";
        System.out.println("\tClear " + LocationDB.returnLocation(this.locationId).getLocationName() + " -> " + cleared);
    }
}
