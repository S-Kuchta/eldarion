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

    public BattleService() {
        this.selectedEnemyForShowSelection = "A";
        this.enemyList = new ArrayList<>();
        this.alliesList = new ArrayList<>();
        this.tempCharacterList = new ArrayList<>();
    }


    /**
     * This method handles the main battle loop in the game.
     * It initializes the battle by adding the hero and enemies to their respective lists and setting the player's target.
     * Then it enters a loop that continues until either all enemies are defeated or the hero's health reaches zero.
     * In each iteration of the loop, it calls the battleTurn method to handle the turn mechanics.
     * If all enemies are defeated, it returns true indicating a victory for the player.
     * If the hero's health reaches zero, it returns false indicating a defeat.
     * Between each turn, there is a delay of 2.5 seconds to allow the player to read the battle log.
     *
     * @param hero    The hero character controlled by the player.
     * @param enemies A list of enemy characters the player will fight against.
     * @return boolean True if the player wins the battle, false if the player loses.
     */
    public boolean battle(Hero hero, List<Enemy> enemies) {
        HintDB.printHint(HintName.BATTLE_HINT);

        // Initialize lists for battle
        enemyList.addAll(enemies);
        alliesList.add(hero);

        List<GameCharacter> allCharacters = new ArrayList<>();
        allCharacters.addAll(this.alliesList);
        allCharacters.addAll(this.enemyList);

        playerTarget = enemyList.getFirst();
        battleMechanic(hero, allCharacters);
        return this.enemyList.isEmpty();
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
     * @param hero          The hero character participating in the battle.
     * @param allCharacters A list of all characters participating in the battle.
     */
    private void battleMechanic(Hero hero, List<GameCharacter> allCharacters) {
        sortCharactersByHaste(allCharacters);
        int currentTurn = 0;

        while (!isBattleFinished() || hero.getEffectiveAbilityValue(Ability.HEALTH) > 0) {
            sortCharactersByHaste(allCharacters);
            if (currentTurn >= allCharacters.size()) {
                currentTurn = 0;
            }

            if (isBattleFinished()) {
                return;
            }

            GameCharacter attackingCharacter = allCharacters.get(currentTurn);
            GameCharacter target = setNpcTarget(attackingCharacter);

            this.printBattleHeader(attackingCharacter);
            this.printAndPerformActionOverTime(attackingCharacter, target);

            if (checkIfCharacterDied(attackingCharacter, allCharacters)) {
                currentTurn++;
                continue;
            }


            if (attackingCharacter.isCanPerformAction()) {
                if (attackingCharacter instanceof Hero) {
                    playerTurn(hero);
                    target = playerTarget;
                } else {
                    this.npcUseSpell(attackingCharacter, target, hero);
                }
            }

            checkIfCharacterDied(target, allCharacters);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            attackingCharacter.getCharacterSpellList().forEach(Spell::increaseSpellCoolDown);
            attackingCharacter.restoreHealthAndManaAfterTurn();
            addSummonedCreature(attackingCharacter, allCharacters);
            currentTurn++;
        }
    }

    private boolean isBattleFinished() {
        return alliesList.isEmpty() || enemyList.isEmpty();
    }

    private void printBattleHeader(GameCharacter attackingCharacter) {
        if (attackingCharacter instanceof Hero) {
            printAliesStats();
        } else {
            npcPrintHeader(attackingCharacter);
        }
    }

    private void printAliesStats() {
        for (GameCharacter gameCharacter : this.alliesList) {
            printCharacterStats(gameCharacter);
        }
    }

    private void printCharacterStats(GameCharacter gameCharacter) {
        PrintUtil.printHeaderWithStatsBar(gameCharacter);
        PrintUtil.printEffectiveAbilityPoints(gameCharacter);
        PrintUtil.printBattleBuffs(gameCharacter);
    }

    private GameCharacter setNpcTarget(GameCharacter attackingCharacter) {
        List<GameCharacter> returnList = getCharacterList(attackingCharacter, false);
        return returnList.get(RandomNumberGenerator.getRandomNumber(0, returnList.size() - 1));
    }

    private boolean checkIfCharacterDied(GameCharacter characterToCheck, List<GameCharacter> allCharacters) {
        if (characterToCheck.getEffectiveAbilityValue(Ability.HEALTH) <= 0) {
            if (characterToCheck instanceof NonPlayerCharacter) {
                System.out.println("\n\t" + ConsoleColor.RED + characterToCheck.getNameWithoutColor() + " died!" + ConsoleColor.RESET);
            }

            getCharacterList(characterToCheck, true).remove(characterToCheck);
            allCharacters.remove(characterToCheck);
            setTarget();
            return true;
        }

        return false;
    }

    private List<GameCharacter> getCharacterList(GameCharacter gameCharacter, boolean isSameList) {
        return alliesList.contains(gameCharacter) == isSameList ? alliesList : enemyList;
    }

    private void addSummonedCreature(GameCharacter attackingCharacter, List<GameCharacter> allCharacters) {
        if (!this.tempCharacterList.isEmpty()) {
            for (GameCharacter character : this.tempCharacterList) {
                getCharacterList(attackingCharacter, true).add(character);
                allCharacters.add(character);
            }

            this.tempCharacterList.clear();
        }

        sortCharactersByHaste(allCharacters);
    }

    private void sortCharactersByHaste(List<GameCharacter> characters) {
        characters.sort(Comparator.comparingInt(character -> -character.getEffectiveAbilityValue(Ability.HASTE)));
    }

    private void npcPrintHeader(GameCharacter gameCharacter) {
        PrintUtil.printLongDivider();
        System.out.println("\t" + ConsoleColor.YELLOW_UNDERLINED + gameCharacter.getName() + " Turn!" + ConsoleColor.RESET);
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
        InventoryMenuService inventoryMenuService = new InventoryMenuService();

        while (true) {
            printHeroBattleMenu(hero);
            String choice = InputUtil.stringScanner().toUpperCase();
            if (choice.matches("\\d+") && handleNumericChoice(hero, inventoryMenuService, Integer.parseInt(choice))) {
                break;
            } else if (handleNonNumericChoice(choice)) {
                break;
            }
        }
    }

    private boolean handleNumericChoice(Hero hero, InventoryMenuService inventoryMenuService, int parsedChoice) {
        try {
            PrintUtil.printExtraLongDivider();
            if (parsedChoice == hero.getCharacterSpellList().size()) {
                return hero.getHeroInventory().selectItem(hero, ConsumableItem.class,
                        new ItemFilter(ConsumableItemType.POTION), inventoryMenuService, 1);
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
        printCharacterStats(playerTarget);

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
     * There is 20% chance to cast a random spell
     *
     * @param spellCaster The enemy character casting the spell.
     * @param spellTarget The target of the spell (usually the player's character).
     */
    private void npcUseSpell(GameCharacter spellCaster, GameCharacter spellTarget, Hero hero) {
        Map<Spell, Integer> spells = new HashMap<>();
        Spell spellToCast = spellCaster.getCharacterSpellList().getFirst();

        spellCaster.getCharacterSpellList().forEach(spell -> {
            if (spell.isCanSpellBeCasted()) {
                spells.put(spell, 0);
            }
        });

        if (RandomNumberGenerator.getRandomNumber(0, 100) <= 20) {
            List<Spell> spellList = new ArrayList<>(spells.keySet());
            spellToCast = spellList.get(RandomNumberGenerator.getRandomNumber(0, spellList.size() - 1));
        } else {
            for (Map.Entry<Spell, Integer> spellIntegerEntry : spells.entrySet()) {
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

    //    private void printAndPerformActionOverTime(GameCharacter gameCharacter) {
//        if (!gameCharacter.getBuffsAndDebuffs().isEmpty()) {
//            System.out.println("\t" + "Buffs & Debuffs");
//        }
//
//        gameCharacter.performActionsWithDuration(true);
//    }
    private void printAndPerformActionOverTime(GameCharacter spellCaster, GameCharacter spellTarget) {
        if (!spellCaster.getBuffsAndDebuffs().isEmpty()) {
            System.out.println("\t" + "Buffs & Debuffs");
        }

        spellCaster.performActionsWithDuration(spellTarget, spellCaster);
    }


    public void resetSpellsCoolDowns(Hero hero) {
        hero.getCharacterSpellList().forEach(spell -> {
            spell.setCurrentTurnCoolDown(spell.getTurnCoolDown() + 1);
            spell.checkSpellCoolDown();
        });
    }
}
