package kuchtastefan.service;

import com.google.gson.Gson;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.hint.HintDB;
import kuchtastefan.hint.HintName;
import kuchtastefan.item.ItemDB;
import kuchtastefan.item.craftingItem.CraftingReagentItem;
import kuchtastefan.item.craftingItem.CraftingReagentItemType;
import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.item.wearableItem.WearableItemQuality;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import kuchtastefan.utility.RandomNumberGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlacksmithService {

    private final InventoryMenuService inventoryMenuService;

    public BlacksmithService() {
        this.inventoryMenuService = new InventoryMenuService();
    }

    public void blacksmithMenu(Hero hero) {
        HintDB.printHint(HintName.BLACKSMITH_HINT);

        PrintUtil.printDivider();
        System.out.println("\t\tBlacksmith");
        PrintUtil.printDivider();

        PrintUtil.printIndexAndText("0", "Go back");
        System.out.println();
        PrintUtil.printIndexAndText("1", "Refinement item");
        System.out.println();
        PrintUtil.printIndexAndText("2", "Dismantle item");
        System.out.println();

        final int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
            }
            case 1 -> this.refinementItemQuality(hero);
            case 2 -> this.dismantleItem(hero);
            default -> PrintUtil.printEnterValidInput();
        }
    }

    /**
     * Allows the hero to refine wearable items in their inventory to enhance their quality.
     * The method presents a list of wearable items that can be refined, based on their current quality.
     * The hero must have the required crafting reagents to proceed with the refinement.
     * Successfully refined items are upgraded to the next quality level (e.g., BASIC to IMPROVED).
     * The method continues until the hero decides to go back.
     *
     * @param hero The hero whose inventory is being refined.
     */
    public void refinementItemQuality(Hero hero) {
        while (true) {
            // Print refinement menu header
            PrintUtil.printDivider();
            System.out.println("\t\tRefinement item");
            PrintUtil.printDivider();

            // Initialize a map to store items eligible for refinement along with required crafting reagents
            Map<WearableItem, Map<CraftingReagentItem, Integer>> refinementItemMap = new HashMap<>();
            List<WearableItem> tempItemList = new ArrayList<>();

            int index = 1;
            PrintUtil.printIndexAndText("0", "Go back");
            System.out.println();
            if (hero.getHeroInventory().getHeroInventory().isEmpty()) {
                System.out.println("\tItem list is empty");
            } else {
                for (Map.Entry<WearableItem, Integer> item : hero.getHeroInventory().returnInventoryWearableItemMap().entrySet()) {

                    // Check if the item's quality is eligible for refinement
                    if (item.getKey().getWearableItemQuality().equals(WearableItemQuality.BASIC)) {

                        // Print item description and add it to the refinement map and temporary item list
                        PrintUtil.printIndexAndText(String.valueOf(index), "(" + item.getValue() + "x) ");
                        item.getKey().printItemDescription(hero);

                        refinementItemMap.put(item.getKey(), itemsNeededToRefinement(item.getKey()));
                        tempItemList.add(item.getKey());
                        index++;
                    }
                }
            }

            WearableItem wearableItem = null;

            while (true) {
                int choice = InputUtil.intScanner();
                if (choice == 0) {
                    return;
                }
                try {
                    // Check if the hero has enough crafting reagents to refine the selected item
                    if (hero.getHeroInventory().checkIfHeroInventoryContainsNeededItemsIfTrueRemoveIt(refinementItemMap.get(tempItemList.get(choice - 1)), true)) {
                        wearableItem = tempItemList.get(choice - 1);
                        break;
                    } else {
                        System.out.println();
                        System.out.println(ConsoleColor.RED + "\tYou don't have enough crafting reagents to refinement " + tempItemList.get(choice - 1).getName() + ConsoleColor.RESET);
                    }
                    break;
                } catch (IndexOutOfBoundsException e) {
                    PrintUtil.printEnterValidInput();
                }
            }

            if (wearableItem == null) {
                break;
            } else {
                // Copy the selected item and upgrade its quality if eligible
                WearableItemQuality wearableItemQuality = wearableItem.getWearableItemQuality();
                Gson gson = new Gson();
                WearableItem itemCopy = gson.fromJson(gson.toJson(wearableItem), WearableItem.class);

                // Check the current quality of the item and upgrade it if possible
                if (wearableItemQuality == WearableItemQuality.BASIC) {
                    hero.getHeroInventory().removeItemFromHeroInventory(wearableItem);
                    itemCopy.setItemQuality(WearableItemQuality.IMPROVED);
                    afterSuccessFullRefinementItem(itemCopy, hero);
                } else {
                    PrintUtil.printLongDivider();
                    System.out.println("\t--- You can not refinement your item. Your item has the highest quality ---");
                    PrintUtil.printLongDivider();
                }
            }
        }
    }

    /**
     * Determines the items needed for refining a wearable item.
     *
     * @param wearableItem The wearable item to be refined.
     * @return A map containing the crafting reagent item needed for refinement and the quantity required.
     */
    private Map<CraftingReagentItem, Integer> itemsNeededToRefinement(WearableItem wearableItem) {
        List<CraftingReagentItem> tempList = ItemDB.returnCraftingReagentItemListByTypeAndItemLevel(CraftingReagentItemType.BLACKSMITH_REAGENT, wearableItem.getItemLevel(), null);
        Map<CraftingReagentItem, Integer> itemsNeededForRefinement = new HashMap<>();
        int itemsNeededToRefinement = 0;

        CraftingReagentItem craftingReagentItem = tempList.get(RandomNumberGenerator.getRandomNumber(0, tempList.size() - 1));
        if (wearableItem.getWearableItemQuality().equals(WearableItemQuality.BASIC)) {
            itemsNeededToRefinement = wearableItem.getItemLevel() * 3;
        }

        itemsNeededForRefinement.put(craftingReagentItem, itemsNeededToRefinement);

        System.out.println("\t\tTo refinement you need: " + craftingReagentItem.getName() + " (" + itemsNeededToRefinement + "x)");
        return itemsNeededForRefinement;
    }


    /**
     * Updates the wearable item after successful refinement, including increasing its ability value,
     * adjusting its price, and adding it to the hero's inventory.
     *
     * @param wearableItem The wearable item to be updated after refinement.
     * @param hero         The hero whose inventory the refined item will be added to.
     */
    private void afterSuccessFullRefinementItem(WearableItem wearableItem, Hero hero) {
        wearableItem.increaseWearableItemAbilityValue(wearableItem);
        wearableItem.setPrice(wearableItem.getPrice() * 2);
        hero.getHeroInventory().addItemToInventory(wearableItem);

        PrintUtil.printLongDivider();
        System.out.println("\tYou refinement your item " + wearableItem.getName() + " to " + wearableItem.getWearableItemQuality() + " quality");
        PrintUtil.printLongDivider();
        hero.equipItem(wearableItem);
    }

    /**
     * Dismantles a wearable item, adding crafting reagent items to the hero's inventory based on the item's level.
     *
     * @param hero The hero who is dismantling the item.
     */
    public void dismantleItem(Hero hero) {

        PrintUtil.printDivider();
        System.out.println("\t\tDismantle item");
        PrintUtil.printDivider();

        List<WearableItem> tempItemList = returnItemList(hero);
        WearableItem wearableItem = selectItem(tempItemList);
        if (wearableItem == null) {
            return;
        }


        List<CraftingReagentItem> tempList = ItemDB.returnCraftingReagentItemListByTypeAndItemLevel(
                CraftingReagentItemType.BLACKSMITH_REAGENT, wearableItem.getItemLevel(), null);

        CraftingReagentItem item = null;
        int numbersOfIteration = 0;
        for (int i = 0; i < RandomNumberGenerator.getRandomNumber(1, 2) + wearableItem.getItemLevel(); i++) {
            item = tempList.get(RandomNumberGenerator.getRandomNumber(0, tempList.size() - 1));
            hero.getHeroInventory().addItemToInventory(item);
            numbersOfIteration++;
        }

        assert item != null;
        PrintUtil.printLongDivider();
        System.out.println("\tYou dismantled " + wearableItem.getName()
                + ", you got " + item.getName()
                + "(" + numbersOfIteration + "x)");
        PrintUtil.printLongDivider();

        hero.unEquipItem(wearableItem);
        hero.getHeroInventory().removeItemFromHeroInventory(wearableItem);
    }

    private WearableItem selectItem(List<WearableItem> tempItemList) {
        while (true) {
            int choice = InputUtil.intScanner();
            if (choice == 0) {
                return null;
            }
            try {
                return tempItemList.get(choice - 1);

            } catch (IndexOutOfBoundsException e) {
                PrintUtil.printEnterValidInput();
            }
        }
    }

    private List<WearableItem> returnItemList(Hero hero) {
        List<WearableItem> tempItemList = hero.getHeroInventory().returnInventoryWearableItemMap().keySet().stream().toList();
        System.out.println();
        this.inventoryMenuService.printItemInventory(hero, hero.getHeroInventory().returnInventoryWearableItemMap());

        return tempItemList;
    }
}
