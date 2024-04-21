package kuchtastefan.service;

import com.google.gson.Gson;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.hint.HintDB;
import kuchtastefan.hint.HintName;
import kuchtastefan.item.ItemWithCount;
import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.item.wearableItem.WearableItemQuality;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

import java.util.ArrayList;
import java.util.List;

public class BlacksmithingService {

    public void blacksmithingMenu(Hero hero) {
        HintDB.printHint(HintName.BLACKSMITH_HINT);

        PrintUtil.printMenuHeader("Blacksmithing");
        PrintUtil.printMenuOptions("Go back", "Hero Inventory");

        final int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
            }
            case 1 -> this.selectItem(hero);
            default -> PrintUtil.printEnterValidInput();
        }
    }

    private void selectItem(Hero hero) {
        hero.getHeroInventory().printHeroInventoryByClass(WearableItem.class, 1, hero);

        final int choice = InputUtil.intScanner();
        if (choice == 0) {
            blacksmithingMenu(hero);
        } else {
            List<WearableItem> items = new ArrayList<>(hero.getHeroInventory().returnHeroInventoryByClass(WearableItem.class).keySet());
            if (choice - 1 < items.size()) {
                itemMenu(hero, items.get(choice - 1));
            } else {
                PrintUtil.printEnterValidInput();
            }
        }
    }

    private void itemMenu(Hero hero, WearableItem wearableItem) {
        PrintUtil.printMenuHeader(wearableItem.getName());
        PrintUtil.printMenuOptions("Go back", "Refinement item", "Dismantle item");

        final int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> selectItem(hero);
            case 1 -> this.refineItemQuality(hero, wearableItem);
            case 2 -> this.dismantleItem(hero, wearableItem);

            default -> PrintUtil.printEnterValidInput();
        }
    }

    private void dismantleItem(Hero hero, WearableItem item) {
        ItemWithCount reagent = item.dismantleItem();
        System.out.println("\t" + ConsoleColor.YELLOW + reagent.item().getName()
                + ConsoleColor.RESET + " " + reagent.count() + "x obtained!");

        for (int i = 0; i < reagent.count(); i++) {
            hero.getHeroInventory().addItemWithNewCopyToItemList(reagent.item());
        }
    }

    private void refineItemQuality(Hero hero, WearableItem item) {
        if (item.getWearableItemQuality() != WearableItemQuality.BASIC) {
            System.out.println("\tYou can not refinement your item. Your item has the highest quality");
            return;
        }

        ItemWithCount requiredReagent = item.reagentNeededToRefine();
        if (!hero.getHeroInventory().hasRequiredItems(requiredReagent.item(), requiredReagent.count())) {
            return;
        }

        WearableItem refinedItem = new Gson().fromJson(new Gson().toJson(item), WearableItem.class);
        refinedItem.refineItem();
        hero.getHeroInventory().addItemToInventory(refinedItem);
        hero.getHeroInventory().removeItemFromHeroInventory(item, 1);
        hero.getHeroInventory().removeItemFromHeroInventory(requiredReagent.item(), requiredReagent.count());

        System.out.println("\tYou refinement your item " + refinedItem.getName() + " to " + refinedItem.getWearableItemQuality() + " quality");
    }
}
