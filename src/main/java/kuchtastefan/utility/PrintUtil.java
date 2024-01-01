package kuchtastefan.utility;

import kuchtastefan.ability.Ability;
import kuchtastefan.domain.GameCharacter;
import kuchtastefan.domain.Hero;
import kuchtastefan.gameSettings.GameSettings;
import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.item.wearableItem.WearableItemType;

import java.util.Map;

public class PrintUtil {

    public static void printCurrentAbilityPoints(GameCharacter gameCharacter) {
        System.out.println();
        System.out.println(gameCharacter instanceof Hero ? "\tYour abilities:" : "\tEnemy abilities:");
        System.out.print("\t");
        for (Map.Entry<Ability, Integer> entry : gameCharacter.getAbilities().entrySet()) {
            System.out.print(entry.getKey() + ": " + entry.getValue() + ", ");
        }
        System.out.println();
        printLongDivider();
    }

    public static void printCurrentAbilityPointsWithItems(Hero hero) {
        System.out.println();
        System.out.println("Ability points with items:");
        for (Map.Entry<Ability, Integer> entry : hero.getAbilities().entrySet()) {
            System.out.print(entry.getKey() + ": "
                    + (entry.getValue()
                    + hero.getWearingItemAbilityPoints().get(entry.getKey())) + ", ");
        }
        System.out.println();
        printLongDivider();
    }

    public static void printItemAbilityStats(WearableItem wearableItem) {
        System.out.print(wearableItem.getType() + ": " + wearableItem.getName());
        if (!wearableItem.getName().equals("No item")) {
            System.out.print(", Item stats: ");
        }
        for (Map.Entry<Ability, Integer> ability : wearableItem.getAbilities().entrySet()) {
            if (ability.getValue() != 0) {
                System.out.print(ability.getKey() + ": " + ability.getValue() + ", ");
            }
        }
        System.out.println();
    }

    public static void printCurrentWearingArmor(Hero hero) {
        for (Map.Entry<WearableItemType, WearableItem> item : hero.getEquippedItem().entrySet()) {
            System.out.print(item.getKey() + ": " + item.getValue().getName());
            if (!item.getValue().getName().equals("No item")) {
                System.out.print(", Item stats: ");
            }
            for (Map.Entry<Ability, Integer> ability : item.getValue().getAbilities().entrySet()) {
                if (ability.getValue() != 0) {
                    System.out.print(ability.getKey() + ": " + ability.getValue() + ", ");
                }
            }
            System.out.println();
        }
        printLongDivider();
    }

    public static void printStringSlowly(String s) {
        char[] stringToCharArr = s.toCharArray();

        if (!GameSettings.isPrintStringSlowly()) {
            for (char c : stringToCharArr) {
                System.out.print(c);
            }
        } else {
            for (char c : stringToCharArr) {
                System.out.print(c);
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        System.out.println();
    }

    public static void printHeroGold(Hero hero) {
        System.out.println(hero.getName() + " golds: " + hero.getHeroGold());
    }

    public static void printDivider() {
        System.out.println("|----------------------------------------------|");
    }

    public static void printLongDivider() {
        System.out.println("|------------------------------------------------------------------------------|");
    }

    public static int printItemCountByType(Hero hero, WearableItemType wearableItemType) {
        int count = 0;
        for (WearableItem wearableItem : hero.getHeroInventory()) {
            if (wearableItem.getType() == wearableItemType) {
                count++;
            }
        }
        return count;
    }

    public static void printFullItemDescription(WearableItem wearableItem) {
        printItemAbilityStats(wearableItem);
        System.out.println("\t\t\tPrice: "
                + wearableItem.getPrice()
                + " golds, item level: "
                + wearableItem.getItemLevel() + ", item quality: "
                + wearableItem.getItemQuality());
    }

    public static void printShopHeader(Hero hero, WearableItemType wearableItemType) {
        printLongDivider();
        System.out.println("\t\t" + "Welcome to the "
                + wearableItemType + " Shop\t\t\t\t\tYou have "
                + hero.getHeroGold() + " golds");
        printLongDivider();
    }

    public static void printInventoryHeader(WearableItemType wearableItemType) {
        printDivider();
        System.out.println("\t\t" + wearableItemType + " inventory");
        printDivider();
    }

    public static void printMarketHeader(String marketType) {
        printDivider();
        System.out.println("\t\tWelcome to the " + marketType + " Market");
        printDivider();
    }

    public static void printInventoryHeader(String inventoryType) {
        printDivider();
        System.out.println("\t\tWelcome to the " + inventoryType + " Inventory");
        printDivider();
    }

}
