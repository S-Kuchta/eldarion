package kuchtastefan.quest.questObjectives;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.items.Item;
import kuchtastefan.items.ItemsLists;

public class QuestUseItemObjective extends QuestObjective {

    private final int itemId;
    private final boolean removeItemAfterUse;

    public QuestUseItemObjective(String questObjectiveName, int itemId, boolean removeItemAfterUse) {
        super(questObjectiveName);
        this.itemId = itemId;
        this.removeItemAfterUse = removeItemAfterUse;
    }

    @Override
    public void printQuestObjectiveAssignment(Hero hero) {

    }

    @Override
    public void checkIfQuestObjectiveIsCompleted(Hero hero) {

    }

    @Override
    public void removeCompletedItemsOrEnemies(Hero hero) {
        Item item = ItemsLists.getItemMapIdItem().get(this.itemId);
        if (this.removeItemAfterUse) {
            hero.getHeroInventory().removeItemFromItemList(item);
        }
    }
}
