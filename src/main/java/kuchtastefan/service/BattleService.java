package kuchtastefan.service;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.Action;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.npc.NonPlayerCharacter;
import kuchtastefan.character.npc.enemy.Enemy;
import kuchtastefan.character.spell.CharactersInvolvedInBattle;
import kuchtastefan.character.spell.Spell;
import kuchtastefan.gameSettings.GameSetting;
import kuchtastefan.gameSettings.GameSettingsDB;
import kuchtastefan.hint.HintDB;
import kuchtastefan.hint.HintName;
import kuchtastefan.item.itemFilter.ItemFilter;
import kuchtastefan.item.specificItems.consumeableItem.ConsumableItem;
import kuchtastefan.item.specificItems.consumeableItem.ConsumableItemType;
import kuchtastefan.utility.*;
import lombok.Getter;

import java.util.*;

@Getter
public class BattleService {

    private GameCharacter playerTarget;
    private String selectedEnemyForShowSelection;
    private final List<GameCharacter> enemyList;
    private final List<GameCharacter> alliesList;
    private final List<GameCharacter> tempCharacterList;
    private boolean heroPlay;

    public BattleService() {
        this.selectedEnemyForShowSelection = "A";
        this.enemyList = new ArrayList<>();
        this.alliesList = new ArrayList<>();
        this.tempCharacterList = new ArrayList<>();
    }


    public boolean battle(Hero hero, List<Enemy> enemies) {
        HintDB.printHint(HintName.BATTLE_HINT);

        // Initialize lists for battle
        enemyList.addAll(enemies);
        alliesList.add(hero);

        for (int i = 0; i < this.enemyList.size(); i++) {
            if (enemyList.get(i) instanceof Enemy enemy) {
                enemy.setCount(i);
            }
        }

        List<GameCharacter> allCharacters = new ArrayList<>();
        allCharacters.addAll(this.alliesList);
        allCharacters.addAll(this.enemyList);

        // Initialize variables for selected hero and enemy
        playerTarget = enemyList.getFirst();

        // Main battle loop
        while (true) {
            battleTurn(hero, allCharacters);
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            // Check if all enemies are defeated
            if (this.enemyList.isEmpty()) {
                return true; // Battle won
            }

            // Check if hero's health reaches zero
            if (hero.getEffectiveAbilityValue(Ability.HEALTH) <= 0) {
                return false; // Battle lost
            }
        }
    }

    /**
     * This method handles the turn mechanics during a battle.
     * It sorts the characters by their haste, determines if the first character is a Hero,
     * If the character has not died, it checks if they can perform an action.
     * If the character is a Hero and can perform an action, it triggers the player's turn.
     * If the character is not a Hero and can perform an action, it triggers the NPC's turn.
     * After the character's turn, it increases the cooldown of their spells and restores their health and mana.
     * If the character has died, it removes them from the list of characters.
     *
     * @param hero The hero character participating in the battle.
     * @param allCharacters A list of all characters participating in the battle.
     */
    private void battleTurn(Hero hero, List<GameCharacter> allCharacters) {
        sortCharactersByHaste(allCharacters);
        heroPlay = allCharacters.getFirst() instanceof Hero;
        ListIterator<GameCharacter> iterator = allCharacters.listIterator();

        while (iterator.hasNext()) {
            GameCharacter attackingCharacter = iterator.next();

            if (attackingCharacter.getEffectiveAbilityValue(Ability.HEALTH) > 0) {
                printBattleHeader(attackingCharacter);
                this.printAndPerformActionOverTime(attackingCharacter);
            }

            if (!checkIfCharacterDied(attackingCharacter)) {
                if (!attackingCharacter.isCanPerformAction()) {
                    if (heroPlay) {
                        heroPlay = false;
                    }
                } else {
                    if (heroPlay) {
                        playerTurn(hero);
                        heroPlay = false;
                    } else {
                        this.npcUseSpell(attackingCharacter, setNpcTarget(attackingCharacter), hero);
                    }
                }

                attackingCharacter.getCharacterSpellList().forEach(Spell::increaseSpellCoolDown);
                attackingCharacter.restoreHealthAndManaAfterTurn();
                addSummonedCreature(iterator, attackingCharacter);

            } else {
                iterator.remove();
            }
        }
    }

    private void printBattleHeader(GameCharacter attackingCharacter) {
        if (attackingCharacter instanceof Hero) {
            printCharacterStats();
            printEnemyStats();
        } else {
            npcPrintHeader(attackingCharacter);
        }
    }

    private GameCharacter setNpcTarget(GameCharacter attackingCharacter) {
        List<GameCharacter> returnList = returnListContainsCharacter(attackingCharacter);
        return returnList.get(RandomNumberGenerator.getRandomNumber(0, returnList.size() - 1));
    }

