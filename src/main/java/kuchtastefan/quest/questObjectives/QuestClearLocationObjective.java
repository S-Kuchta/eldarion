package kuchtastefan.quest.questObjectives;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.constant.ConstantSymbol;
import kuchtastefan.regions.locations.LocationMap;
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
                System.out.println("\t" + ConstantSymbol.QUEST_OBJECTIVE_SYMBOL + " You completed "
                        + ConsoleColor.YELLOW + getQuestObjectiveName() + ConsoleColor.RESET
                        + " quest objective " + ConstantSymbol.QUEST_OBJECTIVE_SYMBOL);
                this.setCompleted(true);
            }
        }
    }

    @Override
    public void removeCompletedItemsOrEnemies(Hero hero) {

    }

    @Override
    public void printQuestObjectiveAssignment(Hero hero) {
        String cleared = this.isCompleted() ? "Cleared" : "Not Cleared Yet";
        System.out.println("\tClear "
                + ConsoleColor.YELLOW
                + LocationMap.getMapIdLocation().get(this.locationId).getLocationName()
                + ConsoleColor.RESET
                + " -> " + cleared);
    }
}
