package kuchtastefan.quest.questObjectives.specificQuestObjectives;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import kuchtastefan.item.specificItems.questItem.UsableQuestItem;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.RemoveObjectiveProgress;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.world.location.Location;
import kuchtastefan.world.location.LocationDB;
import kuchtastefan.world.location.locationStage.LocationStage;
import lombok.Getter;

//@Getter
public class QuestUseItemObjective extends QuestObjective implements RemoveObjectiveProgress {

    private final int itemToUseId;
    private final int locationId;
    private final int locationStageId;

    public QuestUseItemObjective(String questObjectiveName, int itemToUseId, int locationId, int locationStageId, int questObjectiveId) {
        super(questObjectiveId, questObjectiveName);
        this.itemToUseId = itemToUseId;
        this.locationId = locationId;
        this.locationStageId = locationStageId;
    }

    @Override
    public void questObjectiveAssignment(Hero hero) {
        Location location = LocationDB.returnLocation(this.locationId);
        LocationStage locationStage = location.getLocationStages().get(this.locationStageId);
        Item item = ItemDB.returnItemFromDB(this.itemToUseId);

        System.out.println("\tUse " + item.getName() + " in " + location.getLocationName() + "  " + locationStage.getStageName());
        checkIfQuestObjectiveIsCompleted(hero);
    }

    @Override
    public void checkIfQuestObjectiveIsCompleted(Hero hero) {
        Item item = hero.getHeroInventory().getItemFromInventory(this.itemToUseId);
        if (!this.completed && item instanceof UsableQuestItem usableQuestItem && usableQuestItem.isWasUsed()) {
            setCompleted(hero, true);
        }
    }

    @Override
    public void removeCompletedQuestObjectiveAssignment(Hero hero) {
        hero.getHeroInventory().removeItemFromHeroInventory(hero.getHeroInventory().getItemFromInventory(this.itemToUseId), 1);
    }
}
