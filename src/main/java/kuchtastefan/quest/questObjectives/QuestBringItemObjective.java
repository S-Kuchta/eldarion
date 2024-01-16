package kuchtastefan.quest.questObjectives;


import kuchtastefan.characters.hero.Hero;
import kuchtastefan.items.questItem.QuestItem;
import kuchtastefan.regions.locations.LocationType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class QuestBringItemObjective extends QuestObjective {

//    private final Map<Item, Integer> questItemAndCountNeeded;
    private final QuestItem itemDropNeeded;
    private final String[] enemyNeededToItemDrop;
    private final int itemDropCountNeeded;
    private final LocationType[] locationsType;


    public QuestBringItemObjective(String questObjectiveName, String[] enemyNeededToItemDrop, LocationType[] locationsType, QuestItem itemDropNeeded, int itemDropCountNeeded) {
        super(questObjectiveName);
        this.locationsType = locationsType;
        this.itemDropNeeded = itemDropNeeded;
        this.itemDropCountNeeded = itemDropCountNeeded;
        this.enemyNeededToItemDrop = enemyNeededToItemDrop;
    }

    @Override
    public void printQuestObjectiveAssignment(Hero hero) {
//        for (Map.Entry<Item, Integer> questItem : this.questItemAndCountNeeded.entrySet()) {
            System.out.println("\tBring " + this.itemDropNeeded.getName() + "x - " + this.itemDropCountNeeded );
//        }
    }

    @Override
    public void checkQuestObjectiveCompleted(Hero hero) {
//        Map<Item, Integer> questItemAndCountNeeded = new HashMap<>();
//        questItemAndCountNeeded.put(this.itemDropNeeded, this.itemDropCountNeeded);
        if (hero.getHeroInventory().checkIfHeroInventoryContainsNeededItemsIfTrueRemoveIt(new HashMap<>(Map.of(this.itemDropNeeded, this.itemDropCountNeeded)), false)) {
            System.out.println("\t--> You completed " + getQuestObjectiveName() + " quest objective <--");
            setCompleted(true);
        } else {
            setCompleted(false);
        }
    }

    @Override
    public void removeCompletedItemsOrEnemies(Hero hero) {
//        Map<Item, Integer> questItemAndCountNeeded = new HashMap<>();
//        questItemAndCountNeeded.put(this.itemDropNeeded, this.itemDropCountNeeded);
        hero.getHeroInventory().checkIfHeroInventoryContainsNeededItemsIfTrueRemoveIt(new HashMap<>(Map.of(this.itemDropNeeded, this.itemDropCountNeeded)), true);
    }

    public boolean checkLocation(LocationType locationTypeParam) {
        for (LocationType locationType : this.locationsType) {
            if (locationType.equals(locationTypeParam)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkEnemy(String enemyNameParam) {
//        for (String enemyName : this.enemyNeededToItemDrop) {
//            if (enemyName.equals(enemyNameParam)) {
//                return true;
//            }
//        }
        return Arrays.asList(enemyNeededToItemDrop).contains(enemyNameParam);
//        return false;
    }

    public LocationType[] getLocationsType() {
        return locationsType;
    }

    public QuestItem getItemDropNeeded() {
        return itemDropNeeded;
    }

    public int getItemDropCountNeeded() {
        return itemDropCountNeeded;
    }

    public String[] getEnemyNeededToItemDrop() {
        return enemyNeededToItemDrop;
    }
}
