package kuchtastefan.service;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.Action;
import kuchtastefan.actions.actionsWIthDuration.*;
import kuchtastefan.actions.instantActions.ActionDealDamage;
import kuchtastefan.actions.instantActions.ActionRestoreHealth;
import kuchtastefan.actions.instantActions.ActionRestoreMana;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.NpcCharacter;
import kuchtastefan.character.enemy.Enemy;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.hero.inventory.InventoryMenuService;
import kuchtastefan.character.spell.Spell;
import kuchtastefan.constant.Constant;
import kuchtastefan.constant.ConstantSymbol;
import kuchtastefan.gameSettings.GameSettings;
import kuchtastefan.hint.HintName;
import kuchtastefan.hint.HintUtil;
import kuchtastefan.utility.*;

import java.util.*;

public class BattleService {

    public boolean battle(Hero hero, List<Enemy> enemies) {
        HintUtil.printHint(HintName.BATTLE_HINT);
        InventoryMenuService inventoryMenuService = new InventoryMenuService();

        // Initialize lists for battle
        List<GameCharacter> enemyList = new ArrayList<>(enemies);
        List<GameCharacter> alliesList = new ArrayList<>();
        List<GameCharacter> tempCharacterList = new ArrayList<>();
        alliesList.add(hero);
//        Iterator<GameCharacter> alliesIterator = alliesList.iterator();

        // Initialize variables for selected hero and enemy
        String selectedHeroForShowSelected = "A";
        GameCharacter enemyChosen = enemyList.getFirst();

        // Determine if the hero plays first based on HASTE ability
        boolean heroPlay = true;
        for (GameCharacter enemy : enemyList) {
            if (enemy.getCurrentAbilityValue(Ability.HASTE) >= hero.getCurrentAbilityValue(Ability.HASTE)) {
                heroPlay = false;
                break;
            }
        }

        hero.getBattleActionsWithDuration().clear();
        hero.getBattleActionsWithDuration().addAll(hero.getRegionActionsWithDuration());

        // Main battle loop
        while (true) {


            // Hero's turn
            if (heroPlay) {
                for (GameCharacter gameCharacter : alliesList) {

//                while (alliesIterator.hasNext()) {

//                    GameCharacter gameCharacter = alliesIterator.next();
                    gameCharacter.checkAndRemoveActionTurns();

                    // If hero can't perform action, skip to next turn
                    if (!gameCharacter.isCanPerformAction()) {
                        heroPlay = false;
                        this.printAndPerformActionOverTime(gameCharacter);
                        checkSpellsCoolDowns(gameCharacter);
                        continue;
                    }

                    if (gameCharacter instanceof Hero) {
                        // Loop for hero's actions
                        while (true) {
                            printBattleMenu((Hero) gameCharacter, enemyChosen, selectedHeroForShowSelected, enemyList, alliesList);

                            // Get user's choice
                            String choice = InputUtil.stringScanner().toUpperCase();
                            if (choice.matches("\\d+")) {
                                try {
                                    PrintUtil.printExtraLongDivider();
                                    int parsedChoice = Integer.parseInt(choice);
                                    if (parsedChoice == hero.getCharacterSpellList().size()) {

                                        // If choice is for consumable items, open inventory menu
                                        if (inventoryMenuService.consumableItemsMenu(hero, true)) {
                                            checkSpellsCoolDowns(hero);
                                            break;
                                        }
                                    } else {
                                        // If choice is for a spell, use the spell on the enemy
                                        if (hero.getCharacterSpellList().get(parsedChoice).useSpell(hero, enemyChosen, enemyList, alliesList, tempCharacterList)) {
                                            checkSpellsCoolDowns(hero);
                                            break;
                                        }
                                    }
                                } catch (IndexOutOfBoundsException e) {
                                    PrintUtil.printEnterValidInput();
                                }
                            } else {
                                // Handle special commands like showing action names or hiding CoolDowns
                                if (choice.equals("X")) {
                                    GameSettings.setShowInformationAboutActionName();
                                } else if (choice.equals("Y")) {
                                    GameSettings.setHideSpellsOnCoolDown();
                                } else {
                                    try {
                                        selectedHeroForShowSelected = choice;
                                        enemyChosen = enemyList.get(LetterToNumber.valueOf(choice).getValue() - 1);
                                    } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                                        selectedHeroForShowSelected = "A";
                                        enemyChosen = enemyList.getFirst();
                                        PrintUtil.printEnterValidInput();
                                    }
                                }
                            }
                        }
                    } else {
                        PrintUtil.printLongDivider();
                        System.out.println("\t\t" + ConstantSymbol.SWORD_SYMBOL
                                + " " + gameCharacter.getName() + " Turn!"
                                + ConstantSymbol.SWORD_SYMBOL);
                        System.out.println();

                        characterUseSpell(gameCharacter, enemyList.get(RandomNumberGenerator.getRandomNumber(0, enemyList.size() - 1)), enemyList, alliesList, tempCharacterList);
                    }

                    this.printAndPerformActionOverTime(gameCharacter);
                    heroPlay = false;
                }

                checkIfCharacterDied(alliesList);

                alliesList.addAll(tempCharacterList);
                tempCharacterList.clear();
            } else {
                Iterator<GameCharacter> iterator = enemyList.iterator();
                GameCharacter enemyAttack = hero;
                while (iterator.hasNext()) {
                    GameCharacter enemyInCombat = iterator.next();

                    // If enemy is alive
                    if (enemyInCombat.getCurrentAbilityValue(Ability.HEALTH) > 0) {

                        enemyInCombat.checkAndRemoveActionTurns();
                        checkSpellsCoolDowns(enemyInCombat);

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            System.out.println(e.getMessage());
                        }

                        // Print enemy's turn
                        PrintUtil.printLongDivider();
                        System.out.println("\t\t" + ConstantSymbol.SWORD_SYMBOL
                                + " " + enemyInCombat.getName() + " Turn!"
                                + ConstantSymbol.SWORD_SYMBOL);
                        System.out.println();

                        // If enemy can't perform action, skip to next enemy
                        if (!enemyInCombat.isCanPerformAction()) {
                            this.printAndPerformActionOverTime(enemyInCombat);
                            continue;
                        }

                        // Select random character for enemy attack from alliesList
                        if (alliesList.size() > 1) {
                            enemyAttack = alliesList.get(RandomNumberGenerator.getRandomNumber(0, alliesList.size() - 1));
                        }
                        this.characterUseSpell(enemyInCombat, enemyAttack, alliesList, enemyList, tempCharacterList);
                        this.printAndPerformActionOverTime(enemyInCombat);
                    }

                    // If enemy is killed
                    if (enemyInCombat.getCurrentAbilityValue(Ability.HEALTH) <= 0) {

                        System.out.println();
                        System.out.println("\t" + ConsoleColor.RED + enemyInCombat.getName() + " died!" + ConsoleColor.RESET);

                        // Remove enemy from the list
                        iterator.remove();
                        if (!enemyList.isEmpty()) {
                            enemyChosen = enemyList.getFirst();
                            selectedHeroForShowSelected = "A";
                        }
                    }

                    checkIfCharacterDied(alliesList);
                }

                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                heroPlay = true;
            }

