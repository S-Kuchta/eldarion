package kuchtastefan.utility;

import kuchtastefan.ability.Ability;
import kuchtastefan.gameSettings.GameSettings;
import kuchtastefan.domain.GameCharacter;
import kuchtastefan.domain.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PrintUtil {

    public static void printCurrentAbilityPoints(GameCharacter gameCharacter) {
        System.out.println();
        System.out.println(gameCharacter instanceof Hero ? "Your abilities" : "Enemy abilities:");
        for (Map.Entry<Ability, Integer> entry : gameCharacter.getAbilities().entrySet()) {
            System.out.print(entry.getKey() + ": " + entry.getValue() + ", ");
        }
        System.out.println();
        printDivider();
    }

    public static void printCurrentAbilityPointsWithItems(Hero hero) {
        System.out.println();
        System.out.println("Ability points with items");
        for (Map.Entry<Ability, Integer> entry : hero.getAbilities().entrySet()) {
            System.out.print(entry.getKey() + ": "
                    + (entry.getValue()
                    + hero.getWearingItemAbilityPoints().get(entry.getKey())) + ", ");
        }
        System.out.println();
        printDivider();
    }

    public static void printItemAbilityStats(Item item) {
        System.out.print(item.getType() + ": " + item.getName());
        if (!item.getName().equals("No item")) {
            System.out.print(", Item stats: ");
        }
        for (Map.Entry<Ability, Integer> ability : item.getAbilities().entrySet()) {
            if (ability.getValue() != 0) {
                System.out.print(ability.getKey() + ": " + ability.getValue() + ", ");
            }
        }
        System.out.println();
    }

    public static void printCurrentWearingArmor(Hero hero) {
        for (Map.Entry<ItemType, Item> item : hero.getEquippedItem().entrySet()) {
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
        printDivider();
    }

    public static void printStringLetterByLetter(String s) {
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
        System.out.println("----------------------------------------------");
    }

    public static int printItemCountByType(Hero hero, ItemType itemType) {
        int count = 0;
        for (Item item : hero.getHeroInventory()) {
            if (item.getType() == itemType) {
                count++;
            }
        }
        return count;
    }

}
