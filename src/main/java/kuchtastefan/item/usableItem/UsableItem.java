package kuchtastefan.item.usableItem;

import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.hero.inventory.UsingHeroInventory;
import kuchtastefan.item.Item;
import kuchtastefan.item.specificItems.consumeableItem.ConsumableItem;
import kuchtastefan.item.specificItems.wearableItem.WearableItem;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

public interface UsableItem {

    boolean useItem(Hero hero);

    static String returnUseItemText(Item item) {
        if (item instanceof ConsumableItem) {
            return "Consume item";
        }

        if (item instanceof WearableItem) {
            return "Equip item";
        }

        return "Use item";
    }

    static boolean useItem(Hero hero, Item item, UsingHeroInventory usingHeroInventory) {
        if (item instanceof UsableItem usableItem) {
            PrintUtil.printMenuHeader(item.getName());
            PrintUtil.printMenuOptions("Go back", returnUseItemText(item));

            final int choice = InputUtil.intScanner();
            switch (choice) {
                case 0 -> usingHeroInventory.mainMenu(hero);
                case 1 -> {
                    return usableItem.useItem(hero);
                }

                default -> PrintUtil.printEnterValidInput();
            }
        }

        usingHeroInventory.mainMenu(hero);
        return false;
    }
}
