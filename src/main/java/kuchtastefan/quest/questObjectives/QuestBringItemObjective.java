package kuchtastefan.quest.questObjectives;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.items.questItem.QuestItem;

import java.util.Map;

public class QuestBringItemObjective extends QuestObjective {

    private final Map<QuestItem, Integer> questItemAndCountNeeded;

    public QuestBringItemObjective(Map<QuestItem, Integer> questItemAndCountNeeded) {
        this.questItemAndCountNeeded = questItemAndCountNeeded;
    }

    @Override
    public void checkCompleted(Hero hero) {
        if (hero.getHeroInventory().checkIfHeroInventoryContainsNeededItemsIfTrueRemoveIt(this.questItemAndCountNeeded, false)) {
            System.out.println("\t--> You completed quest objective <--");
            setCompleted(true);
        } else {
            setCompleted(false);
        }
    }

    public Map<QuestItem, Integer> getQuestItemAndCountNeeded() {
        return questItemAndCountNeeded;
    }
}
