package kuchtastefan.quest.questObjectives;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.items.questItem.QuestItem;

import java.util.Map;

public class QuestBringItemObjective extends QuestObjective {

    private final Map<QuestItem, Integer> questItemAndCountNeeded;


    public QuestBringItemObjective(String questObjectiveName, Map<QuestItem, Integer> questItemAndCountNeeded) {
        super(questObjectiveName);
        this.questItemAndCountNeeded = questItemAndCountNeeded;
    }

    @Override
    public void printQuestObjectiveAssignment(Hero hero) {
        for (Map.Entry<QuestItem, Integer> questItem : this.questItemAndCountNeeded.entrySet()) {
            System.out.println("\tBring " + questItem.getValue() + "x - " + questItem.getKey() );
        }
    }

    @Override
    public void checkQuestObjectiveCompleted(Hero hero) {
        if (hero.getHeroInventory().checkIfHeroInventoryContainsNeededItemsIfTrueRemoveIt(this.questItemAndCountNeeded, false)) {
            System.out.println("\t--> You completed " + getQuestObjectiveName() + " quest objective <--");
            setCompleted(true);
        } else {
            setCompleted(false);
        }
    }

    @Override
    public void removeCompletedItemsOrEnemies(Hero hero) {

    }

    public Map<QuestItem, Integer> getQuestItemAndCountNeeded() {
        return questItemAndCountNeeded;
    }
}
