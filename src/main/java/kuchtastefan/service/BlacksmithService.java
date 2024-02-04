package kuchtastefan.service;

import com.google.gson.Gson;
import kuchtastefan.characters.QuestGiverCharacter;
import kuchtastefan.characters.hero.Hero;
import kuchtastefan.characters.vendor.CraftingReagentItemVendorCharacter;
import kuchtastefan.characters.vendor.WearableItemVendorCharacter;
import kuchtastefan.hint.HintName;
import kuchtastefan.hint.HintUtil;
import kuchtastefan.items.ItemsLists;
import kuchtastefan.items.craftingItem.CraftingReagentItem;
import kuchtastefan.items.craftingItem.CraftingReagentItemType;
import kuchtastefan.items.wearableItem.WearableItem;
import kuchtastefan.items.wearableItem.WearableItemQuality;
import kuchtastefan.quest.QuestList;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import kuchtastefan.utility.RandomNumberGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlacksmithService {

    public void blacksmithMenu(Hero hero) {
        final WearableItemVendorCharacter citySmithVendor = new WearableItemVendorCharacter("Reingron Bronzeback", 8,
                ItemsLists.returnWearableItemListByItemLevel(hero.getLevel(), null, false));
        final CraftingReagentItemVendorCharacter cityReagentVendor = new CraftingReagentItemVendorCharacter("Krartunn Skulrarg", 8,
                ItemsLists.returnCraftingReagentItemListByTypeAndItemLevel(CraftingReagentItemType.BLACKSMITH_REAGENT, hero.getLevel(), 0));

        HintUtil.printHint(HintName.BLACKSMITH_HINT);

        QuestGiverCharacter questGiverCharacter = new QuestGiverCharacter("Gimli", 8);
        questGiverCharacter.addQuest(QuestList.questList.get(1));
        questGiverCharacter.setNameBasedOnQuestsAvailable(hero);

        PrintUtil.printDivider();
        System.out.println("\t\tBlacksmith");
        PrintUtil.printDivider();

        System.out.println("\t0. Go back");
        System.out.println("\t1. Refinement item");
        System.out.println("\t2. Dismantle item");
        System.out.println("\t3. " + citySmithVendor.getName() + " (Wearable Items Merchant)");
        System.out.println("\t4. " + cityReagentVendor.getName() + " (Blacksmith reagents Merchant)");
        System.out.println("\t5. " + questGiverCharacter.getName());
        final int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
            }
            case 1 -> this.refinementItemQuality(hero);
            case 2 -> this.dismantleItem(hero);
            case 3 -> citySmithVendor.vendorMenu(hero);
            case 4 -> cityReagentVendor.vendorMenu(hero);
            case 5 -> questGiverCharacter.questGiverMenu(hero);
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
            PrintUtil.printDivider();
            System.out.println("\t\tRefinement item");
            PrintUtil.printDivider();

            Map<WearableItem, Map<CraftingReagentItem, Integer>> refinementItemMap = new HashMap<>();
            List<WearableItem> tempItemList = new ArrayList<>();

            int index = 1;
            System.out.println("\t0. Go back");
            if (hero.getHeroInventory().getHeroInventory().isEmpty()) {
                System.out.println("\tItem list is empty");
            } else {
                for (Map.Entry<WearableItem, Integer> item : hero.getHeroInventory().returnInventoryWearableItemMap().entrySet()) {
                    if (!item.getKey().getWearableItemQuality().equals(WearableItemQuality.SUPERIOR)) {
                        System.out.print("\t" + index + ". (" + item.getValue() + "x) ");
                        PrintUtil.printItemDescription(item.getKey(), false, hero);
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
                    if (hero.getHeroInventory().checkIfHeroInventoryContainsNeededItemsIfTrueRemoveIt(refinementItemMap.get(tempItemList.get(choice - 1)), true)) {
                        wearableItem = tempItemList.get(choice - 1);
                        break;
                    } else {
                        PrintUtil.printLongDivider();
                        System.out.println("\tYou don't have enough crafting reagents to refinement " + tempItemList.get(choice - 1).getName());
                        PrintUtil.printLongDivider();
                    }
                    break;
                } catch (IndexOutOfBoundsException e) {
                    PrintUtil.printEnterValidInput();
                }
            }

            if (wearableItem == null) {
                break;
            } else {
                WearableItemQuality wearableItemQuality = wearableItem.getWearableItemQuality();
                Gson gson = new Gson();
                WearableItem itemCopy = gson.fromJson(gson.toJson(wearableItem), WearableItem.class);

                if (wearableItemQuality == WearableItemQuality.BASIC) {
                    hero.getHeroInventory().removeItemFromItemList(wearableItem);
                    itemCopy.setItemQuality(WearableItemQuality.IMPROVED);
                    afterSuccessFullRefinementItem(itemCopy, hero);

                } else if (wearableItemQuality == WearableItemQuality.IMPROVED) {
                    hero.getHeroInventory().removeItemFromItemList(wearableItem);
                    itemCopy.setItemQuality(WearableItemQuality.SUPERIOR);
                    afterSuccessFullRefinementItem(itemCopy, hero);

                } else {
                    PrintUtil.printLongDivider();
                    System.out.println("\t--- You can not refinement your item. Your item has the highest quality ---");
                    PrintUtil.printLongDivider();
                }
            }
        }
    }

    private Map<CraftingReagentItem, Integer> itemsNeededToRefinement(WearableItem wearableItem) {
        List<CraftingReagentItem> tempList = ItemsLists.returnCraftingReagentItemListByTypeAndItemLevel(CraftingReagentItemType.BLACKSMITH_REAGENT, wearableItem.getItemLevel(), null);
        Map<CraftingReagentItem, Integer> itemsNeededForRefinement = new HashMap<>();
        int itemsNeededToRefinement = 0;

        CraftingReagentItem craftingReagentItem = tempList.get(RandomNumberGenerator.getRandomNumber(0, tempList.size() - 1));
        if (wearableItem.getWearableItemQuality().equals(WearableItemQuality.BASIC)) {
            itemsNeededToRefinement = wearableItem.getItemLevel() * 3;
        }

        if (wearableItem.getWearableItemQuality().equals(WearableItemQuality.IMPROVED)) {
            itemsNeededToRefinement = wearableItem.getItemLevel() * 4;
        }

        itemsNeededForRefinement.put(craftingReagentItem, itemsNeededToRefinement);

        System.out.println("\t\tTo refinement you need: " + craftingReagentItem.getName() + " (" + itemsNeededToRefinement + "x)");
        return itemsNeededForRefinement;
    }

    private void afterSuccessFullRefinementItem(WearableItem wearableItem, Hero hero) {
        wearableItem.increaseWearableItemAbilityValue(wearableItem);
        wearableItem.setPrice(wearableItem.getPrice() * 2);
        hero.getHeroInventory().addItemToItemList(wearableItem);

        PrintUtil.printLongDivider();
        System.out.println("\tYou refinement your item " + wearableItem.getName() + " to " + wearableItem.getWearableItemQuality() + " quality");
        PrintUtil.printLongDivider();
        hero.equipItem(wearableItem);
    }

    public void dismantleItem(Hero hero) {

        PrintUtil.printDivider();
        System.out.println("\t\tDismantle item");
        PrintUtil.printDivider();

        List<WearableItem> tempItemList = printItemList(hero);
        WearableItem wearableItem = selectItem(tempItemList);
        if (wearableItem == null) {
            return;
        }

        PrintUtil.printLongDivider();
        List<CraftingReagentItem> tempList = ItemsLists.returnCraftingReagentItemListByTypeAndItemLevel(
                CraftingReagentItemType.BLACKSMITH_REAGENT, wearableItem.getItemLevel(), null);

        CraftingReagentItem item = null;
        int numbersOfIteration = 0;
        for (int i = 0; i < RandomNumberGenerator.getRandomNumber(2, 4) + wearableItem.getItemLevel(); i++) {
            item = tempList.get(RandomNumberGenerator.getRandomNumber(0, tempList.size() - 1));
            hero.getHeroInventory().addItemToItemList(item);
            numbersOfIteration++;
        }

        assert item != null;
        System.out.println("\tYou dismantled " + wearableItem.getName()
                + ", you got " + item.getName()
                + "(" + numbersOfIteration + "x)");
        PrintUtil.printLongDivider();

        hero.unEquipItem(wearableItem);
        hero.getHeroInventory().removeItemFromItemList(wearableItem);
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

    private List<WearableItem> printItemList(Hero hero) {
        List<WearableItem> tempItemList = new ArrayList<>();
        int index = 1;
        System.out.println("\t0. Go back");
        if (hero.getHeroInventory().getHeroInventory().isEmpty()) {
            System.out.println("\tItem list is empty");
        } else {
            for (Map.Entry<WearableItem, Integer> item : hero.getHeroInventory().returnInventoryWearableItemMap().entrySet()) {
                tempItemList.add(item.getKey());
                System.out.print("\t" + index + ". (" + item.getValue() + "x) ");
                PrintUtil.printItemDescription(item.getKey(), false, hero);
                index++;
            }
        }

        return tempItemList;
    }
}