    private boolean checkIfCharacterDied(GameCharacter characterToCheck) {
        if (characterToCheck.getEffectiveAbilityValue(Ability.HEALTH) <= 0) {
            System.out.println();
            System.out.println("\t" + characterToCheck.getName() + ConsoleColor.RED + " died!" + ConsoleColor.RESET);
            returnListContainsCharacter(characterToCheck).remove(characterToCheck);

            setTarget();
            return true;
        } else {
            return false;
        }
    }

    private List<GameCharacter> returnListContainsCharacter(GameCharacter gameCharacter) {
        return alliesList.contains(gameCharacter) ? alliesList : enemyList;
    }

    private void addSummonedCreature(ListIterator<GameCharacter> iterator, GameCharacter attackingCharacter) {
        if (!this.tempCharacterList.isEmpty()) {
            for (GameCharacter character : this.tempCharacterList) {
                iterator.add(character);
                returnListContainsCharacter(attackingCharacter).add(character);
            }

            this.tempCharacterList.clear();
        }
    }

    private void sortCharactersByHaste(List<GameCharacter> characters) {
        characters.sort(Comparator.comparingInt(character -> -character.getEffectiveAbilityValue(Ability.HASTE)));
    }

    private void npcPrintHeader(GameCharacter gameCharacter) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        PrintUtil.printLongDivider();
        System.out.println("\t" + ConsoleColor.YELLOW_UNDERLINED
                + gameCharacter.getName() + " Turn!"
                + ConsoleColor.RESET);
        System.out.println();
    }

    private void setTarget() {
        if (!enemyList.isEmpty()) {
            playerTarget = enemyList.getFirst();
            selectedEnemyForShowSelection = "A";
        }
    }


    /**
     * This method handles the player's turn during a battle.
     * It prints the battle menu, takes the player's input, and performs the corresponding action.
     * The player can choose to cast a spell, use a potion, or change the selected enemy.
     * The turn continues until the player makes a valid choice that ends the turn, such as casting a spell or using a potion.
     *
     * @param hero The hero character controlled by the player.
     */
    private void playerTurn(Hero hero) {
        InventoryService inventoryService = new InventoryService();

        while (true) {
            printHeroBattleMenu(hero);
            String choice = InputUtil.stringScanner().toUpperCase();
            if (choice.matches("\\d+") && handleNumericChoice(hero, inventoryService, Integer.parseInt(choice))) {
                break;
            } else if (handleNonNumericChoice(choice)) {
                break;
            }
        }
    }

    private boolean handleNumericChoice(Hero hero, InventoryService inventoryService, int parsedChoice) {
        try {
            PrintUtil.printExtraLongDivider();
            if (parsedChoice == hero.getCharacterSpellList().size()) {
                return hero.getHeroInventory().selectItem(hero, ConsumableItem.class,
                        new ItemFilter(ConsumableItemType.POTION), inventoryService, 1);
            } else {
                return hero.getCharacterSpellList().get(parsedChoice).useSpell(
                        new CharactersInvolvedInBattle(hero, this.playerTarget, enemyList, alliesList, tempCharacterList));
            }
        } catch (IndexOutOfBoundsException e) {
            PrintUtil.printEnterValidInput();
            return false;
        }
    }

    private boolean handleNonNumericChoice(String choice) {
        if (choice.equals("X")) {
            GameSettingsDB.setTrueOrFalse(GameSetting.SHOW_INFORMATION_ABOUT_ACTION_NAME);
        } else if (choice.equals("Y")) {
            GameSettingsDB.setTrueOrFalse(GameSetting.HIDE_SPELLS_ON_COOL_DOWN);
        } else {
            setSelectedEnemy(choice);
        }

        return false;
    }

    private void setSelectedEnemy(String choice) {
        try {
            selectedEnemyForShowSelection = choice;
            playerTarget = enemyList.get(LetterToNumber.valueOf(choice).getValue() - 1);
        } catch (IllegalArgumentException e) {
            selectedEnemyForShowSelection = "A";
            playerTarget = enemyList.getFirst();
        } catch (IndexOutOfBoundsException e) {
            PrintUtil.printEnterValidInput();
        }
    }


    /**
     * This method is responsible for printing the battle menu during a battle.
     * It prints the stats of the characters, the enemies, the available spells, and the potions menu.
     *
     * @param hero The hero character participating in the battle.
     */
    private void printHeroBattleMenu(Hero hero) {
//        printCharacterStats();
//        printEnemyStats();

        int index = 1;
        for (GameCharacter enemy : enemyList) {
            if (enemy instanceof NonPlayerCharacter nonPlayerCharacter && !nonPlayerCharacter.isDefeated()) {
                printEnemySelection(index++, nonPlayerCharacter);
            }
        }

        PrintUtil.printSpellGameSettings();
        System.out.println();
        printHeroSpells(hero);
        PrintUtil.printIndexAndText(String.valueOf(hero.getCharacterSpellList().size()), "Potions Menu");
        System.out.println();
    }

    private void printCharacterStats() {
        for (GameCharacter gameCharacter : this.alliesList) {
            PrintUtil.printHeaderWithStatsBar(gameCharacter);
            PrintUtil.printEffectiveAbilityPoints(gameCharacter);
            PrintUtil.printBattleBuffs(gameCharacter);
        }
    }

    private void printEnemyStats() {
        PrintUtil.printHeaderWithStatsBar(playerTarget);
        PrintUtil.printEffectiveAbilityPoints(playerTarget);
        PrintUtil.printBattleBuffs(playerTarget);
    }

    private void printEnemySelection(int index, NonPlayerCharacter nonPlayerCharacter) {
        ConsoleColor consoleColor = ConsoleColor.RESET;
        ConsoleColor healthsColor = ConsoleColor.RESET;
        if (Objects.equals(LetterToNumber.getStringFromValue(index), selectedEnemyForShowSelection)) {
            consoleColor = ConsoleColor.WHITE_BRIGHT;
            healthsColor = ConsoleColor.RED;
        }

        String enemy = consoleColor + nonPlayerCharacter.getName() + " - "
                + nonPlayerCharacter.getCharacterRarity() + " - "
                + healthsColor + "Healths: "
                + nonPlayerCharacter.getEffectiveAbilityValue(Ability.HEALTH) + ConsoleColor.RESET;

        PrintUtil.printIndexAndText(LetterToNumber.getStringFromValue(index), enemy);
    }

    private void printHeroSpells(Hero hero) {
        int spellIndex = 0;
        System.out.println();
        for (Spell spell : hero.getCharacterSpellList()) {
            if (shouldPrintSpell(spell)) {
                System.out.print(ConsoleColor.CYAN + "\t" + spellIndex + ". " + ConsoleColor.RESET);
                PrintUtil.printSpellDescription(hero, this.playerTarget, spell);
                System.out.println();
            }
            spellIndex++;
        }
    }

    private boolean shouldPrintSpell(Spell spell) {
        return !GameSettingsDB.returnGameSettingValue(GameSetting.HIDE_SPELLS_ON_COOL_DOWN) || spell.isCanSpellBeCasted();
    }


    /**
     * Determines the spell the enemy character will cast during battle based on certain criteria.
     * Evaluates factors such as potential damage, healing ability, and utility of each spell.
     * The spell with the highest priority points is selected for casting.
     *
     * @param spellCaster The enemy character casting the spell.
     * @param spellTarget The target of the spell (usually the player's character).
     */
    private void npcUseSpell(GameCharacter spellCaster, GameCharacter spellTarget, Hero hero) {
        Map<Spell, Integer> spells = new HashMap<>();
        Spell spellToCast = spellCaster.getCharacterSpellList().getFirst();
        spellCaster.getCharacterSpellList().forEach(spell -> spells.put(spell, 0));

        for (Map.Entry<Spell, Integer> spellIntegerEntry : spells.entrySet()) {
            if (spellIntegerEntry.getKey().isCanSpellBeCasted()) {
                int priorityPoints = 0;
                for (Action action : spellIntegerEntry.getKey().getSpellActions()) {
                    priorityPoints += action.returnPriorityPoints(spellCaster, spellTarget);
                }

                // Determine the spell with the highest priority points
                spellIntegerEntry.setValue(spellIntegerEntry.getValue() + priorityPoints);
                if (spellIntegerEntry.getValue() > spells.get(spellToCast)) {
                    spellToCast = spellIntegerEntry.getKey();
                }
            }
        }

        spellToCast.useSpell(new CharactersInvolvedInBattle(hero, spellCaster, spellTarget, alliesList, enemyList, tempCharacterList));
    }

    private void printAndPerformActionOverTime(GameCharacter gameCharacter) {
        if (!gameCharacter.getBuffsAndDebuffs().isEmpty()) {
            System.out.println("\t" + "Buffs & Debuffs");
            gameCharacter.performActionsWithDuration(true);
            System.out.println();
        }
    }

    public void resetSpellsCoolDowns(Hero hero) {
        hero.getCharacterSpellList().forEach(spell -> {
            spell.setCurrentTurnCoolDown(spell.getTurnCoolDown() + 1);
            spell.checkSpellCoolDown();
        });
    }
}
