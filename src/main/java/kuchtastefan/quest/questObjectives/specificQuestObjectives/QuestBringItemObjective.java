package kuchtastefan.quest.questObjectives.specificQuestObjectives;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.QuestObjectiveTarget;
import kuchtastefan.quest.questObjectives.RemoveObjectiveProgress;
import lombok.Getter;

@Getter
public class QuestBringItemObjective extends QuestObjective implements RemoveObjectiveProgress {

    private final int objectiveItemId;
    private final int itemCountNeeded;

    public QuestBringItemObjective(String questObjectiveName, int objectiveItemId, int itemCountNeeded, int questObjectiveId) {
        super(questObjectiveId, questObjectiveName);
        this.objectiveItemId = objectiveItemId;
        this.itemCountNeeded = itemCountNeeded;
    }

    @Override
    public void questObjectiveAssignment(Hero hero) {
        Item item = ItemDB.returnItemFromDB(this.objectiveItemId);
        System.out.println("\tBring " + hero.getHeroInventory().getItemCount(item) + "/" + itemCountNeeded + " " + item.getName() );
    }

    @Override
    public void checkIfQuestObjectiveIsCompleted(Hero hero) {
        Item item = ItemDB.returnItemFromDB(this.objectiveItemId);
        if (hero.getHeroInventory().hasRequiredItems(item, this.itemCountNeeded)) {
            setCompleted(true);
            System.out.println("\t" + "You completed " + this.getQuestObjectiveName() + " quest objective");
        }
    }

    @Override
    public boolean makeProgressInQuestObjective(QuestObjectiveTarget questObjectiveTarget, Hero hero) {
        return false;
    }

    @Override
    public void removeCompletedQuestObjectiveAssignment(Hero hero) {
        Item item = ItemDB.returnItemFromDB(this.objectiveItemId);
        hero.getHeroInventory().removeItemFromHeroInventory(item, this.itemCountNeeded);
    }
}
