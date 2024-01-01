package kuchtastefan.service;

import com.google.gson.Gson;
import kuchtastefan.domain.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.item.wearableItem.WearableItemQuality;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

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
                    hero.removeItemFromItemList(wearableItem);
                    itemCopy.setItemQuality(WearableItemQuality.IMPROVED);
                    afterSuccessFullImproveItem(itemCopy, hero);
                } else if (wearableItemQuality == WearableItemQuality.IMPROVED) {
                    hero.removeItemFromItemList(wearableItem);
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
        hero.addItemToItemList(wearableItem);

        PrintUtil.printLongDivider();
        System.out.println("\t\tYou refinement your item " + wearableItem.getName() + " to " + wearableItem.getItemQuality() + " quality");
        PrintUtil.printLongDivider();
        hero.updateWearingItemAbilityPoints();
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

        double goldForDestroy = wearableItem.getPrice() / 4;

        PrintUtil.printLongDivider();
        System.out.println("\tYou dismantled " + wearableItem.getName() + ", you got " + goldForDestroy + " golds");
        PrintUtil.printLongDivider();
        hero.removeItemFromItemList(wearableItem);
        hero.setHeroGold(hero.getHeroGold() + goldForDestroy);
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

        if (hero.getHeroInventory().isEmpty()) {
            System.out.println("\tItem list is empty");
        }

        for (Map.Entry<Item, Integer> item : hero.getHeroInventory().entrySet()) {
            tempItemList.add((WearableItem) item.getKey());
        }

        for (WearableItem wearableItem : tempItemList) {
            System.out.println("\t" + index + ". " + wearableItem.getName()
                    + ", item level: "
                    + wearableItem.getItemLevel()
                    + ", item quality: "
                    + wearableItem.getItemQuality());
            index++;
        }
        return tempItemList;
    }
}
