package kuchtastefan.service;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.Action;
import kuchtastefan.actions.actionsWIthDuration.*;
import kuchtastefan.actions.instantActions.ActionDealDamage;
import kuchtastefan.actions.instantActions.ActionInstantStun;
import kuchtastefan.actions.instantActions.ActionRestoreHealth;
import kuchtastefan.actions.instantActions.ActionRestoreMana;
import kuchtastefan.character.GameCharacter;
import kuchtastefan.character.enemy.Enemy;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.hero.inventory.InventoryMenuService;
import kuchtastefan.character.npc.NonPlayerCharacter;
import kuchtastefan.character.spell.Spell;
import kuchtastefan.constant.Constant;
import kuchtastefan.constant.ConstantSymbol;
import kuchtastefan.gameSettings.GameSetting;
import kuchtastefan.gameSettings.GameSettingsService;
import kuchtastefan.hint.HintName;
import kuchtastefan.hint.HintUtil;
import kuchtastefan.utility.*;

import java.util.*;

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
        this.heroPlay = true;
    }


    public boolean battle(Hero hero, List<Enemy> enemies) {
        HintUtil.printHint(HintName.BATTLE_HINT);

        // Initialize lists for battle
        enemyList.addAll(enemies);
        alliesList.add(hero);

        // Initialize variables for selected hero and enemy
        playerTarget = enemyList.getFirst();

        hero.getBattleActionsWithDuration().clear();
        hero.getBattleActionsWithDuration().addAll(hero.getRegionActionsWithDuration());

        // Main battle loop
        while (true) {
            battleTurnMechanic(alliesList, enemyList, hero);
            battleTurnMechanic(enemyList, alliesList, hero);

            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
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

    private void battleTurnMechanic(List<GameCharacter> attackingCharacters, List<GameCharacter> defendingCharacters, Hero hero) {
        GameCharacter target = defendingCharacters.getFirst();

        Iterator<GameCharacter> iterator = attackingCharacters.iterator();
        while (iterator.hasNext()) {
            if (!defendingCharacters.isEmpty()) {
                target = defendingCharacters.getFirst();
            }

            GameCharacter attackingCharacter = iterator.next();

            // If character is alive
            if (attackingCharacter.getCurrentAbilityValue(Ability.HEALTH) > 0) {

                attackingCharacter.checkAndRemoveActionTurns();
                checkSpellsCoolDowns(attackingCharacter);
                if (attackingCharacter instanceof Hero) {
                    heroPlay = true;
                } else {
                    npcPrintHeader(attackingCharacter);
                }

                // If character can't perform action, skip to next character
                if (!attackingCharacter.isCanPerformAction()) {
                    this.printAndPerformActionOverTime(attackingCharacter);
                    if (heroPlay) {
                        heroPlay = false;
                    }
                    continue;
                }

                // If character can perform action
                if (heroPlay) {
                    playerTurn(hero);
                    target = playerTarget;
                } else {
                    // Select random character for npc attack
                    if (defendingCharacters.size() > 1) {
                        target = defendingCharacters.get(RandomNumberGenerator.getRandomNumber(0, defendingCharacters.size() - 1));
                    }

                    this.npcUseSpell(attackingCharacter, target, hero);
                }

                this.printAndPerformActionOverTime(attackingCharacter);
            }

            if (checkIfCharacterDied(target)) {
                defendingCharacters.remove(target);
                setTarget();
            }

            if (checkIfCharacterDied(attackingCharacter)) {
                iterator.remove();
                setTarget();
            }

            heroPlay = false;
        }

        attackingCharacters.addAll(tempCharacterList);
        tempCharacterList.clear();
    }

    private boolean checkIfCharacterDied(GameCharacter characterToCheck) {
        if (characterToCheck.getCurrentAbilityValue(Ability.HEALTH) <= 0) {
            System.out.println();
            System.out.println("\t" + ConsoleColor.RED + characterToCheck.getName() + " died!" + ConsoleColor.RESET);
            return true;
        } else {
            return false;
        }
    }

    private void setTarget() {
        if (!enemyList.isEmpty()) {
            playerTarget = enemyList.getFirst();
            selectedEnemyForShowSelection = "A";
        }
    }

    private void playerTurn(Hero hero) {
        InventoryMenuService inventoryMenuService = new InventoryMenuService();

        // Loop for hero's actions
        while (true) {
            printBattleMenu(hero);

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
                        if (hero.getCharacterSpellList().get(parsedChoice).useSpell(hero, playerTarget, enemyList, hero, tempCharacterList)) {
                            checkSpellsCoolDowns(hero);
                            break;
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    PrintUtil.printEnterValidInput();
                }
            } else {
                // Handle special commands
                if (choice.equals("X")) {
                    GameSettingsService.setTrueOrFalse(GameSetting.SHOW_INFORMATION_ABOUT_ACTION_NAME);
                } else if (choice.equals("Y")) {
                    GameSettingsService.setTrueOrFalse(GameSetting.HIDE_SPELLS_ON_COOL_DOWN);
                } else {
                    try {
                        selectedEnemyForShowSelection = choice;
                        playerTarget = enemyList.get(LetterToNumber.valueOf(choice).getValue() - 1);
                    } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                        selectedEnemyForShowSelection = "A";
                        playerTarget = enemyList.getFirst();
                        PrintUtil.printEnterValidInput();
                    }
                }
            }
        }
    }

    private void npcPrintHeader(GameCharacter gameCharacter) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        PrintUtil.printLongDivider();
        System.out.println("\t\t" + ConstantSymbol.SWORD_SYMBOL
                + " " + gameCharacter.getName() + " Turn!"
                + ConstantSymbol.SWORD_SYMBOL);
        System.out.println();
    }

    private void printAndPerformActionOverTime(GameCharacter gameCharacter) {
        System.out.println("\n\t_____ " + gameCharacter.getName() + " buffs and debuffs _____");
        gameCharacter.performActionsWithDuration(ActionDurationType.BATTLE_ACTION);
        gameCharacter.restoreAbility(gameCharacter.getCurrentAbilityValue(Ability.INTELLECT)
                * Constant.RESTORE_MANA_PER_ONE_INTELLECT, Ability.MANA);
    }

    private void printBattleMenu(Hero hero) {
        // Print hero's header with stats and buffs
        PrintUtil.printHeaderWithStatsBar(hero);
        PrintUtil.printBattleBuffs(hero);

        // Print alies header with stats and buffs
        for (GameCharacter gameCharacter : alliesList) {
            if (!(gameCharacter instanceof Hero)) {
                PrintUtil.printHeaderWithStatsBar(gameCharacter);
                PrintUtil.printBattleBuffs(gameCharacter);
            }
        }

        // Print enemy's header with stats and buffs
        PrintUtil.printExtraLongDivider();
        System.out.printf("%58s %n", "Enemy");
        PrintUtil.printHeaderWithStatsBar(playerTarget);
        PrintUtil.printBattleBuffs(playerTarget);
        PrintUtil.printExtraLongDivider();

        int index = 1;
        System.out.print("\t");
        // Print available enemies for selection
        for (GameCharacter enemyFromList : enemyList) {
            if (!((NonPlayerCharacter) enemyFromList).isDefeated()) {
                System.out.print(ConsoleColor.CYAN + LetterToNumber.getStringFromValue(index) + ConsoleColor.RESET
                        + ". " + enemyFromList.getName() + " - " + ((NonPlayerCharacter) enemyFromList).getCharacterRarity() + " - "
                        + " Healths: "
                        + enemyFromList.getCurrentAbilityValue(Ability.HEALTH) + " ");

                // Highlight selected enemy
                if (Objects.equals(LetterToNumber.getStringFromValue(index), selectedEnemyForShowSelection)) {
                    System.out.print(ConstantSymbol.SWORD_SYMBOL + " ");
                }
                index++;
            }
        }

        PrintUtil.printSpellGameSettings();

        // Print hero's spells with descriptions
        int spellIndex = 0;
        System.out.println();
        for (Spell spell : hero.getCharacterSpellList()) {

            // Check if spells should be hidden when on CoolDown
            if (GameSettingsService.gameSettings.get(GameSetting.HIDE_SPELLS_ON_COOL_DOWN)) {
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
    private void npcUseSpell(GameCharacter spellCaster, GameCharacter spellTarget, Hero hero) {
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

                        if (action instanceof ActionSummonCreature) {
                            priorityPoints += 4;
                        }

                        if (action instanceof ActionStun || action instanceof ActionInstantStun) {
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

        spellToCast.useSpell(spellCaster, spellTarget, enemyList, hero, tempCharacterList);
    }

    /**
     * Returns the spell in the character's spell list with the highest potential total damage output.
     *
     * @param gameCharacter The character whose spell list is considered.
     * @return The spell with the highest potential total damage.
     */
    private Spell returnSpellWithTheHighestTotalDamage(GameCharacter gameCharacter) {

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
            spell.increaseSpellCoolDown();
        }
    }

    private void resetSpellsCoolDowns(Hero hero) {
        for (Spell spell : hero.getCharacterSpellList()) {
            spell.setCurrentTurnCoolDown(spell.getTurnCoolDown() + 1);
            spell.checkSpellCoolDown();
        }
    }
}
