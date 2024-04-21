package kuchtastefan.quest.questObjectives.specificQuestObjectives;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.RemoveObjectiveProgress;

public class QuestFindItemObjective extends QuestObjective implements RemoveObjectiveProgress {

    private final int objectiveItemId;

    public QuestFindItemObjective(String questObjectiveName, int objectiveItemId) {
        super(questObjectiveName);
        this.objectiveItemId = objectiveItemId;
    }

    @Override
    public void printQuestObjectiveAssignment(Hero hero) {
        Item item = ItemDB.returnItemFromDB(this.objectiveItemId);
        System.out.println("\tFind " + item.getName());
    }

    @Override
    public void checkIfQuestObjectiveIsCompleted(Hero hero) {
        Item item = ItemDB.returnItemFromDB(this.objectiveItemId);
        if (hero.getHeroInventory().getHeroInventory().containsKey(item)) {
            setCompleted(true);
            System.out.println("\t" + "You completed " + this.getQuestObjectiveName() + " quest objective");
        }
    }

    @Override
    public void removeCompletedQuestObjectiveAssignment(Hero hero) {
        Item item = ItemDB.returnItemFromDB(this.objectiveItemId);
        hero.getHeroInventory().removeItemFromHeroInventory(item, 1);
    }
}
