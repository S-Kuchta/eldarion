package kuchtastefan.quest.questObjectives;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.items.Item;
import kuchtastefan.items.ItemsLists;
import kuchtastefan.regions.locations.LocationType;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public class QuestBringItemObjective extends QuestObjective {

    private final Integer objectiveItemId;
    private final Integer[] itemDropFromEnemy;
    private final int itemDropCountNeeded;
    private final LocationType[] locationType;


    public QuestBringItemObjective(String questObjectiveName, Integer[] itemDropFromEnemy,
                                   LocationType[] locationType, Integer objectiveItemId, int itemDropCountNeeded) {
        super(questObjectiveName);
        this.locationType = locationType;
        this.objectiveItemId = objectiveItemId;
        this.itemDropCountNeeded = itemDropCountNeeded;
        this.itemDropFromEnemy = itemDropFromEnemy;
    }

    @Override
    public void printQuestObjectiveAssignment(Hero hero) {

        Item questItem = ItemsLists.getItemMap().get(this.objectiveItemId);
        hero.getHeroInventory().getHeroInventory().putIfAbsent(questItem, 0);
        if (hero.getHeroInventory().getHeroInventory().get(questItem) < this.itemDropCountNeeded) {
            System.out.println("\tBring " + this.itemDropCountNeeded + "x " + questItem.getName() + " - You have: "
                    + hero.getHeroInventory().getHeroInventory().get(questItem) + " / " + this.itemDropCountNeeded);
        } else {
            System.out.println("\tBring " + this.itemDropCountNeeded + "x " + questItem.getName() + " - You have: "
                    + this.itemDropCountNeeded + " / " + this.itemDropCountNeeded);
        }
    }

    @Override
    public void checkIfQuestObjectiveIsCompleted(Hero hero) {
        Item questItem = ItemsLists.getItemMap().get(this.objectiveItemId);

        if (hero.getHeroInventory().checkIfHeroInventoryContainsNeededItemsIfTrueRemoveIt(
                new HashMap<>(Map.of(questItem, this.itemDropCountNeeded)), false)) {

            System.out.println("\t--> You completed " + getQuestObjectiveName() + " quest objective <--");
            setCompleted(true);
        } else {
            setCompleted(false);
        }
    }

    @Override
    public void removeCompletedItemsOrEnemies(Hero hero) {
        Item questItem = ItemsLists.getItemMap().get(this.objectiveItemId);

        hero.getHeroInventory().checkIfHeroInventoryContainsNeededItemsIfTrueRemoveIt(
                new HashMap<>(Map.of(questItem, this.itemDropCountNeeded)), true);
    }

    public boolean checkLocation(LocationType locationTypeParam) {
        return Arrays.asList(this.locationType).contains(locationTypeParam);
    }

    public boolean checkEnemy(Integer questEnemyId) {
        return Arrays.asList(this.itemDropFromEnemy).contains(questEnemyId);
    }
}
