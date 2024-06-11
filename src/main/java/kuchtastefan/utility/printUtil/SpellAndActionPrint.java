package kuchtastefan.utility.printUtil;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;
import kuchtastefan.actions.Action;
import kuchtastefan.actions.actionsWithDuration.ActionWithAffectingAbility;
import kuchtastefan.actions.actionsWithDuration.ActionWithDuration;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.spell.Spell;
import kuchtastefan.gameSettings.GameSetting;
import kuchtastefan.gameSettings.GameSettingsDB;
import kuchtastefan.utility.ConsoleColor;

import java.util.HashSet;

public class SpellAndActionPrint {

    public static void printSpellDescription(Hero hero, GameCharacter spellTarget, Spell spell) {
        System.out.print(ConsoleColor.MAGENTA + spell.getSpellName() + ConsoleColor.RESET
                + " [Mana Cost: " + ConsoleColor.BLUE + spell.getSpellManaCost() + ConsoleColor.RESET + "]");
        if (spell.getTurnCoolDown() > 0) {
            System.out.print("[CoolDown: " + printActionTurnCoolDown(spell.getCurrentTurnCoolDown(), spell.getTurnCoolDown()) + "]");
        }

        if (spell.isHitAllEnemy()) {
            System.out.print("[Hit All Enemies]");
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
            if (action instanceof ActionWithDuration actionWithDuration) {
                if (actionWithDuration.getActionMaxStacks() > 1) {
                    System.out.print(" [Stackable to: " + actionWithDuration.getActionMaxStacks() + "]");
                }
            }
            if (GameSettingsDB.returnGameSettingValue(GameSetting.SHOW_INFORMATION_ABOUT_ACTION_NAME)) {
                System.out.print("\n\t\t" + action.getActionName().getDescription());
            }
        }
    }

    public static void printBuffTable(GameCharacter gameCharacter) {
        if (gameCharacter.getBuffsAndDebuffs() == null) {
            gameCharacter.setBuffsAndDebuffs(new HashSet<>());
        }

        if (!gameCharacter.getBuffsAndDebuffs().isEmpty()) {
            buffTable(gameCharacter);
        }
    }

    private static void buffTable(GameCharacter gameCharacter) {
        AsciiTable at = new AsciiTable();
        CWC_LongestLine cwc = new CWC_LongestLine();

        at.addRule();
        for (ActionWithDuration action : gameCharacter.getBuffsAndDebuffs()) {
            String ability = action instanceof ActionWithAffectingAbility affectingAbility ? affectingAbility.getAbility()
                    + " by " + action.getCurrentActionValue() : "Value: " + action.getCurrentActionValue();

            at.addRow(action.getActionName(),
                    ability,
                    "Turns: " + printActionTurnRemaining(action.getCurrentActionTurn(), action.getMaxActionTurns()),
                    "Stacks: " + printActionTurnRemaining(action.getActionCurrentStacks(), action.getActionMaxStacks()),
                    action.getActionStatusEffect());
        }

        at.addRule();
        at.setPaddingLeft(2);
        at.setPaddingRight(2);
        at.getContext().setGridTheme(253);
        at.getContext().setFrameLeftMargin(4);
        cwc.add(25, 50);
        at.getRenderer().setCWC(cwc);
        System.out.println(at.render());
    }

    private static StringBuilder printActionTurnRemaining(int currentValue, int maxValue) {
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

    private static StringBuilder printActionTurnCoolDown(int currentValue, int maxValue) {
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
