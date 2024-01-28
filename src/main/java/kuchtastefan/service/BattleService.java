package kuchtastefan.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kuchtastefan.ability.Ability;
import kuchtastefan.actions.actionsWIthDuration.ActionDurationType;
import kuchtastefan.characters.GameCharacter;
import kuchtastefan.characters.enemy.Enemy;
import kuchtastefan.characters.hero.Hero;
import kuchtastefan.spell.Spell;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.LetterToNumber;
import kuchtastefan.utility.PrintUtil;
import kuchtastefan.utility.RuntimeTypeAdapterFactoryUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class BattleService {

    public boolean battle(Hero hero, Enemy enemy) {
        String selectedHero = "A";

        Gson gson = new GsonBuilder().registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.actionsRuntimeTypeAdapterFactory).create();

        Enemy newEnemy = gson.fromJson(gson.toJson(enemy), Enemy.class);

        List<Enemy> enemyList = new ArrayList<>();
        enemyList.add(enemy);
        enemyList.add(newEnemy);
        Enemy enemyChoice = null;

        boolean heroPlay = true;
        hero.getBattleActionsWithDuration().clear();
        hero.getBattleActionsWithDuration().addAll(hero.getRegionActionsWithDuration());

        while (true) {

            if (heroPlay) {
                while (true) {
                    if (enemyChoice == null) {
                        enemyChoice = enemyList.getFirst();
                    }

                    PrintUtil.printHeaderWithStatsBar(hero);
                    PrintUtil.printBattleBuffs(hero);
                    PrintUtil.printHeaderWithStatsBar(enemyChoice);
                    PrintUtil.printBattleBuffs(enemyChoice);
                    PrintUtil.printExtraLongDivider();

                    int index = 1;
                    for (Enemy enemyFromList : enemyList) {
                        if (!enemyFromList.isDefeated()) {
                            System.out.print("\t" + LetterToNumber.getStringFromValue(index)
                                    + ". " + enemyFromList.getName() + " - " + enemyFromList.getEnemyRarity() + " - "
                                    + " Healths: "
                                    + enemyFromList.getCurrentAbilityValue(Ability.HEALTH));

                            if (Objects.equals(LetterToNumber.getStringFromValue(index), selectedHero)) {
                                System.out.print(" - SELECTED - ");
                            }
                            index++;
                        }
                    }

                    int spellIndex = 0;
                    System.out.println();
                    for (Spell spell : hero.getCharacterSpellList()) {
                        System.out.println("\t" + spellIndex + ". " + spell.getSpellName() + ", "
                                + spell.getSpellDescription() + " "
                                + PrintUtil.printActionTurnCoolDown(spell.getCurrentTurnCoolDown(), spell.getTurnCoolDown()));

                        spellIndex++;
                    }

                    String choice = InputUtil.stringScanner().toUpperCase();
                    if (choice.matches("\\d+")) {
                        try {
                            int number = Integer.parseInt(choice);
                            if (hero.getCharacterSpellList().get(number).useSpell(hero, enemyChoice)) {
                                checkSpellsCoolDowns(hero);
                                break;
                            }
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("\tEnter valid input");
                            continue;
                        }
                    }

                    try {
//                        selectedHero = LetterToNumber.valueOf(choice).name();
                        enemyChoice = enemyList.get(LetterToNumber.valueOf(choice).ordinal());
                    } catch (IndexOutOfBoundsException e) {
                        selectedHero = "A";
                        enemyChoice = enemyList.getFirst();
                        System.out.println("\tEnter valid input");
                    } catch (IllegalArgumentException e) {
                        System.out.println("\tBad value");
                    }
                }

                System.out.println("\n\t" + hero.getName() + " suffered from actions over time");
                hero.updateCurrentAbilitiesDependsOnActiveActionsAndIncreaseTurn(ActionDurationType.BATTLE_ACTION);

                heroPlay = false;
            } else {
                Iterator<Enemy> iterator = enemyList.iterator();
                while (iterator.hasNext()) {

                    Enemy enemy1 = iterator.next();
                    if (enemy1.getCurrentAbilityValue(Ability.HEALTH) > 0) {
                        PrintUtil.printLongDivider();
                        System.out.println("\t" + enemy1.getName() + " TURN!");

                        enemyUseSpell(enemy1, hero);
                        checkSpellsCoolDowns(enemy1);

                        System.out.println("\n\t" + enemy1.getName() + " suffered from actions over time");
                        enemy1.updateCurrentAbilitiesDependsOnActiveActionsAndIncreaseTurn(ActionDurationType.BATTLE_ACTION);
                    }

                    if (enemy1.getCurrentAbilityValue(Ability.HEALTH) <= 0) {
                        System.out.println("\tYou killed " + enemy1.getName());
                        iterator.remove();
                        if (!enemyList.isEmpty()) {
                            enemyChoice = enemyList.getFirst();
                        }
                        selectedHero = "A";
                    }
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

    private void heroUseSpell(Hero hero, Enemy enemy) {
        int index = 0;
        for (Spell spell : hero.getCharacterSpellList()) {
            System.out.println("\t" + index + ". " + spell.getSpellName() + ", "
                    + spell.getSpellDescription() + " "
                    + PrintUtil.printActionTurnCoolDown(spell.getCurrentTurnCoolDown(), spell.getTurnCoolDown()));

            index++;
        }

        while (true) {
            try {
                final int choice = InputUtil.intScanner();
                if (hero.getCharacterSpellList().get(choice).useSpell(hero, enemy)) {
                    break;
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("\tEnter valid input");
            }
        }
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
