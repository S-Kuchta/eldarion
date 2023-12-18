package kuchtastefan.utility;

import kuchtastefan.ability.Ability;
import kuchtastefan.domain.GameCharacter;

import java.util.Map;

public class PrintUtils {
    public static void printAbilities(GameCharacter character) {
        for (Map.Entry<Ability, Integer> entry : character.getAbilities().entrySet()) {
            System.out.print(entry.getKey() + ": " + entry.getValue() + ", ");
        }
        System.out.println();
    }

    public static void printDivider() {
        System.out.println("----------------------------------");
    }
}
