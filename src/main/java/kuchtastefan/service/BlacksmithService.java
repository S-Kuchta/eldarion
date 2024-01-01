package kuchtastefan.service;

import kuchtastefan.domain.Hero;
import kuchtastefan.hint.HintName;
import kuchtastefan.hint.HintUtil;
import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.item.wearableItem.WearableItemQuality;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

public class BlacksmithService {
    public void refinementItemQuality(Hero hero) {
//        HintUtil.printHint(HintName.REFINEMENT);
        while (true) {
            PrintUtil.printDivider();
            System.out.println("\t\tRefinement item");
            PrintUtil.printDivider();
            printItemList(hero);
            WearableItem wearableItem = selectItem(hero);
            if (wearableItem == null) {
                break;
            } else {
                WearableItemQuality wearableItemQuality = wearableItem.getItemQuality();
                if (wearableItemQuality == WearableItemQuality.BASIC) {
                    wearableItem.setItemQuality(WearableItemQuality.IMPROVED);
                    afterSuccessFullImproveItem(wearableItem, hero);
                } else if (wearableItemQuality == WearableItemQuality.IMPROVED) {
                    wearableItem.setItemQuality(WearableItemQuality.SUPERIOR);
                    afterSuccessFullImproveItem(wearableItem, hero);
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
        PrintUtil.printLongDivider();
        System.out.println("\t\tYou refinement your item " + wearableItem.getName() + " to " + wearableItem.getItemQuality() + " quality");
        PrintUtil.printLongDivider();
        hero.updateWearingItemAbilityPoints();
    }

    public void dismantleItem(Hero hero) {
//        HintUtil.printHint(HintName.DISMANTLE);
        PrintUtil.printDivider();
        System.out.println("\t\tDismantle item");
        PrintUtil.printDivider();
        printItemList(hero);
        WearableItem wearableItem = selectItem(hero);
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

    private WearableItem selectItem(Hero hero) {
        while (true) {
            int choice = InputUtil.intScanner();
            if (choice == 0) {
                return null;
            }
            try {
                return hero.getHeroInventory().get(choice - 1);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Inter valid input");
            }
        }
    }

    private void printItemList(Hero hero) {
        int index = 1;
        System.out.println("\t0. Go back");

        if (hero.getHeroInventory().isEmpty()) {
            System.out.println("\tItem list is empty");
        }

        for (WearableItem wearableItem : hero.getHeroInventory()) {
            System.out.println("\t" + index + ". " + wearableItem.getName()
                    + ", item level: "
                    + wearableItem.getItemLevel()
                    + ", item quality: "
                    + wearableItem.getItemQuality());
            index++;
        }
    }
}
