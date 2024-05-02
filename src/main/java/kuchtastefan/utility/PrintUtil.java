package kuchtastefan.utility;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.Action;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.spell.Spell;
import kuchtastefan.constant.Constant;
import kuchtastefan.constant.ConstantSymbol;
import kuchtastefan.gameSettings.GameSetting;
import kuchtastefan.gameSettings.GameSettingsDB;
import kuchtastefan.item.specificItems.wearableItem.WearableItem;
import kuchtastefan.item.specificItems.wearableItem.WearableItemType;
import kuchtastefan.quest.Quest;
import kuchtastefan.quest.QuestStatus;
import kuchtastefan.quest.questObjectives.QuestObjective;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PrintUtil {

//    public static void printSpellDescription(Hero hero, GameCharacter spellTarget, Spell spell) {
//
//        System.out.print(ConsoleColor.MAGENTA + spell.getSpellName() + ConsoleColor.RESET
//                + " [Mana Cost: " + ConsoleColor.BLUE + spell.getSpellManaCost() + ConsoleColor.RESET + "]");
//        if (spell.getTurnCoolDown() > 0) {
//            System.out.print("[CoolDown: "
//                    + printActionTurnCoolDown(spell.getCurrentTurnCoolDown(), spell.getTurnCoolDown()) + "]");
//        }
//
//        if (spell.getBonusValueFromAbility() != null) {
//            System.out.print("[Bonus From Ability: ");
//            for (Map.Entry<Ability, Integer> abilityBonus : spell.getBonusValueFromAbility().entrySet()) {
//                System.out.print(abilityBonus.getKey() + "(" + abilityBonus.getValue() + "x), ");
//            }
//            System.out.print("]");
//        }
//
//        System.out.println("\n\t" + spell.getSpellDescription());
//
//        for (Action action : spell.getSpellActions()) {
//
//            int totalActionValue = action.returnTotalActionValue(spell.getBonusValueFromAbility(), hero);
//            if (spellTarget != null && action.getActionEffectOn().equals(ActionEffectOn.SPELL_TARGET)) {
//                totalActionValue -= spellTarget.getCurrentAbilityValue(Ability.RESIST_DAMAGE);
//            }
//
//            System.out.print("\t- " + ConsoleColor.YELLOW + action.getActionName() + ConsoleColor.RESET + " on ");
//            if (spell.isHitAllEnemy()) {
//                System.out.print("All enemies ");
//            } else {
//                System.out.print(action.getActionEffectOn() + " ");
//            }
//
//            printActionDetails(action, totalActionValue);
//        }
//    }

    public static void printSpellDescription(Hero hero, GameCharacter spellTarget, Spell spell) {

        System.out.print(ConsoleColor.MAGENTA + spell.getSpellName() + ConsoleColor.RESET
                + " [Mana Cost: " + ConsoleColor.BLUE + spell.getSpellManaCost() + ConsoleColor.RESET + "]");
        if (spell.getTurnCoolDown() > 0) {
            System.out.print("[CoolDown: "
                    + printActionTurnCoolDown(spell.getCurrentTurnCoolDown(), spell.getTurnCoolDown()) + "]");
        }

        for (Action action : spell.getSpellActions()) {
            if (spellTarget == null) {
                spellTarget = new Hero("Target");
            }

            System.out.println();
            System.out.print("\t\t");
            action.printActionDescription(hero, spellTarget);
            if (GameSettingsDB.returnGameSettingValue(GameSetting.SHOW_INFORMATION_ABOUT_ACTION_NAME)) {
                System.out.print("\n\t\t" + action.getActionName().getDescription());
            }
        }
    }

    public static void printBattleBuffs(GameCharacter gameCharacter) {
        if (gameCharacter.getBuffsAndDebuffs() == null) {
            gameCharacter.setBuffsAndDebuffs(new HashSet<>());
        }

        generateTableWithBuffs(gameCharacter.getBuffsAndDebuffs());
    }

    public static void generateTableWithBuffs(Set<ActionWithDuration> actionWithDurationList) {
        String leftAlignment = "| %-30s | %-20s | %-28s | %-20s |%n";
        for (ActionWithDuration actionWithDuration : actionWithDurationList) {

            System.out.format(leftAlignment, actionWithDuration.getActionName(),
                    "Action Value: " + actionWithDuration.getCurrentActionValue(),
                    "Turns: " + printActionTurnRemaining(actionWithDuration.getCurrentActionTurn(), actionWithDuration.getMaxActionTurns()),
                    "Stacks: " + printActionTurnRemaining(actionWithDuration.getActionCurrentStacks(), actionWithDuration.getActionMaxStacks()));
        }
    }

    public static StringBuilder printActionTurnRemaining(int currentValue, int maxValue) {

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < maxValue; i++) {
            if (i > currentValue - 1) {
                stringBuilder.append("_");
            } else {
                stringBuilder.append("■");
            }
        }
        return stringBuilder;
    }

    public static StringBuilder printActionTurnCoolDown(int currentValue, int maxValue) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < maxValue; i++) {
            if (i + 1 >= currentValue) {
                stringBuilder.append("_");
            } else {
                stringBuilder.append("■");
            }
        }
        return stringBuilder;
    }

    public static void printHeaderWithStatsBar(GameCharacter gameCharacter) {
        printExtraLongDivider();
//        System.out.printf("%58s %n", gameCharacter.getName());
        System.out.println("\t" + gameCharacter.getName());

        printBar(gameCharacter, Ability.HEALTH);
        printBar(gameCharacter, Ability.MANA);
        printBar(gameCharacter, Ability.ABSORB_DAMAGE);
        if (gameCharacter instanceof Hero) {
            System.out.println();
            System.out.println();
            printExperienceBar((Hero) gameCharacter);
        }
        System.out.println();
    }

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
//        printHeaderWithStatsBar(gameCharacter);
//        System.out.printf("%80s %n", gameCharacter.getName() + " Ability Points");
        System.out.print("\t\t\t");
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

    public static void printStringSlowly(String s) {
        char[] stringToCharArr = s.toCharArray();

        if (!GameSettingsDB.returnGameSettingValue(GameSetting.PRINT_STRING_SLOWLY)) {
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

    public static void printTextWrap(String text) {
        StringBuilder line = new StringBuilder();

        for (String word : text.split("\\s")) {
            if (line.length() + word.length() <= 60) {
                line.append(word).append(" ");
            } else {
                printStringSlowly(line.toString().trim());
                line.setLength(0);
                line.append(word).append(" ");
            }
        }

        if (!line.isEmpty()) {
            printStringSlowly(line.toString().trim());
        }
    }

    public static void printDivider() {
        System.out.println("|-----------------------------------------------|");
    }

    public static void printLongDivider() {
        System.out.println("|----------------------------------------------------------------------------------|");
    }

    public static void printExtraLongDivider() {
        System.out.println("|-------------------------------------------------------------------------------------------------------------|");
    }

    public static int printWearableItemCountByType(Hero hero, WearableItemType wearableItemType) {
        int count = 0;
        for (Map.Entry<WearableItem, Integer> item : hero.getHeroInventory().returnHeroInventory(WearableItem.class).entrySet()) {
            if (item.getKey().getItemType().equals(wearableItemType)) {
                count += item.getValue();
            }
        }

        return count;
    }

    public static void printShopHeader(Hero hero, String shop) {
        printLongDivider();
        System.out.println("\t\t" + "Welcome to the "
                + shop + " Shop\t\t\tYou have "
                + hero.getHeroGold() + " golds");
        printLongDivider();
    }

    public static void printEnterValidInput() {
        System.out.println(ConsoleColor.RED + "\tEnter valid input" + ConsoleColor.RESET);
    }

    public static void printIndexAndText(String index, String text) {
        System.out.print(ConsoleColor.CYAN + "\t" + index + ". " + ConsoleColor.RESET + text);
    }

    public static void printGameSettingsYesOrNo(boolean gameSetting) {
        if (!gameSetting) {
            System.out.print(ConsoleColor.WHITE + "Yes" + ConsoleColor.RESET + " / No");
        } else {
            System.out.print("Yes / " + ConsoleColor.WHITE + "No" + ConsoleColor.RESET);
        }
    }

    public static void printCompleteQuestText(String questName) {
        System.out.println("\t" + ConstantSymbol.QUEST_SYMBOL
                + " You have completed Quest " + ConsoleColor.YELLOW + questName + ConsoleColor.RESET + " "
                + ConstantSymbol.QUEST_SYMBOL);
    }

    public static void printSpellGameSettings() {
        System.out.println();
        PrintUtil.printIndexAndText("X", "Hide action description - ");
        PrintUtil.printGameSettingsYesOrNo(GameSettingsDB.returnGameSettingValue(GameSetting.SHOW_INFORMATION_ABOUT_ACTION_NAME));

        System.out.print("\t");
        PrintUtil.printIndexAndText("Y", "Hide spells on CoolDown - ");
        PrintUtil.printGameSettingsYesOrNo(GameSettingsDB.returnGameSettingValue(GameSetting.HIDE_SPELLS_ON_COOL_DOWN));
    }

    public static void printQuestDetails(Quest quest, Hero hero) {
        PrintUtil.printLongDivider();
        System.out.println("\t\t\t\t------ " + quest.getQuestName() + " ------");
        System.out.print("\t");
        PrintUtil.printTextWrap(quest.getQuestDescription());
        System.out.println();

        for (QuestObjective questObjective : quest.getQuestObjectives()) {
            questObjective.printQuestObjectiveAssignment(hero);
        }
    }

    public static String returnQuestSuffix(Quest quest) {
        switch (quest.getQuestStatus()) {
            case QuestStatus.UNAVAILABLE -> {
                return "-" + ConsoleColor.WHITE + "!" + ConsoleColor.RESET + "-";
            }
            case QuestStatus.AVAILABLE -> {
                return "-" + ConsoleColor.YELLOW_BOLD_BRIGHT + "!" + ConsoleColor.RESET + "-";
            }
            case QuestStatus.COMPLETED -> {
                return "-" + ConsoleColor.YELLOW_BOLD_BRIGHT + "?" + ConsoleColor.RESET + "-";
            }
            case QuestStatus.TURNED_IN -> {
                return " -- Completed --";
            }
            default -> {
                return "";
            }
        }
    }

    public static void printMenuHeader(String header) {
        System.out.println(ConsoleColor.YELLOW_UNDERLINED + "\t\t\t\t\t" + ConsoleColor.YELLOW + header + ConsoleColor.YELLOW_UNDERLINED + "\t\t\t\t\t" + ConsoleColor.RESET);
    }

    public static void printMenuOptions(String... options) {
        for (int i = 0; i < options.length; i++) {
            PrintUtil.printIndexAndText(String.valueOf(i), options[i]);
            System.out.println();
        }
    }
}






