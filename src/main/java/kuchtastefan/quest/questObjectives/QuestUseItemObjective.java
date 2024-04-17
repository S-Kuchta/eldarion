package kuchtastefan.quest.questObjectives;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import kuchtastefan.item.questItem.UsableQuestItem;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.world.location.Location;
import kuchtastefan.world.location.LocationDB;
import kuchtastefan.world.location.locationStage.LocationStage;
import lombok.Getter;

@Getter
public class QuestUseItemObjective extends QuestObjective implements RemoveObjectiveProgress {

    private final int itemToUseId;
    private final int locationId;
    private final int locationStageId;

    public QuestUseItemObjective(String questObjectiveName, int itemToUseId, int locationId, int locationStageId) {
        super(questObjectiveName);
        this.itemToUseId = itemToUseId;
        this.locationId = locationId;
        this.locationStageId = locationStageId;
    }

    @Override
    public void printQuestObjectiveAssignment(Hero hero) {
        Location location = LocationDB.returnLocation(this.locationId);
        LocationStage locationStage = location.getLocationStages().get(this.locationStageId);
        Item item = ItemDB.returnItemFromDB(this.itemToUseId);

        System.out.println("\tUse " + ConsoleColor.YELLOW + item.getName() + ConsoleColor.RESET + " in "
                + location.getLocationName() + "  " + locationStage.getStageName());
    }

    @Override
    public void checkIfQuestObjectiveIsCompleted(Hero hero) {
            Item item = hero.getHeroInventory().returnItemFromInventory(this.itemToUseId);
            if (item instanceof UsableQuestItem usableQuestItem && usableQuestItem.isWasUsed()) {
                System.out.println("\t" + " You completed "
                        + ConsoleColor.YELLOW + this.getQuestObjectiveName() + ConsoleColor.RESET
                        + " quest objective ");
            }
    }

    @Override
    public void removeCompletedQuestObjectiveAssignment(Hero hero) {
            hero.getHeroInventory().removeItemFromHeroInventory(hero.getHeroInventory().returnItemFromInventory(this.itemToUseId));
    }
}
