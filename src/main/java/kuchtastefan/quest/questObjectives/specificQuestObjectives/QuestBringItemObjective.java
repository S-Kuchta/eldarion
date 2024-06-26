package kuchtastefan.quest.questObjectives.specificQuestObjectives;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.hero.save.item.HeroItem;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import kuchtastefan.quest.questObjectives.ConnectedWithItem;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.ResetObjectiveProgress;
import lombok.Getter;

@Getter
public class QuestBringItemObjective extends QuestObjective implements ResetObjectiveProgress, ConnectedWithItem {

    private final int itemId;
    private final int itemCountNeeded;

    public QuestBringItemObjective(String questObjectiveName, int itemId, int itemCountNeeded, int id) {
        super(id, questObjectiveName);
        this.itemId = itemId;
        this.itemCountNeeded = itemCountNeeded;
    }

    @Override
    public void printQuestObjectiveProgress(Hero hero) {
        Item item = ItemDB.returnItemFromDB(this.itemId);
//        hero.getHeroInventoryManager().addItem(item,0);
//        hero.getHeroInventoryManager().getHeroItems().addEntityIfNotContains(new HeroItem(item.getItemId(), 0));
        System.out.println("\tBring " + hero.getHeroInventoryManager().getItemCount(item) + "/" + this.itemCountNeeded + " " + item.getName());
    }

    @Override
    public void verifyQuestObjectiveCompletion(Hero hero) {
        Item item = ItemDB.returnItemFromDB(this.itemId);
        if (hero.getHeroInventoryManager().hasRequiredItems(item, this.itemCountNeeded)) {
            setCompleted(hero, true);
        }
    }

    @Override
    public void resetCompletedQuestObjectiveAssignment(Hero hero) {
        Item item = ItemDB.returnItemFromDB(this.itemId);
        hero.getHeroInventoryManager().removeItem(item, this.itemCountNeeded);
    }
}
