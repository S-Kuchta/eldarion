package kuchtastefan.quest.questObjectives;

import kuchtastefan.characters.hero.Hero;
import kuchtastefan.items.Item;
import kuchtastefan.items.ItemsLists;

public class QuestFindObjective extends QuestObjective {

    private final int objectiveItemId;

    public QuestFindObjective(String questObjectiveName, int objectiveItemId) {
        super(questObjectiveName);
        this.objectiveItemId = objectiveItemId;
    }

    @Override
    public void printQuestObjectiveAssignment(Hero hero) {
        Item item = ItemsLists.getItemMapIdItem().get(this.objectiveItemId);
        System.out.println("\tFind " + item.getName());
    }

    @Override
    public void checkIfQuestObjectiveIsCompleted(Hero hero) {
        Item item = ItemsLists.getItemMapIdItem().get(this.objectiveItemId);
        if (hero.getHeroInventory().getHeroInventory().containsKey(item)) {
            setCompleted(true);
            System.out.println("\tYou completed " + this.getQuestObjectiveName());
        }
    }

    @Override
    public void removeCompletedItemsOrEnemies(Hero hero) {
        Item item = ItemsLists.getItemMapIdItem().get(this.objectiveItemId);
        hero.getHeroInventory().removeItemFromItemList(item);
    }
}
