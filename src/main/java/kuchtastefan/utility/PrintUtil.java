package kuchtastefan.utility;

import kuchtastefan.ability.Ability;
import kuchtastefan.characters.GameCharacter;
import kuchtastefan.characters.hero.Hero;
import kuchtastefan.gameSettings.GameSettings;
import kuchtastefan.items.consumeableItem.ConsumableItem;
import kuchtastefan.items.craftingItem.CraftingReagentItem;
import kuchtastefan.items.wearableItem.WearableItem;
import kuchtastefan.items.wearableItem.WearableItemType;

import java.util.Map;

public class PrintUtil {

    public static void printCurrentAbilityPoints(GameCharacter gameCharacter) {
        printLongDivider();
        System.out.print("\t\t\t\t\t\t\t\t");
        System.out.println(gameCharacter instanceof Hero ? "Your abilities:" : "Enemy abilities:");
        System.out.print("\t");
        for (Map.Entry<Ability, Integer> entry : gameCharacter.getAbilities().entrySet()) {
            System.out.print(entry.getKey() + ": " + entry.getValue() + ", ");
        }
        System.out.println();
        printLongDivider();
    }

    public static void printCurrentAbilityPointsWithItems(Hero hero) {
        printLongDivider();
        System.out.println("\t\t\t\t\t------ Ability points with items ------");
        System.out.print("\t");
        for (Map.Entry<Ability, Integer> entry : hero.getAbilities().entrySet()) {
            System.out.print(entry.getKey() + ": "
                    + (entry.getValue()
                    + hero.getWearingItemAbilityPoints().get(entry.getKey())) + ", ");
        }
        System.out.println();
        printLongDivider();
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
                + " (" + wearableItem.getItemQuality() + "), iLevel: " + wearableItem.getItemLevel());
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

        // TODO add \t in front of each line
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

    public static int printWearableItemCountByType(Hero hero, WearableItemType wearableItemType) {
        int count = 0;
        for (Map.Entry<WearableItem, Integer> item : hero.getHeroInventory().returnInventoryWearableItemMap().entrySet()) {
            if (item.getKey().getWearableItemType().equals(wearableItemType)) {
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

    public static void printConsumableItemFromList(Map<ConsumableItem, Integer> consumableItemMap) {
        int index = 1;
        for (Map.Entry<ConsumableItem, Integer> item : consumableItemMap.entrySet()) {
            System.out.print("\t" + index + ". (" + item.getValue() + "x) ");
            printConsumableItemInfo(item.getKey());
            System.out.println();
            index++;
        }
    }

    public static void printConsumableItemInfo(ConsumableItem consumableItem) {
        System.out.print(consumableItem.getName()
                + ", " + consumableItem.getConsumableItemType()
                + ", iLevel: " + consumableItem.getItemLevel());
        if (consumableItem.getRestoreAmount() != 0) {
            System.out.print(", Restore Amount: " + consumableItem.getRestoreAmount() + " health");
        }

        for (Ability ability : Ability.values()) {
            if (consumableItem.getIncreaseAbilityPoint().get(ability) != 0) {
                System.out.print(", increase " + ability + ": "
                        + consumableItem.getIncreaseAbilityPoint().get(ability) + ", ");
            }
        }
    }

    public static void printCraftingReagentItemInfo(CraftingReagentItem craftingReagentItem) {
        System.out.print(craftingReagentItem.getName()
                + ", " + craftingReagentItem.getCraftingReagentItemType()
                + ", iLevel: " + craftingReagentItem.getItemLevel());
    }
}


























