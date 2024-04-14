package kuchtastefan.world.event.specificEvent;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import kuchtastefan.item.questItem.QuestItem;
import kuchtastefan.item.questItem.UsableQuestItem;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import kuchtastefan.world.event.Event;

public class UseItemEvent extends Event {

    private final int itemId;

    public UseItemEvent(int eventLevel, int itemId) {
        super(eventLevel);
        this.itemId = itemId;
    }

    @Override
    public boolean eventOccurs(Hero hero) {
        Item item = hero.getHeroInventory().returnItemFromInventory(this.itemId);

        System.out.println("Do you want to use item " + item.getName() + "?");
        PrintUtil.printIndexAndText("0", "No");
        PrintUtil.printIndexAndText("1", "Yes");

        while (true) {
            int input = InputUtil.intScanner();
            switch (input) {
                case 0 -> {
                    return false;
                }
                case 1 -> {
                    if (hero.getHeroInventory().getHeroInventory().containsKey(item)) {
                        System.out.println("\tYou are using " + item.getName());
                        if (item instanceof UsableQuestItem usableQuestItem) {
                            usableQuestItem.setWasUsed(true);
                        }
                        return true;
                    } else {
                        System.out.println("\tYou do not have needed item!");
                        return false;
                    }
                }
                default -> PrintUtil.printEnterValidInput();
            }
        }
    }
}
