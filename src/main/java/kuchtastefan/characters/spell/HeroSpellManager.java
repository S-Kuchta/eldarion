package kuchtastefan.characters.spell;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kuchtastefan.characters.hero.Hero;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.LetterToNumberSpellLevel;
import kuchtastefan.utility.PrintUtil;
import kuchtastefan.utility.RuntimeTypeAdapterFactoryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HeroSpellManager {

    public void spellMenu(Hero hero) {
        System.out.println("\t0. Go back");
        System.out.println("\t1. Learn new Spell");
        System.out.println("\t2. Reset spells");
        System.out.println("\t3. Show learned spells");
        final int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
            }
            case 1 -> learnNewSpell(hero);
            case 2 -> resetSpells(hero);
            case 3 -> checkLearnedSpells(hero);
            default -> PrintUtil.printEnterValidInput();
        }
    }

    private void learnNewSpell(Hero hero) {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(
                RuntimeTypeAdapterFactoryUtil.actionsRuntimeTypeAdapterFactory).create();


        List<Spell> spellsByClass = new ArrayList<>();
        for (Spell spellForAdd : SpellsList.getSpellList()) {
            if (spellForAdd.getSpellClass().equals(hero.getCharacterClass())) {
                spellsByClass.add(spellForAdd);
            }
        }

        int levelChosen = 1;
        List<Spell> spellsByClassAndLevel = addSpellsToListFulfillingRequirements(levelChosen, spellsByClass);

        while (true) {
            PrintUtil.printExtraLongDivider();
            for (LetterToNumberSpellLevel letterToNumberSpellLevel : LetterToNumberSpellLevel.values()) {
                System.out.print("\t" + letterToNumberSpellLevel.name()
                        + ". Spell level: " + letterToNumberSpellLevel.getValue() + ", ");
            }

            System.out.println("\n\t0. Go back");
            int index = 1;
            for (Spell spell : spellsByClassAndLevel) {
                System.out.print("\t" + index + ". ");
                PrintUtil.printSpellDescription(hero, spell);
                index++;
                System.out.println();
            }

            String choice = InputUtil.stringScanner().toUpperCase();
            if (choice.matches("\\d+")) {
                try {
                    int parsedChoice = Integer.parseInt(choice);
                    System.out.println(parsedChoice);

                    if (parsedChoice == 0) {
                        hero.getCharacterSpellList().removeIf(spell -> spell.getSpellLevel() != 0);
                        for (Map.Entry<Integer, Spell> spellEntry : hero.getLearnedSpells().entrySet()) {
                            hero.getCharacterSpellList().add(gson.fromJson(gson.toJson(spellEntry.getValue()), Spell.class));
                        }
                        break;
                    }

                    Spell spellChosen = spellsByClassAndLevel.get(parsedChoice - 1);
                    if (spellChosen.getSpellLevel() > hero.getLevel()) {
                        System.out.println("\tYou don't meet minimum level requirements!");
                    } else {
                        if (hero.getLearnedSpells().containsKey(spellChosen.getSpellLevel())) {
                            System.out.println("\tYou unlearned " + hero.getLearnedSpells().get(levelChosen).getSpellName());
                        }
                        System.out.println("\tYou learned " + spellChosen.getSpellName());
                        hero.getLearnedSpells().put(spellChosen.getSpellLevel(), spellChosen);
                    }
                } catch (IndexOutOfBoundsException e) {
                    PrintUtil.printEnterValidInput();
                }
            } else {
                try {
                    levelChosen = LetterToNumberSpellLevel.valueOf(choice).getValue();
                    spellsByClassAndLevel = addSpellsToListFulfillingRequirements(levelChosen, spellsByClass);
                } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                    PrintUtil.printEnterValidInput();
                }
            }
        }
    }

    private List<Spell> addSpellsToListFulfillingRequirements(int level, List<Spell> spellsByClass) {
        List<Spell> spellListByClassAndLevel = new ArrayList<>();
        for (Spell spell : spellsByClass) {
            if (spell.getSpellLevel() == level) {
                spellListByClassAndLevel.add(spell);
            }
        }
        return spellListByClassAndLevel;
    }

    private void resetSpells(Hero hero) {
        hero.getLearnedSpells().clear();
        hero.getCharacterSpellList().removeIf(spell -> spell.getSpellLevel() != 0);
    }

    private void checkLearnedSpells(Hero hero) {
        for (Spell spell : hero.getCharacterSpellList()) {
            System.out.println("\t" + spell.getSpellName());
        }
    }
}
