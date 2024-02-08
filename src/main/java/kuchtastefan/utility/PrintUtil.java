package kuchtastefan.utility;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.Action;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.characters.GameCharacter;
import kuchtastefan.characters.hero.Hero;
import kuchtastefan.characters.spell.Spell;
import kuchtastefan.constant.Constant;
import kuchtastefan.gameSettings.GameSettings;
import kuchtastefan.items.Item;
import kuchtastefan.items.consumeableItem.ConsumableItem;
import kuchtastefan.items.craftingItem.CraftingReagentItem;
import kuchtastefan.items.junkItem.JunkItem;
import kuchtastefan.items.wearableItem.WearableItem;
import kuchtastefan.items.wearableItem.WearableItemType;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PrintUtil {

    public static void printSpellDescription(Hero hero, Spell spell) {

        System.out.print(spell.getSpellName() + " [Mana Cost: " + spell.getSpellManaCost() + "]");
        if (spell.getTurnCoolDown() > 0) {
            System.out.print("[CoolDown: "
                    + printActionTurnCoolDown(spell.getCurrentTurnCoolDown(), spell.getTurnCoolDown()) + "]");
        }

        System.out.println("\n\t" + spell.getSpellDescription());

        for (Action action : spell.getSpellActions()) {
            int totalActionValue = action.getMaxActionValue();

            if (spell.getBonusValueFromAbility() != null) {
                for (Map.Entry<Ability, Integer> abilityBonus : spell.getBonusValueFromAbility().entrySet()) {
                    totalActionValue += hero.getCurrentAbilityValue(abilityBonus.getKey())
                            * abilityBonus.getValue();
                }
            }

            System.out.print("\t- " + ConsoleColor.YELLOW + action.getActionName() + ConsoleColor.RESET + " on "
                    + action.getActionEffectOn());
            if (action.getMaxActionValue() != 0) {
                System.out.print(" [Action value: "
                        + (int) (totalActionValue * Constant.LOWER_DAMAGE_MULTIPLIER)
                        + " - " + totalActionValue + "]");
            }
            System.out.print(" [Chance to Perform: " + action.getChanceToPerformAction() + "%]");

            if (action instanceof ActionWithDuration) {
                System.out.print(" [Turns Duration: " + ((ActionWithDuration) action).getMaxActionTurns() + "]"
                        + " [Max Stacks: " + ((ActionWithDuration) action).getActionMaxStacks() + "]");
            }
            if (GameSettings.isShowInformationAboutActionName()) {
                System.out.print("\n\t\t" + action.getActionName().getDescription());
            }
            System.out.println();
        }
    }

    public static void printBattleBuffs(GameCharacter gameCharacter) {
        if (gameCharacter.getBattleActionsWithDuration() == null) {
            gameCharacter.setBattleActionsWithDuration(new HashSet<>());
        }

        generateTableWithBuffs(gameCharacter.getBattleActionsWithDuration());
    }

    public static void printRegionBuffs(Hero hero) {
        generateTableWithBuffs(hero.getRegionActionsWithDuration());
    }

    public static void generateTableWithBuffs(Set<ActionWithDuration> actionWithDurationList) {
        String leftAlignment = "| %-30s | %-20s | %-28s | %-20s |%n";
        for (ActionWithDuration actionWithDuration : actionWithDurationList) {

            String specialSymbol;
            if (actionWithDuration.getCurrentActionValue() == 0) {
                specialSymbol = "";
            } else if (actionWithDuration.getCurrentActionValue() < 0) {
                specialSymbol = " - ";
            } else {
                specialSymbol = " + ";
            }

            System.out.format(leftAlignment, specialSymbol + actionWithDuration.getActionName(),
                    "Action Value: " + specialSymbol + actionWithDuration.getCurrentActionValue(),
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
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t" + gameCharacter.getName());
        printBar(gameCharacter, Ability.HEALTH);
        printBar(gameCharacter, Ability.MANA);
        printBar(gameCharacter, Ability.ABSORB_DAMAGE);
        System.out.println();
        printExtraLongDivider();
    }

    private static void printBar(GameCharacter gameCharacter, Ability ability) {
        int maxValue = gameCharacter.getMaxAbilities().get(ability);
        int currentValue = gameCharacter.getCurrentAbilityValue(ability);
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

    public static void printAbilityPoints(GameCharacter gameCharacter) {
        printExtraLongDivider();
        System.out.print("\t\t\t\t\t\t\t\t");
        System.out.println(gameCharacter instanceof Hero ? "Your abilities:" : "Enemy abilities:");
        System.out.print("\t");
        for (Map.Entry<Ability, Integer> entry : gameCharacter.getAbilities().entrySet()) {
            System.out.print(entry.getKey() + ": " + entry.getValue() + ", ");
        }
        System.out.println();
        printExtraLongDivider();
    }

    public static void printCurrentAbilityPointsWithItems(Hero hero) {
        printHeaderWithStatsBar(hero);
        System.out.println("\t\t\t\t\t\t\t\t------ Current Ability points with items ------");
        System.out.print("\t\t\t");
        for (Map.Entry<Ability, Integer> abilityPoints : hero.getCurrentAbilities().entrySet()) {

            if (abilityPoints.getKey().equals(Ability.HEALTH)
                    || abilityPoints.getKey().equals(Ability.MANA)
                    || abilityPoints.getKey().equals(Ability.ABSORB_DAMAGE)) {

            } else {
                System.out.print(abilityPoints.getKey() + ": " + abilityPoints.getValue() + ", ");
            }

        }
        System.out.println();
        printExtraLongDivider();
    }

    /**
     * Print full wearable item description.
     * Includes: if is right now equipped, item name, item type, item quality,
     * item level, buy/sell price and ability points of item
     *
     * @param wearableItem show this item description
     * @param sellItem     if this param is true, price will be set to sell price
     */
    public static void printItemDescription(WearableItem wearableItem, boolean sellItem, Hero hero) {

        if (hero.getEquippedItem().containsValue(wearableItem)) {
            System.out.print("-- EQUIPPED -- ");
        }
        System.out.print(wearableItem.getWearableItemType() + ": "
                + wearableItem.getName()
                + " (" + wearableItem.getWearableItemQuality() + "), iLevel: " + wearableItem.getItemLevel());
        if (!sellItem) {
            System.out.print(", Item Price: " + wearableItem.getPrice());
        } else {
            System.out.print(", Sell Price: " + wearableItem.returnSellItemPrice());
        }

        if (!wearableItem.getName().equals("No item")) {
            System.out.print("\n\t\tItem stats: ");
        }
        for (Map.Entry<Ability, Integer> ability : wearableItem.getAbilities().entrySet()) {
            if (ability.getValue() != 0) {
                System.out.print(ability.getKey() + ": " + ability.getValue() + ", ");
            }
        }
        System.out.println();
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

    public static void printTextWrap(String text) {
//        char[] charArray = text.toCharArray();
//        int numberOfTextWrap = 1;
//        for (int i = 0; i <charArray.length; i++) {
//            System.out.print(charArray[i]);
//            if (i == (55 * numberOfTextWrap)) {
//                System.out.print(" -");
//                System.out.println("");
//                numberOfTextWrap++;
//            }
//        }

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
        for (Map.Entry<WearableItem, Integer> item : hero.getHeroInventory()
                .returnInventoryWearableItemMap().entrySet()) {
            if (item.getKey().getWearableItemType().equals(wearableItemType)) {
                count += item.getValue();
            }
        }
        return count;
    }

    public static int returnItemCountInHeroInventory(Hero hero, Item item) {
        return hero.getHeroInventory().getHeroInventory().get(item);
    }

    public static void printShopHeader(Hero hero, String shop) {
        printLongDivider();
        System.out.println("\t\t" + "Welcome to the "
                + shop + " Shop\t\t\tYou have "
                + hero.getHeroGold() + " golds");
        printLongDivider();
    }

    public static void printInventoryWearableItemTypeHeader(WearableItemType wearableItemType) {
        printLongDivider();
        System.out.println("\t\t\t\t\t\t------ " + wearableItemType + " inventory ------");
        printLongDivider();
    }

    public static void printInventoryHeader(String inventory) {
        printLongDivider();
        System.out.println("\t\t\t\t------ " + inventory + " items Inventory ------");
        printLongDivider();
    }

    public static void printConsumableItemInfo(ConsumableItem consumableItem, boolean sellItem) {
        System.out.print(consumableItem.getName()
                + ", " + consumableItem.getConsumableItemType()
                + ", iLevel: " + consumableItem.getItemLevel());
        if (!sellItem) {
            System.out.print(", Item Price: " + consumableItem.getPrice());
        } else {
            System.out.print(", Sell Price: " + consumableItem.returnSellItemPrice());
        }
        System.out.println();
        for (Action action : consumableItem.getActionList()) {
            printActionDetails(action);
        }
    }

    public static void printJunkItemInfo(JunkItem junkItem, boolean sellItem) {
        double sellPrice = sellItem ? junkItem.returnSellItemPrice() : junkItem.getPrice();
        System.out.print(junkItem.getName() + ", Item price: " + sellPrice + " golds");
    }

    public static void printCraftingReagentItemInfo(CraftingReagentItem craftingReagentItem, boolean sellItem) {
        double sellPrice = sellItem ? craftingReagentItem.returnSellItemPrice() : craftingReagentItem.getPrice();

        System.out.println(craftingReagentItem.getName() + ", Item Type: " + craftingReagentItem.getCraftingReagentItemType()
                + ", iLevel: " + craftingReagentItem.getItemLevel() + ", Item price: " + sellPrice + " golds");

    }

    public static void printActionDetails(Action action) {
        System.out.print("\t\t");
        System.out.print(ConsoleColor.YELLOW + "" + action.getActionName() + ConsoleColor.RESET + ": " + action.getCurrentActionValue());
        if (action instanceof ActionWithDuration) {
            System.out.print(" (duration: " + ((ActionWithDuration) action).getMaxActionTurns()
                    + " " + ((ActionWithDuration) action)
                    .getActionDurationType()
                    .toString()
                    .toLowerCase()
                    .replace("_", " ")
                    + " turns, max stacks: " + ((ActionWithDuration) action).getActionMaxStacks() + ")");
        }
        System.out.println();
    }

    public static void printEnterValidInput() {
        System.out.println(ConsoleColor.RED + "\tEnter valid input" + ConsoleColor.RESET);
    }

    public static void printIndexAndText(String index, String text) {
        System.out.print(ConsoleColor.CYAN + "\t" + index + ". " + ConsoleColor.RESET + text);
    }
}


























