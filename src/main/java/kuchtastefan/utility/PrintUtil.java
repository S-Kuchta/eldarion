package kuchtastefan.utility;

import kuchtastefan.ability.Ability;
import kuchtastefan.domain.GameCharacter;
import kuchtastefan.domain.Hero;
import kuchtastefan.item.ItemType;

import java.util.Map;

public class PrintUtil {

    public static void printCurrentAbilityPoints(GameCharacter hero) {
        System.out.println(hero.getClass().getSimpleName().equals("Hero") ? "Your abilities:" : "Enemy abilities:");
        for (Map.Entry<Ability, Integer> entry : hero.getAbilities().entrySet()) {
            System.out.print(entry.getKey() + ": " + entry.getValue() + ", ");
        }
        System.out.println();
        printDivider();
    }

    public static void printCurrentAbilityPointsWithoutItems(Hero hero) {
        System.out.println(hero.getClass().getSimpleName().equals("Hero") ? "Your abilities:" : "Enemy abilities:");

        for (Map.Entry<Ability, Integer> entry : hero.getAbilities().entrySet()) {
            if (hero.getWearingItemAbilityPoints().get(entry.getKey()) == null) {
                System.out.print(entry.getKey() + ": " + entry.getValue() + ", ");
            } else {
                System.out.print(entry.getKey() + ": " + (entry.getValue() - hero.getWearingItemAbilityPoints().get(entry.getKey())) + ", ");
            }
        }
        System.out.println();
        printDivider();
    }

    public static void printCurrentWearingArmor(Hero hero) {
        System.out.println("You are wearing: ");

        for (Map.Entry<ItemType, String> item : hero.getEquippedItem().entrySet()) {
            System.out.println("Item part: " + item.getKey() + " item name: " + item.getValue());
        }
    }

    public static void printDivider() {
        System.out.println("----------------------------------------------");
    }

}
