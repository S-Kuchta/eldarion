package kuchtastefan.character.spell;

import kuchtastefan.character.CharacterClass;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.gameSettings.GameSetting;
import kuchtastefan.gameSettings.GameSettingsDB;
import kuchtastefan.hint.HintDB;
import kuchtastefan.hint.HintName;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.LetterToNumberSpellLevel;
import kuchtastefan.utility.printUtil.GameSettingsPrint;
import kuchtastefan.utility.printUtil.PrintUtil;
import kuchtastefan.utility.printUtil.SpellAndActionPrint;

import java.util.ArrayList;
import java.util.List;

public class HeroSpellManager {

    private CharacterClass characterClass;
    private int levelChosen;

    public HeroSpellManager() {
        this.levelChosen = 1;
    }


    public void spellMenu(Hero hero) {
        this.characterClass = hero.getCharacterClass();

        while (true) {
            PrintUtil.printMenuOptions("Go back", "Learn new Spell", "Reset spells", "Show learned spells");

            final int choice = InputUtil.intScanner();
            switch (choice) {
                case 0 -> {
                    return;
                }
                case 1 -> learnSpell(hero);
                case 2 -> resetSpells(hero);
                case 3 -> checkLearnedSpells(hero);
                default -> PrintUtil.printEnterValidInput();
            }
        }
    }

    /**
     * Allows the hero to learn new spells based on their class and level. Displays a list of available spells
     * according to the hero's class and current level. The hero can select a spell to learn, provided they meet
     * the minimum level requirement. Additionally, the hero can toggle options to show/hide spell descriptions
     * and spells on CoolDown. The method continuously prompts the hero for input until they choose to go back.
     *
     * @param hero The hero who is learning new spells.
     */
    private void learnSpell(Hero hero) {
        HintDB.printHint(HintName.NEW_SPELL_HINT);

        while (true) {
            printMenu(hero);
            String choice = InputUtil.stringScanner().toUpperCase();
            if (choice.equals("0")) {
                break;
            }

            if (choice.matches("\\d+")) {
                handleNumericChoice(hero, choice);
            } else {
                handleNonNumericChoice(choice);
            }
        }
    }

    private void printMenu(Hero hero) {
        PrintUtil.printExtraLongDivider();

        // Print the spell levels
        for (LetterToNumberSpellLevel letterToNumberSpellLevel : LetterToNumberSpellLevel.values()) {
            PrintUtil.printIndexAndText(letterToNumberSpellLevel.name(), "Spell level: " + letterToNumberSpellLevel.getValue() + ", ");
        }

        GameSettingsPrint.printSpellGameSettings();

        System.out.print("\n\n");
        PrintUtil.printIndexAndText("0", "Go back\n");

        int index = 1;
        for (Spell spell : returnSpellListByClassAndLevel()) {
            PrintUtil.printIndexAndText(String.valueOf(index++), "");
            SpellAndActionPrint.printSpellDescription(hero, null, spell);
            System.out.println();
        }
    }

    private void handleNumericChoice(Hero hero, String choice) {
        int parsedChoice = Integer.parseInt(choice);

        if (parsedChoice <= returnSpellListByClassAndLevel().size() && parsedChoice > 0) {
            Spell spellChosen = returnSpellListByClassAndLevel().get(parsedChoice - 1);
            if (spellChosen.getSpellLevel() > hero.getLevel()) {
                System.out.println("\tYou don't meet minimum level requirements!");
            } else {
                if (hero.getCharacterSpellList().contains(spellChosen)) {
                    return;
                }

                if (containsSpellOnLevel(spellChosen.getSpellLevel())) {
                    hero.getCharacterSpellList().removeIf(spell -> spell.getSpellLevel() == spellChosen.getSpellLevel());
                }


                System.out.println("\tYou learned " + spellChosen.getSpellName());
                hero.getCharacterSpellList().add(spellChosen);
            }
        } else {
            PrintUtil.printEnterValidInput();
        }
    }

    private void handleNonNumericChoice(String choice) {
        if (choice.equals("X")) {
            GameSettingsDB.setTrueOrFalse(GameSetting.SHOW_INFORMATION_ABOUT_ACTION_NAME);
        } else if (choice.equals("Y")) {
            GameSettingsDB.setTrueOrFalse(GameSetting.HIDE_SPELLS_ON_COOL_DOWN);
        } else {
            try {
                levelChosen = LetterToNumberSpellLevel.valueOf(choice).getValue();
            } catch (IllegalArgumentException e) {
                PrintUtil.printEnterValidInput();
            }
        }
    }

    private List<Spell> returnSpellListByClassAndLevel() {
        List<Spell> spells = new ArrayList<>();
        for (Spell spell : SpellDB.SPELL_LIST) {
            if (spell.getSpellClass().equals(characterClass) && spell.getSpellLevel() == levelChosen) {
                spells.add(spell);
            }
        }

        return spells;
    }

    private boolean containsSpellOnLevel(int spellLevel) {
        return returnSpellListByClassAndLevel().stream().anyMatch(spell -> spell.getSpellLevel() == spellLevel);
    }

    private void resetSpells(Hero hero) {
        hero.getCharacterSpellList().removeIf(spell -> spell.getSpellLevel() != 0);
    }

    private void checkLearnedSpells(Hero hero) {
        for (Spell spell : hero.getCharacterSpellList()) {
            System.out.print("\t");
            SpellAndActionPrint.printSpellDescription(hero, null, spell);
            System.out.println();
        }
    }
}
