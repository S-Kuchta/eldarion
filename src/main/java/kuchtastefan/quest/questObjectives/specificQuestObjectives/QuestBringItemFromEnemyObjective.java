package kuchtastefan.quest.questObjectives.specificQuestObjectives;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.RemoveObjectiveProgress;
import lombok.Getter;

import java.util.Arrays;

@Getter
public class QuestBringItemFromEnemyObjective extends QuestObjective implements RemoveObjectiveProgress {

    private final Integer objectiveItemId;
    private final Integer[] itemDropFromEnemy;
    private final int itemDropCountNeeded;


    public QuestBringItemFromEnemyObjective(String questObjectiveName, Integer[] itemDropFromEnemy,
                                            Integer objectiveItemId, int itemDropCountNeeded) {
        super(questObjectiveName);
        this.objectiveItemId = objectiveItemId;
        this.itemDropCountNeeded = itemDropCountNeeded;
        this.itemDropFromEnemy = itemDropFromEnemy;
    }

    @Override
    public void printQuestObjectiveAssignment(Hero hero) {

        Item questItem = ItemDB.returnItemFromDB(this.objectiveItemId);
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
        Item questItem = ItemDB.returnItemFromDB(this.objectiveItemId);

        if (hero.getHeroInventory().hasRequiredItems(questItem, this.itemDropCountNeeded)) {
            System.out.println("\t" + " You completed " + getQuestObjectiveName() + " quest objective");
            setCompleted(true);
        } else {
            setCompleted(false);
        }
    }

    @Override
    public void removeCompletedQuestObjectiveAssignment(Hero hero) {
        Item questItem = ItemDB.returnItemFromDB(this.objectiveItemId);
        hero.getHeroInventory().removeItemFromHeroInventory(questItem, this.itemDropCountNeeded);
    }

    public boolean checkEnemy(Integer questEnemyId) {
        return Arrays.asList(this.itemDropFromEnemy).contains(questEnemyId);
    }
}
