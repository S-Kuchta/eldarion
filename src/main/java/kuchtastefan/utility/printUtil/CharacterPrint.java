package kuchtastefan.utility.printUtil;

import kuchtastefan.ability.Ability;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.constant.Constant;
import kuchtastefan.item.specificItems.wearableItem.WearableItem;
import kuchtastefan.item.specificItems.wearableItem.WearableItemType;
import kuchtastefan.utility.ConsoleColor;

import java.util.Map;

import static kuchtastefan.utility.printUtil.PrintUtil.printExtraLongDivider;
import static kuchtastefan.utility.printUtil.PrintUtil.printLongDivider;

public class CharacterPrint {

    public static void printHeaderWithStatsBar(GameCharacter gameCharacter) {
        printExtraLongDivider();
        System.out.println("\t" + gameCharacter.getName());

        printBar(gameCharacter, Ability.HEALTH);
        printBar(gameCharacter, Ability.MANA);
        printBar(gameCharacter, Ability.ABSORB_DAMAGE);
        if (gameCharacter instanceof Hero hero) {
            System.out.println();
            System.out.println();
            printExperienceBar(hero);
        }
        System.out.println();
    }

    /**
     * Print Bar for Health, Mana and Absorb Damage
     *
     */
    private static void printBar(GameCharacter gameCharacter, Ability ability) {
        int maxValue = gameCharacter.getEnhancedAbilities().get(ability);
        int currentValue = gameCharacter.getEffectiveAbilityValue(ability);
        double oneBarValue = (double) maxValue / 15;

        ConsoleColor consoleColor = ConsoleColor.RESET;
        if (ability.equals(Ability.HEALTH)) {
            consoleColor = ConsoleColor.RED_BRIGHT;
        } else if (ability.equals(Ability.MANA)) {
            consoleColor = ConsoleColor.BLUE_BRIGHT;
        }

        String charToPrint;
        System.out.print("\t" + ability + " »");
        for (int i = 0; i < 15; i++) {
            if (i * oneBarValue >= currentValue) {
                charToPrint = "_";
            } else {
                charToPrint = consoleColor + "■" + ConsoleColor.RESET;
            }
            System.out.print(charToPrint);
        }

        if (ability.equals(Ability.ABSORB_DAMAGE)) {
            System.out.print("« [" + currentValue + "]");
        } else {
            System.out.print("« [" + currentValue + "/" + maxValue + "]");
        }
    }

    private static void printExperienceBar(Hero hero) {
        if (hero.getLevel() < Constant.MAX_LEVEL) {
            double currentValue = hero.getExperiencePoints();
            double maxValue = hero.getExperiencePointsService().getNeededExperiencePointsForNewLevel();
            double oneBarValue = maxValue / 60;

            String charToPrint;
            System.out.print("\t" + "Experience Points" + " »");
            // 75
            for (int i = 0; i < 60; i++) {
                if (i * oneBarValue >= currentValue) {
                    charToPrint = "_";
                } else {
                    charToPrint = ConsoleColor.YELLOW_BRIGHT + "■" + ConsoleColor.RESET;
                }
                System.out.print(charToPrint);
            }

            System.out.print("« [" + (int) currentValue + "/" + (int) maxValue + "][Level: " + hero.getLevel() + "]");
        } else {
            System.out.print("\t» [");
            System.out.printf(ConsoleColor.YELLOW_UNDERLINED + "%47s", "");
            System.out.print("MAX LEVEL");
            System.out.printf("%47s", "" + ConsoleColor.RESET);
            System.out.print("] «");
        }
    }

    public static void printBaseAbilityPoints(GameCharacter gameCharacter) {
        printExtraLongDivider();
        System.out.printf("%58s %n", "Abilities:");
        System.out.print("\t");
        for (Map.Entry<Ability, Integer> ability : gameCharacter.getBaseAbilities().entrySet()) {
            if (!(ability.getKey().equals(Ability.MANA)
                    || ability.getKey().equals(Ability.HEALTH)
                    || ability.getKey().equals(Ability.ABSORB_DAMAGE))) {

                System.out.print(ability.getKey() + ": " + ConsoleColor.YELLOW + ability.getValue() + ConsoleColor.RESET + "| ");
            }
        }
        System.out.println();
        printExtraLongDivider();
    }

    public static void printEffectiveAbilityPoints(GameCharacter gameCharacter) {
        System.out.print("\t\t");
        for (Map.Entry<Ability, Integer> abilityPoints : gameCharacter.getEffectiveAbilities().entrySet()) {

            if (!(abilityPoints.getKey().equals(Ability.MANA)
                    || abilityPoints.getKey().equals(Ability.HEALTH)
                    || abilityPoints.getKey().equals(Ability.ABSORB_DAMAGE))) {

                System.out.print(abilityPoints.getKey() + ": " + ConsoleColor.YELLOW + abilityPoints.getValue() + ConsoleColor.RESET + "| ");
            }
        }
        System.out.println();
        printExtraLongDivider();
    }

    public static void printCurrentWearingArmor(Hero hero) {
        printLongDivider();
        System.out.println("\t\t\t\t------ Current Wearing Armor and Weapon ------");
        for (Map.Entry<WearableItemType, WearableItem> item : hero.getEquippedItem().entrySet()) {
            System.out.print("\t" + item.getKey() + ": " + item.getValue().getName());
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
}
