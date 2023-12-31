package kuchtastefan.service;

import kuchtastefan.domain.Hero;
import kuchtastefan.item.wearableItem.wearableItem;
import kuchtastefan.item.wearableItem.wearableItemQuality;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

public class BlacksmithService {
    public void improveItemQuality(Hero hero) {
        while (true) {
            printItemList(hero);
            wearableItem wearableItem = selectItem(hero);
            if (wearableItem == null) {
                break;
            } else {
                wearableItemQuality wearableItemQuality = wearableItem.getItemQuality();
                if (wearableItemQuality == wearableItemQuality.BASIC) {
                    wearableItem.setItemQuality(wearableItemQuality.IMPROVED);
                    wearableItem.setItemAbilities(wearableItem);
                    PrintUtil.printLongDivider();
                    System.out.println("\t\tYou improved " + wearableItem.getName() + " to " + wearableItem.getItemQuality() + " quality");
                    PrintUtil.printLongDivider();
                } else if (wearableItemQuality == wearableItemQuality.IMPROVED) {
                    wearableItem.setItemQuality(wearableItemQuality.SUPERIOR);
                    wearableItem.setItemAbilities(wearableItem);
                    PrintUtil.printLongDivider();
                    System.out.println("\t\tYou improved your item " + wearableItem.getName() + " to " + wearableItem.getItemQuality() + " quality");
                    PrintUtil.printLongDivider();
                } else {
                    PrintUtil.printLongDivider();
                    System.out.println("\t\tYou can not improve your item. Your item has the highest quality");
                    PrintUtil.printLongDivider();
                }
            }
        }
    }

    public void destroyItem(Hero hero) {
        wearableItem wearableItem = selectItem(hero);
        if (wearableItem == null) {
            return;
        }

        hero.removeItemFromItemList(wearableItem);
        hero.setHeroGold(hero.getHeroGold() + (wearableItem.getPrice() / 4));
    }

    private wearableItem selectItem(Hero hero) {
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

        for (wearableItem wearableItem : hero.getHeroInventory()) {
            System.out.println(index + ". " + wearableItem.getName()
                    + ", item level: "
                    + wearableItem.getItemLevel()
                    + ", item quality: "
                    + wearableItem.getItemQuality());
            index++;
        }
    }
}
