package kuchtastefan.utility;

import kuchtastefan.ability.Ability;
import kuchtastefan.domain.GameCharacter;
import kuchtastefan.domain.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemType;

import java.util.List;
import java.util.Map;

public class PrintUtil {

    public static void printCurrentAbilityPoints(GameCharacter hero) {
        System.out.println();
        System.out.println(hero.getClass().getSimpleName().equals("Hero") ? "Your abilities + abilities from Equipped items:" : "Enemy abilities:");
        for (Map.Entry<Ability, Integer> entry : hero.getAbilities().entrySet()) {
            System.out.print(entry.getKey() + ": " + entry.getValue() + ", ");
        }
        System.out.println();
        printDivider();
    }

    public static void printCurrentAbilityPointsWithoutItems(Hero hero) {
        System.out.println();
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

    public static void printCurrentWearingArmor(Hero hero, List<Item> itemList) {
        for (Map.Entry<ItemType, String> item : hero.getEquippedItems().getEquippedItem().entrySet()) {
            if (item.getValue() != null) {
                System.out.print(item.getKey() + ": " + item.getValue());
                for (Item item1 : itemList) {
                    if (item1.getName().equals(item.getValue())) {
                        System.out.print(" -> " + item1.getAbilities());
                    }
                }
            } else {
                System.out.print("You are not wearing " + item.getKey());
            }
            System.out.println();
        }
        System.out.println();
        printDivider();
    }

    public static void printDivider() {
        System.out.println("----------------------------------------------");
    }

}
