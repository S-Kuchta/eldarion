package kuchtastefan.service;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.actionsWIthDuration.ActionDurationType;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

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
                hero.checkActionTurns();

                if (!hero.isCanPerformAction()) {
                    heroPlay = false;
                    hero.updateCurrentCharacterStateDependsOnActiveActionsAndIncreaseTurn(ActionDurationType.BATTLE_ACTION);
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
                            GameSettings.setShowSpellsOnCoolDown();
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

                System.out.println("\n\t" + hero.getName() + " suffered from actions over time");
                hero.updateCurrentCharacterStateDependsOnActiveActionsAndIncreaseTurn(ActionDurationType.BATTLE_ACTION);

                hero.restoreAbility(hero.getCurrentAbilityValue(Ability.INTELLECT)
                        * Constant.RESTORE_MANA_PER_ONE_INTELLECT, Ability.MANA);

                heroPlay = false;
            } else {
                Iterator<Enemy> iterator = enemyList.iterator();
                while (iterator.hasNext()) {
                    Enemy enemyInCombat = iterator.next();

                    if (enemyInCombat.getCurrentAbilityValue(Ability.HEALTH) > 0) {

                        enemyInCombat.checkActionTurns();
                        checkSpellsCoolDowns(enemyInCombat);

                        if (!enemyInCombat.isCanPerformAction()) {
                            enemyInCombat.updateCurrentCharacterStateDependsOnActiveActionsAndIncreaseTurn(ActionDurationType.BATTLE_ACTION);
                            continue;
                        }

                        try {
                            Thread.sleep(800);
                        } catch (InterruptedException e) {
                            System.out.println(e.getMessage());
                        }

                        PrintUtil.printLongDivider();
                        System.out.println("\t\t" + ConsoleColor.RED_BOLD + "—⟪=====> " + ConsoleColor.RESET
                                + enemyInCombat.getName() + " is Attacking!"
                                + ConsoleColor.RED_BOLD + " ⚔" + ConsoleColor.RESET);
                        System.out.println();

                        enemyUseSpell(enemyInCombat, hero);

                        System.out.println("\n\t" + enemyInCombat.getName() + " suffered from actions over time");
                        enemyInCombat.updateCurrentCharacterStateDependsOnActiveActionsAndIncreaseTurn(ActionDurationType.BATTLE_ACTION);
                        enemyInCombat.restoreAbility(hero.getCurrentAbilityValue(Ability.INTELLECT)
                                * Constant.RESTORE_MANA_PER_ONE_INTELLECT, Ability.MANA);
                    }

                    if (enemyInCombat.getCurrentAbilityValue(Ability.HEALTH) <= 0) {
                        PrintUtil.printDivider();
                        System.out.println("\t\tYou killed " + enemyInCombat.getName());
                        PrintUtil.printDivider();

                        iterator.remove();
                        if (!enemyList.isEmpty()) {
                            enemyChosen = enemyList.getFirst();
                            selectedHeroForShowSelected = "A";
                        }
                    }
                }

                try {
                    Thread.sleep(2000);
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
//                String text = enemyFromList.getName() + " - " + enemyFromList.getEnemyRarity() + " - " + " Healths: "
//                        + enemyFromList.getCurrentAbilityValue(Ability.HEALTH);
//                PrintUtil.printIndexAndText(LetterToNumber.getStringFromValue(index), text);

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

        int spellIndex = 0;
        System.out.println();
        for (Spell spell : hero.getCharacterSpellList()) {
            if (GameSettings.isShowSpellsOnCoolDown()) {
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
//        System.out.println("\t" + ConsoleColor.CYAN + spellIndex + ". " + ConsoleColor.RESET + "Potions Menu");
    }

    private void enemyUseSpell(Enemy enemy, Hero hero) {
        enemy.getCharacterSpellList().get(0).useSpell(enemy, hero);
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
