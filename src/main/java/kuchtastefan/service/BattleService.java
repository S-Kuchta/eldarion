package kuchtastefan.service;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.actionsWIthDuration.ActionDurationType;
import kuchtastefan.characters.GameCharacter;
import kuchtastefan.characters.enemy.Enemy;
import kuchtastefan.characters.hero.Hero;
import kuchtastefan.characters.hero.inventory.InventoryService;
import kuchtastefan.constant.Constant;
import kuchtastefan.spell.Spell;
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
                while (true) {
                    printBattleMenu(hero, enemyChosen, selectedHeroForShowSelected, enemyList);

                    String choice = InputUtil.stringScanner().toUpperCase();
                    if (choice.matches("\\d+")) {
                        try {
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
                            System.out.println("\tEnter valid input");
                        }
                    } else {
                        try {
                            selectedHeroForShowSelected = choice;
                            enemyChosen = enemyList.get(LetterToNumber.valueOf(choice).getValue());
                        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                            selectedHeroForShowSelected = "A";
                            enemyChosen = enemyList.getFirst();
                            System.out.println("\tEnter valid input");
                        }
                    }
                }

                System.out.println("\n\t" + hero.getName() + " suffered from actions over time");
                hero.updateCurrentAbilitiesDependsOnActiveActionsAndIncreaseTurn(ActionDurationType.BATTLE_ACTION);
                hero.restoreAbility(hero.getCurrentAbilityValue(Ability.INTELLECT)
                        * Constant.RESTORE_MANA_PER_ONE_INTELLECT, Ability.MANA);

                heroPlay = false;
            } else {
                Iterator<Enemy> iterator = enemyList.iterator();
                while (iterator.hasNext()) {

                    Enemy enemyInCombat = iterator.next();
                    if (enemyInCombat.getCurrentAbilityValue(Ability.HEALTH) > 0) {
                        try {
                            Thread.sleep(800);
                        } catch (InterruptedException e) {
                            System.out.println(e.getMessage());
                        }

                        PrintUtil.printLongDivider();
                        System.out.println("\t\t" + enemyInCombat.getName() + " is Attacking!");

                        enemyUseSpell(enemyInCombat, hero);
                        checkSpellsCoolDowns(enemyInCombat);

                        System.out.println("\n\t" + enemyInCombat.getName() + " suffered from actions over time");
                        enemyInCombat.updateCurrentAbilitiesDependsOnActiveActionsAndIncreaseTurn(ActionDurationType.BATTLE_ACTION);
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
                hero.checkHeroGoldsAndSubtractIfTrue(80 * hero.getLevel());
                hero.getCurrentAbilities().put(Ability.HEALTH, hero.getMaxAbilities().get(Ability.HEALTH));
                this.resetSpellsCoolDowns(hero);
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
        for (Enemy enemyFromList : enemyList) {
            if (!enemyFromList.isDefeated()) {
                System.out.print("\t" + LetterToNumber.getStringFromValue(index)
                        + ". " + enemyFromList.getName() + " - " + enemyFromList.getEnemyRarity() + " - "
                        + " Healths: "
                        + enemyFromList.getCurrentAbilityValue(Ability.HEALTH));

                if (Objects.equals(LetterToNumber.getStringFromValue(index), selectedHeroForShowSelected)) {
                    System.out.print(" - SELECTED - ");
                }
                index++;
            }
        }

        int spellIndex = 0;
        System.out.println();
        for (Spell spell : hero.getCharacterSpellList()) {
            System.out.print("\t" + spellIndex + ". ");
            spell.printSpellDescription(hero);
//            System.out.println("\t" + spellIndex + ". " + spell.getSpellName() + ", "
//                    + spell.getSpellDescription() + " "
//                    + PrintUtil.printActionTurnCoolDown(spell.getCurrentTurnCoolDown(), spell.getTurnCoolDown()));

            spellIndex++;
            System.out.println();
        }
        System.out.println("\t" + spellIndex + ". Potions");
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
