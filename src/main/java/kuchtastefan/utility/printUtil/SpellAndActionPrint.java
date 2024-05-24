package kuchtastefan.utility.printUtil;

import de.vandermeer.asciitable.AsciiTable;
import kuchtastefan.actions.Action;
import kuchtastefan.actions.actionsWIthDuration.ActionWithAffectingAbility;
import kuchtastefan.actions.actionsWIthDuration.ActionWithDuration;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.spell.Spell;
import kuchtastefan.gameSettings.GameSetting;
import kuchtastefan.gameSettings.GameSettingsDB;
import kuchtastefan.utility.ConsoleColor;

import java.util.HashSet;
import java.util.Set;

public class SpellAndActionPrint {

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
            String printChanceToPerform = action.getChanceToPerformAction() == 100 ? "" : " [" + action.getChanceToPerformAction() + "% chance to perform]";
            System.out.print(printChanceToPerform);
            if (GameSettingsDB.returnGameSettingValue(GameSetting.SHOW_INFORMATION_ABOUT_ACTION_NAME)) {
                System.out.print("\n\t\t" + action.getActionName().getDescription());
            }
        }
    }

    public static void printBattleBuffs(GameCharacter gameCharacter) {
        if (gameCharacter.getBuffsAndDebuffs() == null) {
            gameCharacter.setBuffsAndDebuffs(new HashSet<>());
        }

        if (!gameCharacter.getBuffsAndDebuffs().isEmpty()) {
            generateTableWithBuffs(gameCharacter.getBuffsAndDebuffs());
        }
    }

    public static void generateTableWithBuffs(Set<ActionWithDuration> actionWithDurationList) {
        AsciiTable at = new AsciiTable();

        at.addRule();
        for (ActionWithDuration action : actionWithDurationList) {
            String ability = action instanceof ActionWithAffectingAbility affectingAbility ? "Ability: " + affectingAbility.getAbility() : "";
            at.addRow(action.getActionName(),
                    ability,
                    "Turns: " + printActionTurnRemaining(action.getCurrentActionTurn(), action.getMaxActionTurns()),
                    "Stacks: " + printActionTurnRemaining(action.getActionCurrentStacks(), action.getActionMaxStacks()));
        }

        at.addRule();
        at.setPaddingLeft(2);
        at.getContext().setGridTheme(253);
        String rend = at.render(120);
        System.out.println(rend);
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

}
