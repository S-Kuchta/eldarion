package kuchtastefan.quest.questObjectives;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.constant.ConstantSymbol;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import kuchtastefan.utility.ConsoleColor;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
            System.out.println("\tBring " + this.itemDropCountNeeded + "x "
                    + ConsoleColor.YELLOW + questItem.getName() + ConsoleColor.RESET
                    + " - You have: "
                    + hero.getHeroInventory().getHeroInventory().get(questItem) + " / " + this.itemDropCountNeeded);
        } else {
            System.out.println("\tBring " + this.itemDropCountNeeded + "x " + questItem.getName() + " - You have: "
                    + this.itemDropCountNeeded + " / " + this.itemDropCountNeeded);
        }
    }

    @Override
    public void checkIfQuestObjectiveIsCompleted(Hero hero) {
        Item questItem = ItemDB.returnItemFromDB(this.objectiveItemId);

        if (hero.getHeroInventory().checkIfHeroInventoryContainsNeededItemsIfTrueRemoveIt(
                new HashMap<>(Map.of(questItem, this.itemDropCountNeeded)), false)) {

            System.out.println("\t" + ConstantSymbol.QUEST_OBJECTIVE_SYMBOL + " You completed "
                    + ConsoleColor.YELLOW + getQuestObjectiveName() + ConsoleColor.RESET
                    + " quest objective " + ConstantSymbol.QUEST_OBJECTIVE_SYMBOL);

            setCompleted(true);
        } else {
            setCompleted(false);
        }
    }

    @Override
    public void removeCompletedQuestObjectiveAssignment(Hero hero) {
        Item questItem = ItemDB.returnItemFromDB(this.objectiveItemId);

        hero.getHeroInventory().checkIfHeroInventoryContainsNeededItemsIfTrueRemoveIt(
                new HashMap<>(Map.of(questItem, this.itemDropCountNeeded)), true);
    }

    public boolean checkEnemy(Integer questEnemyId) {
        return Arrays.asList(this.itemDropFromEnemy).contains(questEnemyId);
    }
}
