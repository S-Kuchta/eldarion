package kuchtastefan.world.event.specificEvent;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.hero.inventory.UsingHeroInventory;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import kuchtastefan.item.itemFilter.ItemFilter;
import kuchtastefan.item.specificItems.keyItem.KeyItem;
import kuchtastefan.item.specificItems.questItem.UsableQuestItem;
import kuchtastefan.item.usableItem.UsableItem;
import kuchtastefan.quest.QuestDB;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.QuestObjectiveDB;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.printUtil.PrintUtil;
import kuchtastefan.world.event.Event;

public class UseItemEvent extends Event implements UsingHeroInventory {

    private final int itemId;
    private boolean wasUsed;

    public UseItemEvent(int eventLevel, int itemId) {
        super(eventLevel);
        this.itemId = itemId;
        this.wasUsed = false;
    }

    @Override
    public boolean eventOccurs(Hero hero) {
        mainMenu(hero);
        return wasUsed;
    }

    @Override
    public void mainMenu(Hero hero) {
        PrintUtil.printMenuOptions("Go back", "Quest items", "Keys");
        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
            }
            case 1 -> hero.getHeroInventory().selectItem(hero, UsableQuestItem.class, new ItemFilter(), this, 1);
            case 2 -> hero.getHeroInventory().selectItem(hero, KeyItem.class, new ItemFilter(), this, 1);
            default -> PrintUtil.printEnterValidInput();
        }
    }

    @Override
    public boolean itemOptions(Hero hero, Item item) {
        Item neededItem = ItemDB.returnItemFromDB(this.itemId);

        if (neededItem.equals(item)) {
            this.wasUsed = UsableItem.useItem(hero, item, this);
            if (item instanceof UsableQuestItem usableQuestItem) {
                QuestObjective questObjective = QuestObjectiveDB.getQuestObjectiveById(usableQuestItem.getQuestObjectiveId());
                usableQuestItem.setWasUsed(this.wasUsed);
                questObjective.printProgress(hero);
                questObjective.verifyQuestObjectiveCompletion(hero);
                QuestDB.findQuestByObjectiveId(usableQuestItem.getQuestObjectiveId()).checkIfQuestIsCompleted(hero);
            }
        } else {
            System.out.println("\tThis item does not fit here!");
            mainMenu(hero);
            this.wasUsed = false;
        }

        return this.wasUsed;
    }

}
