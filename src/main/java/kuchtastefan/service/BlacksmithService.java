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
import kuchtastefan.quest.Quest;
import kuchtastefan.quest.QuestService;
import kuchtastefan.quest.QuestsMap;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import kuchtastefan.utility.RandomNumberGenerator;

import java.util.*;

public class BlacksmithService {

    public void blacksmithMenu(Hero hero, ItemsLists itemsLists) {
        final WearableItemVendorCharacter citySmithVendor = new WearableItemVendorCharacter("Reingron Bronzeback", 8,
                itemsLists.returnWearableItemListByItemLevel(hero.getLevel(), null));
        final CraftingReagentItemVendorCharacter cityReagentVendor = new CraftingReagentItemVendorCharacter("Krartunn Skulrarg", 8,
                itemsLists.returnCraftingReagentItemListByTypeAndItemLevel(CraftingReagentItemType.BLACKSMITH_REAGENT, hero.getLevel(), 0));

        HintUtil.printHint(HintName.BLACKSMITH_HINT);

        QuestService questService = new QuestService();
        Quest quest = questService.createNewQuest(QuestsMap.questsMap.get("shadowRaiders"),
                List.of(QuestsMap.returnQuestObjective("kill10Bandits")));

        QuestGiverCharacter questGiverCharacter = new QuestGiverCharacter("Gimli", 8,
                List.of(quest));
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
            case 1 -> this.refinementItemQuality(hero, itemsLists);
            case 2 -> this.dismantleItem(hero, itemsLists);
            case 3 -> citySmithVendor.vendorMenu(hero);
            case 4 -> cityReagentVendor.vendorMenu(hero);
            case 5 -> questGiverCharacter.questGiverMenu(hero);
            default -> System.out.println("Enter valid input");
        }
    }

    public void refinementItemQuality(Hero hero, ItemsLists itemsLists) {
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
                    if (!item.getKey().getItemQuality().equals(WearableItemQuality.SUPERIOR)) {
                        System.out.print("\t" + index + ". (" + item.getValue() + "x) ");
                        PrintUtil.printItemDescription(item.getKey(), false, hero);
                        refinementItemMap.put(item.getKey(), itemsNeededToRefinement(item.getKey(), itemsLists));
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
                    System.out.println("\tInter valid input");
                }
            }

            if (wearableItem == null) {
                break;
            } else {
                WearableItemQuality wearableItemQuality = wearableItem.getItemQuality();
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
                    System.out.println("\t\tYou can not refinement your item. Your item has the highest quality");
                    PrintUtil.printLongDivider();
                }
            }
        }
    }

    private Map<CraftingReagentItem, Integer> itemsNeededToRefinement(WearableItem wearableItem, ItemsLists itemsLists) {
        List<CraftingReagentItem> tempList = itemsLists.returnCraftingReagentItemListByTypeAndItemLevel(CraftingReagentItemType.BLACKSMITH_REAGENT, wearableItem.getItemLevel(), null);
        Map<CraftingReagentItem, Integer> itemsNeededForRefinement = new HashMap<>();
        int itemsNeededToRefinement = 0;

        CraftingReagentItem craftingReagentItem = tempList.get(RandomNumberGenerator.getRandomNumber(0, tempList.size() - 1));
        if (wearableItem.getItemQuality().equals(WearableItemQuality.BASIC)) {
            itemsNeededToRefinement = wearableItem.getItemLevel() * 3;
        }

        if (wearableItem.getItemQuality().equals(WearableItemQuality.IMPROVED)) {
            itemsNeededToRefinement = wearableItem.getItemLevel() * 4;
        }

        itemsNeededForRefinement.put(craftingReagentItem, itemsNeededToRefinement);

        System.out.println("\t\tTo refinement you need: " + craftingReagentItem.getName() + " (" + itemsNeededToRefinement + "x)");
        return itemsNeededForRefinement;
    }

    private void afterSuccessFullRefinementItem(WearableItem wearableItem, Hero hero) {
        wearableItem.setItemAbilities(wearableItem);
        wearableItem.setPrice(wearableItem.getPrice() * 2);
        hero.getHeroInventory().addItemToItemList(wearableItem);

        PrintUtil.printLongDivider();
        System.out.println("\tYou refinement your item " + wearableItem.getName() + " to " + wearableItem.getItemQuality() + " quality");
        PrintUtil.printLongDivider();
        hero.equipItem(wearableItem);
    }

    public void dismantleItem(Hero hero, ItemsLists itemsLists) {

        PrintUtil.printDivider();
        System.out.println("\t\tDismantle item");
        PrintUtil.printDivider();

        List<WearableItem> tempItemList = printItemList(hero);
        WearableItem wearableItem = selectItem(tempItemList);
        if (wearableItem == null) {
            return;
        }

        PrintUtil.printLongDivider();
        List<CraftingReagentItem> tempList = itemsLists.returnCraftingReagentItemListByTypeAndItemLevel(
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
                System.out.println("Inter valid input");
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