            // Check if all enemies are defeated
            if (enemyList.isEmpty()) {
                // Clear hero's battle actions and reset spell CoolDowns
                hero.getBattleActionsWithDuration().clear();
                this.resetSpellsCoolDowns(hero);
                return true; // Battle won
            }

            // Check if hero's health reaches zero
            if (hero.getCurrentAbilityValue(Ability.HEALTH) <= 0) {
                // Clear hero's actions and battle actions, deduct gold, reset health, and reset spell CoolDowns
                hero.getRegionActionsWithDuration().clear();
                hero.getBattleActionsWithDuration().clear();
                int goldToRemove = Constant.GOLD_TO_REMOVE_PER_LEVEL_AFTER_DEAD * hero.getLevel();

                hero.checkHeroGoldsAndSubtractIfIsEnough(goldToRemove);
                hero.getCurrentAbilities().put(Ability.HEALTH, hero.getMaxAbilities().get(Ability.HEALTH));
                this.resetSpellsCoolDowns(hero);

                // Print death message
                PrintUtil.printDivider();
                System.out.println("\tYou lost " + goldToRemove + " golds!");
                System.out.println("\t" + ConsoleColor.RED + "You have died!" + ConsoleColor.RESET);
                PrintUtil.printDivider();
                return false; // Battle lost
            }
        }
    }

    private void checkIfCharacterDied(List<GameCharacter> characters) {
        Iterator<GameCharacter> iterator = characters.iterator();
        while (iterator.hasNext()) {
            GameCharacter gameCharacter = iterator.next();
            if (gameCharacter.getCurrentAbilityValue(Ability.HEALTH) <= 0) {
                iterator.remove();
                System.out.println();
                System.out.println("\t" + ConsoleColor.RED + gameCharacter.getName() + " died!" + ConsoleColor.RESET);
            }
        }
    }

    private void printAndPerformActionOverTime(GameCharacter gameCharacter) {
        System.out.println("\n\t" + gameCharacter.getName() + " actions over time");
        gameCharacter.updateCurrentCharacterStateDependsOnActiveActionsAndIncreaseTurn(ActionDurationType.BATTLE_ACTION);
        gameCharacter.restoreAbility(gameCharacter.getCurrentAbilityValue(Ability.INTELLECT)
                * Constant.RESTORE_MANA_PER_ONE_INTELLECT, Ability.MANA);
    }

    private void printBattleMenu(Hero hero, GameCharacter enemyChosen, String selectedHeroForShowSelected, List<GameCharacter> enemyList, List<GameCharacter> alliestList) {
        // Print hero's header with stats and buffs
        PrintUtil.printHeaderWithStatsBar(hero);
        PrintUtil.printBattleBuffs(hero);

        // Print alies header with stats and buffs
        for (GameCharacter gameCharacter : alliestList) {
            if (!(gameCharacter instanceof Hero)) {
                PrintUtil.printHeaderWithStatsBar(gameCharacter);
                PrintUtil.printBattleBuffs(gameCharacter);
                PrintUtil.printExtraLongDivider();
            }
        }

        // Print enemy's header with stats and buffs
        PrintUtil.printHeaderWithStatsBar(enemyChosen);
        PrintUtil.printBattleBuffs(enemyChosen);
        PrintUtil.printExtraLongDivider();

        int index = 1;
        System.out.print("\t");
        // Print available enemies for selection
        for (GameCharacter enemyFromList : enemyList) {
            if (!((NpcCharacter) enemyFromList).isDefeated()) {
                System.out.print(ConsoleColor.CYAN + LetterToNumber.getStringFromValue(index) + ConsoleColor.RESET
                        + ". " + enemyFromList.getName() + " - " + ((NpcCharacter) enemyFromList).getCharacterRarity() + " - "
                        + " Healths: "
                        + enemyFromList.getCurrentAbilityValue(Ability.HEALTH) + " ");

                // Highlight selected enemy
                if (Objects.equals(LetterToNumber.getStringFromValue(index), selectedHeroForShowSelected)) {
                    System.out.print(ConstantSymbol.SWORD_SYMBOL + " ");
                }
                index++;
            }
        }

        // Print settings for hiding action description
        System.out.println();
        PrintUtil.printIndexAndText("X", "Hide action description - ");
        PrintUtil.printGameSettings(GameSettings.isShowInformationAboutActionName());

        // Print settings for hiding spells on CoolDown
        System.out.print("\t");
        PrintUtil.printIndexAndText("Y", "Hide spells on CoolDown - ");
        PrintUtil.printGameSettings(GameSettings.isHideSpellsOnCoolDown());

        // Print hero's spells with descriptions
        int spellIndex = 0;
        System.out.println();
        for (Spell spell : hero.getCharacterSpellList()) {
            // Check if spells should be hidden when on CoolDown
            if (GameSettings.isHideSpellsOnCoolDown()) {
                if (spell.isCanSpellBeCasted()) {
                    System.out.print(ConsoleColor.CYAN + "\t" + spellIndex + ". " + ConsoleColor.RESET);
                    PrintUtil.printSpellDescription(hero, spell);
                    System.out.println();
                }
            } else {
                // Print all spells
                System.out.print(ConsoleColor.CYAN + "\t" + spellIndex + ". " + ConsoleColor.RESET);
                PrintUtil.printSpellDescription(hero, spell);
                System.out.println();
            }

            spellIndex++;
        }

        // Print option for potions menu
        PrintUtil.printIndexAndText(String.valueOf(spellIndex), "Potions Menu");
        System.out.println();
    }

    /**
     * Determines the spell the enemy character will cast during battle based on certain criteria.
     * Evaluates factors such as potential damage, healing ability, and utility of each spell.
     * The spell with the highest priority points is selected for casting.
     *
     * @param spellCaster The enemy character casting the spell.
     * @param spellTarget The target of the spell (usually the player's character).
     */
    private void characterUseSpell(GameCharacter spellCaster, GameCharacter spellTarget, List<GameCharacter> enemyList, List<GameCharacter> alliesList, List<GameCharacter> tempCharacterList) {
        Map<Spell, Integer> spells = new HashMap<>();

        Spell spellToCast = spellCaster.getCharacterSpellList().getFirst();

        int priorityPoints;

        // Evaluate each spell based on various criteria and assign priority points
        if (spellCaster.getCharacterSpellList().size() == 1) {
            spellToCast = spellCaster.getCharacterSpellList().getFirst();
        } else {
            for (Spell spell : spellCaster.getCharacterSpellList()) {
                spells.put(spell, 0);
            }

            spells.put(returnSpellWithTheHighestTotalDamage(spellCaster), 2);

            for (Map.Entry<Spell, Integer> spellIntegerEntry : spells.entrySet()) {
                if (spellIntegerEntry.getKey().isCanSpellBeCasted()) {
                    priorityPoints = 0;
                    for (Action action : spellIntegerEntry.getKey().getSpellActions()) {

                        if (action instanceof ActionIncreaseAbilityPoint) {
                            priorityPoints += 2;
                        }

                        if (action instanceof ActionReflectSpell) {
                            priorityPoints += 3;
                        }

                        if (action instanceof ActionInvulnerability && spellTarget.isCanPerformAction()) {
                            priorityPoints += 5;
                        }

                        if (spellCaster.getCurrentAbilityValue(Ability.MANA) < (spellCaster.getMaxAbilities().get(Ability.MANA) * 0.3)
                                && action instanceof ActionRestoreMana) {
                            priorityPoints += 3;
                        }

                        if (spellCaster.getBattleActionsWithDuration() != null) {
                            for (ActionWithDuration actionWithDuration : spellCaster.getBattleActionsWithDuration()) {
                                if (actionWithDuration.getActionStatusEffect().equals(ActionStatusEffect.DEBUFF)) {
                                    priorityPoints += 2;
                                }
                            }
                        }

                        if (action instanceof ActionRestoreHealth || action instanceof ActionRestoreHealthOverTime || action instanceof ActionAbsorbDamage) {
                            if (spellCaster.getCurrentAbilityValue(Ability.HEALTH) < spellCaster.getMaxAbilities().get(Ability.HEALTH) / 2) {
                                priorityPoints += 2;
                            } else if (spellCaster.getCurrentAbilityValue(Ability.HEALTH) < spellCaster.getMaxAbilities().get(Ability.HEALTH) / 3) {
                                priorityPoints += 4;
                            }
                        }

                        if (action instanceof ActionStun) {
                            priorityPoints += 3;
                        }
                    }

                    // Determine the spell with the highest priority points
                    spellIntegerEntry.setValue(spellIntegerEntry.getValue() + priorityPoints);
                    if (spellIntegerEntry.getValue() > spells.get(spellToCast)) {
                        spellToCast = spellIntegerEntry.getKey();
                    }
                }
            }
        }

        spellToCast.useSpell(spellCaster, spellTarget, enemyList, alliesList, tempCharacterList);
    }

    /**
     * Returns the spell in the character's spell list with the highest potential total damage output.
     *
     * @param gameCharacter The character whose spell list is considered.
     * @return The spell with the highest potential total damage.
     */
    private Spell returnSpellWithTheHighestTotalDamage(GameCharacter gameCharacter) {
        // The spell with the maximum total damage, initialized to the first spell in the character's spell list
        Spell spellWithMaxDamage = gameCharacter.getCharacterSpellList().getFirst();

        int totalDamage = 0;
        int maxTotalDamage = 0;

        for (Spell spell : gameCharacter.getCharacterSpellList()) {
            if (spell.isCanSpellBeCasted()) {

                // Calculate the total damage of the spell, including any damage over time effects
                for (Action action : spell.getSpellActions()) {
                    if (action instanceof ActionDealDamage) {
                        totalDamage += action.totalActionValue(spell.getBonusValueFromAbility(), gameCharacter);
                    }

                    if (action instanceof ActionDealDamageOverTime) {
                        int damageWithStacks = action.totalActionValue(spell.getBonusValueFromAbility(), gameCharacter)
                                * ((ActionDealDamageOverTime) action).getActionMaxStacks();
                        totalDamage += damageWithStacks;
                    }
                }

                // Update the spell with the highest total damage if necessary
                if (totalDamage > maxTotalDamage) {
                    maxTotalDamage = totalDamage;
                    spellWithMaxDamage = spell;
                }
            }
        }

        return spellWithMaxDamage;
    }

    private void checkSpellsCoolDowns(GameCharacter gameCharacter) {
        for (Spell spell : gameCharacter.getCharacterSpellList()) {
            spell.increaseTurnCoolDown();
        }
    }

    private void resetSpellsCoolDowns(Hero hero) {
        for (Spell spell : hero.getCharacterSpellList()) {
            spell.setCurrentTurnCoolDown(spell.getTurnCoolDown() + 1);
            spell.checkTurnCoolDown();
        }
    }
}
