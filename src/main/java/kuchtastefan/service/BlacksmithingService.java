package kuchtastefan.service;

import com.google.gson.Gson;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.hint.HintDB;
import kuchtastefan.hint.HintName;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemWithCount;
import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.item.wearableItem.WearableItemQuality;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import kuchtastefan.workshop.Workshop;

public class BlacksmithingService implements Workshop {

    public void workshopMenu(Hero hero) {
        HintDB.printHint(HintName.BLACKSMITH_HINT);

        PrintUtil.printMenuHeader("Blacksmithing");
        PrintUtil.printMenuOptions("Go back", "Hero Inventory");

        final int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
            }
            case 1 -> hero.getHeroInventory().selectItem(hero, WearableItem.class, this);
            default -> PrintUtil.printEnterValidInput();
        }
    }

    /**
     * This method displays a menu for a specific wearable item.
     * The user can choose to go back, refine the item, or dismantle the item.
     * The user's choice is handled by a switch statement.
     *
     * @param hero The hero who owns the item.
     * @param item The item to be managed.
     */
    public void itemMenu(Hero hero, Item item) {
        PrintUtil.printMenuHeader(item.getName());
        PrintUtil.printMenuOptions("Go back", "Refinement item", "Dismantle item");

        final int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> hero.getHeroInventory().selectItem(hero, WearableItem.class, this);
            case 1 -> this.refineItemQuality(hero, (WearableItem) item);
            case 2 -> this.dismantleItem(hero, (WearableItem) item);

            default -> PrintUtil.printEnterValidInput();
        }
    }

    /**
     * This method is used to dismantle a wearable item for a hero.
     * The item is dismantled into a reagent, which is then added to the hero's inventory.
     *
     * @param hero The hero for whom the item is to be dismantled.
     * @param item The wearable item to be dismantled.
     */
    private void dismantleItem(Hero hero, WearableItem item) {
        ItemWithCount reagent = item.dismantle();
        hero.getHeroInventory().removeItemFromHeroInventory(item, 1);

        hero.getHeroInventory().addItemWithNewCopyToItemList(reagent.item(), reagent.count());
        System.out.println("\t" + reagent.item().getName() + " " + reagent.count() + "x obtained!");
    }

    /**
     * This method is used to refine the quality of a wearable item for a hero.
     * The item can only be refined if its quality is BASIC.
     * The hero's inventory must contain the required reagents for the refinement process.
     * If the item is successfully refined, the refined item is added to the hero's inventory,
     * and the original item and the required reagents are removed from the hero's inventory.
     *
     * @param hero The hero for whom the item is to be refined.
     * @param item The wearable item to be refined.
     */
    private void refineItemQuality(Hero hero, WearableItem item) {
        if (item.getWearableItemQuality() != WearableItemQuality.BASIC) {
            System.out.println("\tYou can not refine your item. Your item has the highest quality");
            return;
        }

        ItemWithCount requiredReagent = item.reagentNeededToRefine();
        if (!hero.getHeroInventory().hasRequiredItems(requiredReagent.item(), requiredReagent.count())) {
            System.out.println("\tYou don't have enough reagents. Your can't refine your item");
            return;
        }

        WearableItem refinedItem = new Gson().fromJson(new Gson().toJson(item), WearableItem.class);
        refinedItem.refine();
        hero.getHeroInventory().addItemToInventory(refinedItem);
        hero.getHeroInventory().removeItemFromHeroInventory(item, 1);
        hero.getHeroInventory().removeItemFromHeroInventory(requiredReagent.item(), requiredReagent.count());

        System.out.println("\tYou refinement your item " + refinedItem.getName() + " to " + refinedItem.getWearableItemQuality() + " quality");
    }
}
