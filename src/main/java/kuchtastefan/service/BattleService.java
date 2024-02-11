package kuchtastefan.service;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.Action;
import kuchtastefan.actions.actionsWIthDuration.*;
import kuchtastefan.actions.instantActions.ActionDealDamage;
import kuchtastefan.actions.instantActions.ActionRestoreHealth;
import kuchtastefan.characters.GameCharacter;
import kuchtastefan.characters.enemy.Enemy;
import kuchtastefan.characters.hero.Hero;
import kuchtastefan.characters.hero.inventory.InventoryService;
import kuchtastefan.characters.spell.Spell;
import kuchtastefan.constant.Constant;
import kuchtastefan.gameSettings.GameSettings;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.LetterToNumber;
import kuchtastefan.utility.PrintUtil;

import java.util.*;

public class BattleService {

    public boolean battle(Hero hero, List<Enemy> enemies) {
        InventoryService inventoryService = new InventoryService();
        List<Enemy> enemyList = new ArrayList<>(enemies);

        String selectedHeroForShowSelected = "A";
        Enemy enemyChosen = enemyList.getFirst();

        boolean heroPlay = true;
        for (Enemy enemy : enemyList) {
            if (enemy.getCurrentAbilityValue(Ability.HASTE) > hero.getCurrentAbilityValue(Ability.HASTE)) {
                heroPlay = false;
                break;
            }
        }

        hero.getBattleActionsWithDuration().clear();
        hero.getBattleActionsWithDuration().addAll(hero.getRegionActionsWithDuration());

        while (true) {
            if (heroPlay) {
                hero.checkAndRemoveActionTurns();

                if (!hero.isCanPerformAction()) {
                    heroPlay = false;
                    this.printAndPerformActionOverTime(hero);
                    checkSpellsCoolDowns(hero);
                    continue;
                }

                while (true) {
                    printBattleMenu(hero, enemyChosen, selectedHeroForShowSelected, enemyList);

                    String choice = InputUtil.stringScanner().toUpperCase();
                    if (choice.matches("\\d+")) {
                        try {
                            PrintUtil.printExtraLongDivider();
                            int parsedChoice = Integer.parseInt(choice);
                            if (parsedChoice == hero.getCharacterSpellList().size()) {
                                if (inventoryService.consumableItemsMenu(hero, true)) {
                                    checkSpellsCoolDowns(hero);
                                    break;
                                }
                            } else {
                                if (hero.getCharacterSpellList().get(parsedChoice).useSpell(hero, enemyChosen)) {
                                    checkSpellsCoolDowns(hero);
                                    break;
                                }
                            }
                        } catch (IndexOutOfBoundsException e) {
                            PrintUtil.printEnterValidInput();
                        }
                    } else {
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

                this.printAndPerformActionOverTime(hero);

                heroPlay = false;
            } else {
                Iterator<Enemy> iterator = enemyList.iterator();
                while (iterator.hasNext()) {
                    Enemy enemyInCombat = iterator.next();

                    if (enemyInCombat.getCurrentAbilityValue(Ability.HEALTH) > 0) {

                        enemyInCombat.checkAndRemoveActionTurns();
                        checkSpellsCoolDowns(enemyInCombat);

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            System.out.println(e.getMessage());
                        }

                        PrintUtil.printLongDivider();
                        System.out.println("\t\t" + ConsoleColor.RED_BOLD + "—⟪=====> " + ConsoleColor.RESET
                                + enemyInCombat.getName() + " Turn!"
                                + ConsoleColor.RED_BOLD + " ⚔" + ConsoleColor.RESET);
                        System.out.println();

                        if (!enemyInCombat.isCanPerformAction()) {
                            this.printAndPerformActionOverTime(enemyInCombat);
                            continue;
                        }

                        this.enemyUseSpell(enemyInCombat, hero);
                        this.printAndPerformActionOverTime(enemyInCombat);
                    }

                    if (enemyInCombat.getCurrentAbilityValue(Ability.HEALTH) <= 0) {
                        PrintUtil.printDivider();
                        System.out.println(ConsoleColor.YELLOW_BOLD + "\t\tYou killed " + enemyInCombat.getName() + ConsoleColor.RESET);
                        PrintUtil.printDivider();

                        iterator.remove();
                        if (!enemyList.isEmpty()) {
                            enemyChosen = enemyList.getFirst();
                            selectedHeroForShowSelected = "A";
                        }
                    }
                }

                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                heroPlay = true;
            }

            if (enemyList.isEmpty()) {
                hero.getBattleActionsWithDuration().clear();
                this.resetSpellsCoolDowns(hero);
                return true;
            }

            if (hero.getCurrentAbilityValue(Ability.HEALTH) <= 0) {
                hero.getRegionActionsWithDuration().clear();
                hero.getBattleActionsWithDuration().clear();
                int goldToRemove = Constant.GOLD_TO_REMOVE_PER_LEVEL_AFTER_DEAD * hero.getLevel();

                hero.checkHeroGoldsAndSubtractIfTrue(goldToRemove);
                hero.getCurrentAbilities().put(Ability.HEALTH, hero.getMaxAbilities().get(Ability.HEALTH));
                this.resetSpellsCoolDowns(hero);

                PrintUtil.printDivider();
                System.out.println("\tYou lost " + goldToRemove + " golds!");
                System.out.println("\t" + ConsoleColor.RED + "You have died!" + ConsoleColor.RESET);
                PrintUtil.printDivider();
                return false;
            }
        }
    }

    private void printAndPerformActionOverTime(GameCharacter gameCharacter) {
        System.out.println("\n\t" + gameCharacter.getName() + " actions over time");
        gameCharacter.updateCurrentCharacterStateDependsOnActiveActionsAndIncreaseTurn(ActionDurationType.BATTLE_ACTION);
        gameCharacter.restoreAbility(gameCharacter.getCurrentAbilityValue(Ability.INTELLECT)
                * Constant.RESTORE_MANA_PER_ONE_INTELLECT, Ability.MANA);
    }

    private void printBattleMenu(Hero hero, Enemy enemyChosen, String selectedHeroForShowSelected, List<Enemy> enemyList) {
        PrintUtil.printHeaderWithStatsBar(hero);
        PrintUtil.printBattleBuffs(hero);
        PrintUtil.printHeaderWithStatsBar(enemyChosen);
        PrintUtil.printBattleBuffs(enemyChosen);
        PrintUtil.printExtraLongDivider();

        int index = 1;
        System.out.print("\t");
        for (Enemy enemyFromList : enemyList) {
            if (!enemyFromList.isDefeated()) {
                System.out.print(ConsoleColor.CYAN + LetterToNumber.getStringFromValue(index) + ConsoleColor.RESET
                        + ". " + enemyFromList.getName() + " - " + enemyFromList.getEnemyRarity() + " - "
                        + " Healths: "
                        + enemyFromList.getCurrentAbilityValue(Ability.HEALTH) + " ");

                if (Objects.equals(LetterToNumber.getStringFromValue(index), selectedHeroForShowSelected)) {
                    System.out.print(ConsoleColor.RED_BOLD + "⚔ " + ConsoleColor.RESET);
                }
                index++;
            }
        }

        System.out.println();
        PrintUtil.printIndexAndText("X", "Show/Hide action description");

        System.out.print("\t");
        PrintUtil.printIndexAndText("Y", "Show/Hide spells on CoolDown");
        PrintUtil.printGameSettings(GameSettings.isHideSpellsOnCoolDown());
        System.out.print(" spells on CoolDown");


        int spellIndex = 0;
        System.out.println();
        for (Spell spell : hero.getCharacterSpellList()) {
            if (GameSettings.isHideSpellsOnCoolDown()) {
                if (spell.isCanSpellBeCasted()) {
                    System.out.print(ConsoleColor.CYAN + "\t" + spellIndex + ". " + ConsoleColor.RESET);
                    PrintUtil.printSpellDescription(hero, spell);
                    System.out.println();
                }
            } else {
                System.out.print(ConsoleColor.CYAN + "\t" + spellIndex + ". " + ConsoleColor.RESET);
                PrintUtil.printSpellDescription(hero, spell);
                System.out.println();
            }

            spellIndex++;

        }
        PrintUtil.printIndexAndText(String.valueOf(spellIndex), "Potions Menu");
        System.out.println();
    }

    private void enemyUseSpell(GameCharacter spellCaster, GameCharacter spellTarget) {
        Map<Spell, Integer> spells = new HashMap<>();

        Spell spellToCast = spellCaster.getCharacterSpellList().getFirst();
        int priorityPoints;

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

                        if (action instanceof ActionInvulnerability && spellTarget.isCanPerformAction()) {
                            priorityPoints += 5;
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

                    spellIntegerEntry.setValue(spellIntegerEntry.getValue() + priorityPoints);
                    if (spellIntegerEntry.getValue() > spells.get(spellToCast)) {
                        spellToCast = spellIntegerEntry.getKey();
                    }
                }
            }
        }

        spellToCast.useSpell(spellCaster, spellTarget);
    }

    private Spell returnSpellWithTheHighestTotalDamage(GameCharacter gameCharacter) {
        Spell spellWithMaxDamage = gameCharacter.getCharacterSpellList().getFirst();
        int totalDamage = 0;
        int maxTotalDamage = 0;

        for (Spell spell : gameCharacter.getCharacterSpellList()) {
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

            if (totalDamage > maxTotalDamage) {
                maxTotalDamage = totalDamage;
                spellWithMaxDamage = spell;
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
