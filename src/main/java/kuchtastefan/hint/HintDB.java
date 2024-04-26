package kuchtastefan.hint;

import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.PrintUtil;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class HintDB {

    @Getter
    private static final Map<HintName, Hint> HINT_DB = new HashMap<>();

    public static void initializeHintList() {
        HINT_DB.put(HintName.BLACKSMITH_HINT, new Hint("""
                \t\t- You can dismantle wearable item. This will destroy item,
                \tbut you get crafting reagents (items),
                \t\t- You can refinement wearable item. This will increase item
                \tprice and ability bonus, For refinement item you need crafting reagents (items)"""));

        HINT_DB.put(HintName.WELCOME, new Hint("""
                \tHello, my name is Stefan, and I am creator of this game.
                \tI will guide you through the game for a short time.
                \tIf you find the slow text print bothersome, you can disable it in the game settings.
                \tI hope you will enjoy the game! Good luck with exploring the world of Eldarion!"""));

        HINT_DB.put(HintName.QUEST_HINT, new Hint("""
                \tThroughout the game, you will be completing quests. For each completed quest, you will receive a reward.
                \tEach quest can have multiple Quest Objectives that you need to fulfill to complete the quest.
                \tAvailable quests will be marked with a yellow exclamation point ( - ! - ),
                \twhile completed but not yet turned in quests will be marked with a yellow question mark ( - ? - ).
                \tYou may also encounter a gray exclamation point, indicating that you have not yet met the requirements
                \tto accept that quest (insufficient level, need to complete a prerequisite quest)."""));

        HINT_DB.put(HintName.HERO_MENU, new Hint("""
                \tIn the hero menu, you can view your statistics, inventory, add or subtract ability points
                \tcheck accepted and completed quests, learn new spells, and adjust game settings."""));

        HINT_DB.put(HintName.NEW_SPELL_HINT, new Hint("""
                \tHere you can learn a new spell. You can learn one new spell on levels 1, 2, 3, 5 and 7.
                \tWhen you want to change a learned spell, simply learn a new one, and the old one will be unlearned."""));

        HINT_DB.put(HintName.REGION_HINT, new Hint("""
                \tWelcome to the region. Here you can travel across the region, discover new locations, and complete quests.
                \tHowever, be cautious of traps while traveling through locations."""));

        HINT_DB.put(HintName.LOCATION_HINT, new Hint("""
                \tWelcome to the location. The Location consists of multiple stages.
                \tIf a stage is not open from the beginning, you can only access it after completing the previous stage.
                \tTo enter some stages, you need an item to unlock the stage.
                \tAfter completing all stages, you will receive a reward."""));

        HINT_DB.put(HintName.BATTLE_HINT, new Hint("""
                \tOne of the most important parts of the game are the battles. Battles occur in turns.
                \tCombat is conducted using spells, each spell has actions that are performed after using the spell.
                \tActions with durations are always performed before of the character's turn.
                \tHowever, be careful, some actions do not have a 100% chance of execution.
                \tIf action has lower chance to perform, you will see percentage chance in action description.
                \tSpells on CoolDowns can't be used.
                \tTo receive rewards or fulfill Quest Objectives, you must defeat all units and win the battle."""));
    }

    public static void printHint(HintName hintName) {
        for (Map.Entry<HintName, Hint> hint : HINT_DB.entrySet()) {
            if (hint.getKey().equals(hintName) && !hint.getValue().isShowed()) {
                PrintUtil.printExtraLongDivider();
                PrintUtil.printStringSlowly(ConsoleColor.YELLOW + "\t" + hintName + ConsoleColor.RESET + "\n" + hint.getValue().getText());
                PrintUtil.printExtraLongDivider();
                hint.getValue().setShowed(true);
            }
        }
    }

    public static void resetAllHints() {
        for (Map.Entry<HintName, Hint> hintEntry : HINT_DB.entrySet()) {
            hintEntry.getValue().setShowed(false);
        }
    }
}
