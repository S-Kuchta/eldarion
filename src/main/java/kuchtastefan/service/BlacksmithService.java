package kuchtastefan.service;

import com.google.gson.Gson;
import kuchtastefan.domain.Hero;
import kuchtastefan.item.ItemsLists;
import kuchtastefan.item.craftingItem.CraftingReagentItem;
import kuchtastefan.item.craftingItem.CraftingReagentItemType;
import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.item.wearableItem.WearableItemQuality;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import kuchtastefan.utility.RandomNumberGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BlacksmithService {
    public void refinementItemQuality(Hero hero) {
        while (true) {
            PrintUtil.printDivider();
            System.out.println("\t\tRefinement item");
            PrintUtil.printDivider();

            List<WearableItem> tempItemList = printItemList(hero);
            WearableItem wearableItem = selectItem(tempItemList);

            if (wearableItem == null) {
                break;
            } else {
                WearableItemQuality wearableItemQuality = wearableItem.getItemQuality();
                Gson gson = new Gson();
                WearableItem itemCopy = gson.fromJson(gson.toJson(wearableItem), WearableItem.class);

                if (wearableItemQuality == WearableItemQuality.BASIC) {
                    hero.getHeroInventory().removeItemFromItemList(wearableItem);
                    itemCopy.setItemQuality(WearableItemQuality.IMPROVED);
                    afterSuccessFullImproveItem(itemCopy, hero);

                } else if (wearableItemQuality == WearableItemQuality.IMPROVED) {
                    hero.getHeroInventory().removeItemFromItemList(wearableItem);
                    itemCopy.setItemQuality(WearableItemQuality.SUPERIOR);
                    afterSuccessFullImproveItem(itemCopy, hero);

                } else {
                    PrintUtil.printLongDivider();
                    System.out.println("\t\tYou can not refinement your item. Your item has the highest quality");
                    PrintUtil.printLongDivider();
                }
            }
        }
    }

    private void afterSuccessFullImproveItem(WearableItem wearableItem, Hero hero) {
        wearableItem.setItemAbilities(wearableItem);
        wearableItem.setPrice(wearableItem.getPrice() * 2);
        hero.getHeroInventory().addItemToItemList(wearableItem);

        PrintUtil.printLongDivider();
        System.out.println("\t\tYou refinement your item " + wearableItem.getName() + " to " + wearableItem.getItemQuality() + " quality");
        PrintUtil.printLongDivider();
        hero.updateWearingItemAbilityPoints();
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
        List<CraftingReagentItem> tempList = itemsLists.returnCraftingReagentItemListByTypeAndLevel(
                CraftingReagentItemType.BLACKSMITH_REAGENT, wearableItem.getItemLevel());

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
                PrintUtil.printItemAbilityPoints(item.getKey());
                index++;
            }
        }

        return tempItemList;
    }
}
