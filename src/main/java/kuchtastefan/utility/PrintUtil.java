package kuchtastefan.utility;

import kuchtastefan.ability.Ability;
import kuchtastefan.domain.Hero;

import java.util.Map;

public class PrintUtil {

    public static void printCurrentAbilityPoints(Hero hero) {
        for (Map.Entry<Ability, Integer> entry : hero.getAbilities().entrySet()) {
            System.out.print(entry.getKey() + ": " + entry.getValue() + ", ");
        }
        System.out.println();
    }

    public static void printDivider() {
        System.out.println("-------------------------------------------");
    }

}
