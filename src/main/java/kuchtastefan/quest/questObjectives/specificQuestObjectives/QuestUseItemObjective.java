package kuchtastefan.quest.questObjectives.specificQuestObjectives;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import kuchtastefan.item.specificItems.questItem.UsableQuestItem;
import kuchtastefan.quest.questObjectives.ConnectedWithItem;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.RemoveObjectiveProgress;
import kuchtastefan.world.location.Location;
import kuchtastefan.world.location.LocationDB;
import kuchtastefan.world.location.locationStage.LocationStage;
import lombok.Getter;

@Getter
public class QuestUseItemObjective extends QuestObjective implements RemoveObjectiveProgress, ConnectedWithItem {

    private final int itemId;
    private final int locationId;
    private final int locationStageId;

    public QuestUseItemObjective(String questObjectiveName, int itemId, int locationId, int locationStageId, int questObjectiveId) {
        super(questObjectiveId, questObjectiveName);
        this.itemId = itemId;
        this.locationId = locationId;
        this.locationStageId = locationStageId;
    }

    @Override
    public void printQuestObjectiveProgress(Hero hero) {
        Location location = LocationDB.returnLocation(this.locationId);
        LocationStage locationStage = location.getLocationStages().get(this.locationStageId);
        Item item = ItemDB.returnItemFromDB(this.itemId);

        System.out.println("\tUse " + item.getName() + " in " + location.getLocationName() + "  " + locationStage.getStageName());
    }

    @Override
    public void verifyQuestObjectiveCompletion(Hero hero) {
        Item item = hero.getHeroInventory().getItemFromInventory(this.itemId);
        if (!this.completed && item instanceof UsableQuestItem usableQuestItem && usableQuestItem.isWasUsed()) {
            setCompleted(hero, true);
        }
    }

    @Override
    public void removeCompletedQuestObjectiveAssignment(Hero hero) {
        hero.getHeroInventory().removeItemFromHeroInventory(hero.getHeroInventory().getItemFromInventory(this.itemId), 1);
    }
}
