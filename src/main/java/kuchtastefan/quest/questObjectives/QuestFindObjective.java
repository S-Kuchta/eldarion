package kuchtastefan.quest.questObjectives;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.constant.ConstantSymbol;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import kuchtastefan.utility.ConsoleColor;

public class QuestFindObjective extends QuestObjective implements RemoveObjectiveProgress {

    private final int objectiveItemId;

    public QuestFindObjective(String questObjectiveName, int objectiveItemId) {
        super(questObjectiveName);
        this.objectiveItemId = objectiveItemId;
    }

    @Override
    public void printQuestObjectiveAssignment(Hero hero) {
        Item item = ItemDB.returnItemFromDB(this.objectiveItemId);
        System.out.println("\tFind " + ConsoleColor.YELLOW + item.getName() + ConsoleColor.RESET);
    }

    @Override
    public void checkIfQuestObjectiveIsCompleted(Hero hero) {
        Item item = ItemDB.returnItemFromDB(this.objectiveItemId);
        if (hero.getHeroInventory().getHeroInventory().containsKey(item)) {
            setCompleted(true);
            System.out.println("\t" + ConstantSymbol.QUEST_OBJECTIVE_SYMBOL + " You completed "
                    + ConsoleColor.YELLOW + this.getQuestObjectiveName() + ConsoleColor.RESET
                    + " quest objective " + ConstantSymbol.QUEST_OBJECTIVE_SYMBOL);
        }
    }

    @Override
    public void removeCompletedQuestObjectiveAssignment(Hero hero) {
        Item item = ItemDB.returnItemFromDB(this.objectiveItemId);
        hero.getHeroInventory().removeItemFromHeroInventory(item);
    }
}
