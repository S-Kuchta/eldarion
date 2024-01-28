package kuchtastefan.service;

import kuchtastefan.ability.Ability;
import kuchtastefan.actions.actionsWIthDuration.ActionDurationType;
import kuchtastefan.characters.GameCharacter;
import kuchtastefan.characters.enemy.Enemy;
import kuchtastefan.characters.hero.Hero;
import kuchtastefan.spell.Spell;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

public class BattleService {

    public boolean battle(Hero hero, Enemy enemy) {

        boolean heroPlay = true;
        hero.getBattleActionsWithDuration().clear();
        hero.getBattleActionsWithDuration().addAll(hero.getRegionActionsWithDuration());

        while (true) {
            if (heroPlay) {
                PrintUtil.printHeaderWithStatsBar(hero);
                PrintUtil.printBattleBuffs(hero);

                PrintUtil.printHeaderWithStatsBar(enemy);
                PrintUtil.printBattleBuffs(enemy);
                PrintUtil.printExtraLongDivider();

                heroUseSpell(hero, enemy);
                checkSpellsCoolDowns(hero);

                System.out.println("\n\t" + hero.getName() + " suffered from actions over time");
                hero.updateCurrentAbilitiesDependsOnActiveActionsAndIncreaseTurn(ActionDurationType.BATTLE_ACTION);
                heroPlay = false;
            } else {
                PrintUtil.printLongDivider();
                System.out.println("\t" + enemy.getName() + " TURN!");

                enemyUseSpell(enemy, hero);
                checkSpellsCoolDowns(enemy);

                System.out.println("\n\t" + enemy.getName() + " suffered from actions over time");
                enemy.updateCurrentAbilitiesDependsOnActiveActionsAndIncreaseTurn(ActionDurationType.BATTLE_ACTION);
                heroPlay = true;
            }

            if (enemy.getCurrentAbilityValue(Ability.HEALTH) <= 0) {
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
