package kuchtastefan.quest.questObjectives.specificQuestObjectives;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.world.location.LocationDB;
import kuchtastefan.world.location.QuestLocation;
import lombok.Getter;

@Getter
public class QuestClearLocationObjective extends QuestObjective {
    private final Integer locationId;

    public QuestClearLocationObjective(String questObjectiveName, Integer locationId, int id) {
        super(id, questObjectiveName);
        this.locationId = locationId;
    }

    @Override
    public void printQuestObjectiveProgress(Hero hero) {
        String cleared = this.isCompleted() ? "Cleared" : "Not Cleared Yet";
        System.out.println("\tClear " + LocationDB.returnLocation(this.locationId).getLocationName() + " -> " + cleared);
    }

    @Override
    public void verifyQuestObjectiveCompletion(Hero hero) {
        QuestLocation questLocation = (QuestLocation) LocationDB.returnLocation(this.locationId);
        if (questLocation != null && questLocation.isCompleted()) {
            this.setCompleted(hero, true);
        }
    }
}
