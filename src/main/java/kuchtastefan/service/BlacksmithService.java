package kuchtastefan.service;

import kuchtastefan.domain.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemQuality;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

public class BlacksmithService {
    public void improveItemQuality(Hero hero) {
        while (true) {
            printItemList(hero);
            Item item = selectItem(hero);
            if (item == null) {
                break;
            } else {
                ItemQuality itemQuality = item.getItemQuality();
                if (itemQuality == ItemQuality.BASIC) {
                    item.setItemQuality(ItemQuality.IMPROVED);
                    item.setItemAbilities(item);
                    PrintUtil.printLongDivider();
                    System.out.println("\t\tYou improved " + item.getName() + " to " + item.getItemQuality() + " quality");
                    PrintUtil.printLongDivider();
                } else if (itemQuality == ItemQuality.IMPROVED) {
                    item.setItemQuality(ItemQuality.SUPERIOR);
                    item.setItemAbilities(item);
                    PrintUtil.printLongDivider();
                    System.out.println("\t\tYou improved your item " + item.getName() + " to " + item.getItemQuality() + " quality");
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
        Item item = selectItem(hero);
        if (item == null) {
            return;
        }

        hero.removeItemFromItemList(item);
        hero.setHeroGold(hero.getHeroGold() + (item.getPrice() / 4));
    }

    private Item selectItem(Hero hero) {
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

        for (Item item : hero.getHeroInventory()) {
            System.out.println(index + ". " + item.getName()
                    + ", item level: "
                    + item.getItemLevel()
                    + ", item quality: "
                    + item.getItemQuality());
            index++;
        }
    }
}
