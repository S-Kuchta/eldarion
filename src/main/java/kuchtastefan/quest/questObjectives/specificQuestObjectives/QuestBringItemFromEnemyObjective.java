package kuchtastefan.quest.questObjectives.specificQuestObjectives;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.npc.enemy.Enemy;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.QuestObjectiveTarget;
import kuchtastefan.quest.questObjectives.RemoveObjectiveProgress;
import lombok.Getter;

import java.util.Arrays;

@Getter
public class QuestBringItemFromEnemyObjective extends QuestObjective implements RemoveObjectiveProgress {

    private final Integer objectiveItemId;
    private final Integer[] npcId;
    private final int itemDropCountNeeded;


    public QuestBringItemFromEnemyObjective(String questObjectiveName, Integer[] npcId,
                                            Integer objectiveItemId, int itemDropCountNeeded) {
        super(questObjectiveName);
        this.objectiveItemId = objectiveItemId;
        this.itemDropCountNeeded = itemDropCountNeeded;
        this.npcId = npcId;
    }

    @Override
    public void questObjectiveAssignment(Hero hero) {
        Item questItem = ItemDB.returnItemFromDB(this.objectiveItemId);
        hero.getHeroInventory().getHeroInventory().putIfAbsent(questItem, 0);

        int itemCount = hero.getHeroInventory().getHeroInventory().get(questItem) < this.itemDropCountNeeded ?
                hero.getHeroInventory().getHeroInventory().get(questItem) : this.itemDropCountNeeded;

        System.out.println("\tBring " + this.itemDropCountNeeded + "x " + questItem.getName() + " - You have: "
                + itemCount + " / " + this.itemDropCountNeeded);
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

    @Override
    public boolean makeProgressInQuestObjective(QuestObjectiveTarget questObjectiveTarget, Hero hero) {
        if (questObjectiveTarget instanceof Enemy enemy) {
            for (int npcId : this.npcId) {
                if (enemy.getNpcId() == npcId) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean checkEnemy(Integer questEnemyId) {
        return Arrays.asList(this.npcId).contains(questEnemyId);
    }
}
