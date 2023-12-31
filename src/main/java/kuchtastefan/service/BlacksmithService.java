package kuchtastefan.service;

import kuchtastefan.domain.Hero;
import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.item.wearableItem.WearableItemQuality;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

public class BlacksmithService {
    public void improveItemQuality(Hero hero) {
        while (true) {
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
                    System.out.println("\t\tYou can not improve your item. Your item has the highest quality");
                    PrintUtil.printLongDivider();
                }
            }
        }
    }

    private void afterSuccessFullImproveItem(WearableItem wearableItem, Hero hero) {
        wearableItem.setItemAbilities(wearableItem);
        PrintUtil.printLongDivider();
        System.out.println("\t\tYou improved your item " + wearableItem.getName() + " to " + wearableItem.getItemQuality() + " quality");
        PrintUtil.printLongDivider();
        hero.updateWearingItemAbilityPoints();
    }

    public void destroyItem(Hero hero) {
        WearableItem wearableItem = selectItem(hero);
        if (wearableItem == null) {
            return;
        }

        hero.removeItemFromItemList(wearableItem);
        hero.setHeroGold(hero.getHeroGold() + (wearableItem.getPrice() / 4));
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
        System.out.println("0. Go back");

        if (hero.getHeroInventory().isEmpty()) {
            System.out.println("Item list is empty");
        }

        for (WearableItem wearableItem : hero.getHeroInventory()) {
            System.out.println(index + ". " + wearableItem.getName()
                    + ", item level: "
                    + wearableItem.getItemLevel()
                    + ", item quality: "
                    + wearableItem.getItemQuality());
            index++;
        }
    }
}
