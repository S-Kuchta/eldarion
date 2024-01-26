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


        while (true) {
            int heroHealth = hero.getCurrentAbilityValue(Ability.HEALTH);
            int enemyHealth = enemy.getCurrentAbilityValue(Ability.HEALTH);


            if (heroPlay) {
                PrintUtil.printHeaderWithStatsBar(hero);
                PrintUtil.printBattleBuffs(hero);

                PrintUtil.printHeaderWithStatsBar(enemy);
                PrintUtil.printBattleBuffs(enemy);

                heroUseSpell(hero, enemy);
                checkSpellsCoolDowns(hero);
                System.out.println("\tYou get from actions over time");
                hero.updateCurrentAbilitiesDependsOnActiveActions(ActionDurationType.BATTLE_ACTION);
                heroPlay = false;
            } else {
                PrintUtil.printLongDivider();
                enemyUseSpell(enemy, hero);
                checkSpellsCoolDowns(enemy);
                System.out.println("\tYou get from actions over time");
                enemy.updateCurrentAbilitiesDependsOnActiveActions(ActionDurationType.BATTLE_ACTION);
                heroPlay = true;
            }

            if (enemy.getCurrentAbilityValue(Ability.HEALTH) <= 0) {
                hero.getBattleActionsWithDuration().clear();
                return true;
            }

            if (hero.getCurrentAbilityValue(Ability.HEALTH) <= 0) {
                hero.getRegionActionsWithDuration().clear();
                hero.getBattleActionsWithDuration().clear();
                hero.checkHeroGoldsAndSubtractIfTrue(80 * hero.getLevel());
                hero.getCurrentAbilities().put(Ability.HEALTH, hero.getMaxAbilities().get(Ability.HEALTH));
                return false;
            }

        }
    }

    private void heroUseSpell(Hero hero, Enemy enemy) {
        int index = 0;
        for (Spell spell : hero.getCharacterSpellList()) {
            System.out.println("\t" + index + ". " + spell.getSpellName() + ", " + spell.getSpellDescription());
            index++;
        }

        final int choice = InputUtil.intScanner();
        if (choice == 1) {
            return;
        }
        hero.getCharacterSpellList().get(choice).useSpell(hero, enemy);
    }

    private void enemyUseSpell(Enemy enemy, Hero hero) {
        enemy.getCharacterSpellList().get(0).useSpell(enemy, hero);
    }

    private void checkSpellsCoolDowns(GameCharacter gameCharacter) {
        for (Spell spell : gameCharacter.getCharacterSpellList()) {
            spell.increaseTurnCoolDown();
        }
    }

//    public boolean battle(Hero hero, Enemy enemy) {
//        boolean heroPlay = true;
//
//        while (true) {
//            int heroHealth = hero.getCurrentAbilityValue(Ability.HEALTH);
//            int enemyHealth = enemy.getCurrentAbilityValue(Ability.HEALTH);
//
//            System.out.println("Your healths: " + heroHealth);
//            System.out.println("Enemy healths: " + enemyHealth);
//
//            if (heroPlay) {
////                battleRound(hero, enemy);
//                Spell spell = SpellsList.getSpellList().get(0);
//
//                spell.useSpell(hero, enemy);
//                spell.increaseTurnCoolDown();
//                heroPlay = false;
//            } else {
////                battleRound(enemy, hero);
//                Spell spell = SpellsList.getSpellList().get(0);
//
//                spell.useSpell(enemy, hero);
//                spell.increaseTurnCoolDown();
//                heroPlay = true;
//            }
//
//            if (enemy.getCurrentAbilityValue(Ability.HEALTH) <= 0) {
//                hero.getBattleActionsWithDuration().clear();
//                return true;
//            }
//
//            if (hero.getCurrentAbilityValue(Ability.HEALTH) <= 0) {
//                hero.getBattleActionsWithDuration().clear();
//                return false;
//            }
//
//            try {
//                Thread.sleep(800);
//            } catch (InterruptedException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//
//
//    }
//
//    private void battleRound(GameCharacter attacker, GameCharacter defender) {
//        int damage = 0;
//        int finalDamage;
//
//        if (criticalHit(attacker)) {
//            System.out.println("Critical hit!");
//            damage += (attack(attacker) * 2);
//        } else {
//            damage = attack(attacker);
//        }
//
//        finalDamage = finalDamage(damage, defense(defender));
//
////        defender.receiveDamage(finalDamage);
////        new ActionDealDamage("Attack", finalDamage, ActionEffectOn.SPELL_TARGET).performAction(defender);
//
//
////        new ActionDealDamageOverTime("Attack over time", 2, 3, ActionDurationType.REGION_ACTION, 3).performAction(defender);
////        defender.updateCurrentAbilitiesDependsOnActiveActions(ActionDurationType.BATTLE_ACTION);
////        if (defender instanceof Enemy) {
////            defender.addActionWithDuration(new ActionDealDamageOverTime("Attack over time", 2, 3, ActionDurationType.BATTLE_ACTION, 3, ActionEffectOn.SPELL_TARGET));
////        }
//
//
////        System.out.println(attacker.getName() + " attacked " + defender.getName() + " for " + finalDamage + " damage!");
//        System.out.println(defender.getName() + " healths are: " + defender.getCurrentAbilityValue(Ability.HEALTH));
//        PrintUtil.printDivider();
//    }
//
//    private int finalDamage(int damage, int defence) {
//        int totalDamage = damage - defence;
//
//        return Math.max(totalDamage, 0);
//    }
//
//    private int attack(GameCharacter gameCharacter) {
//        int minDamage;
//        int maxDamage;
//        if (gameCharacter instanceof Hero) {
//            minDamage = gameCharacter.getCurrentAbilityValue(Ability.ATTACK) +
//                    ((Hero) gameCharacter).returnItemAbilityValue(Ability.ATTACK);
//            maxDamage = minDamage
//                    + gameCharacter.getCurrentAbilityValue(Ability.DEXTERITY)
//                    + ((Hero) gameCharacter).returnItemAbilityValue(Ability.DEXTERITY)
//                    + gameCharacter.getCurrentAbilityValue(Ability.SKILL)
//                    + ((Hero) gameCharacter).returnItemAbilityValue(Ability.SKILL);
//        } else {
//            minDamage = gameCharacter.getCurrentAbilityValue(Ability.ATTACK);
//            maxDamage = gameCharacter.getCurrentAbilityValue(Ability.ATTACK)
//                    + gameCharacter.getCurrentAbilityValue(Ability.DEXTERITY)
//                    + gameCharacter.getCurrentAbilityValue(Ability.SKILL);
//        }
//
//        return RandomNumberGenerator.getRandomNumber(minDamage, maxDamage);
////        return new ActionDealDamage("Attack", RandomNumberGenerator.getRandomNumber(minDamage, maxDamage)).performAction();
//    }
//
//    private int defense(GameCharacter gameCharacter) {
//        int minDefence;
//        int maxDefence;
//        if (gameCharacter instanceof Hero) {
//            minDefence = gameCharacter.getCurrentAbilityValue(Ability.DEFENCE)
//                    + ((Hero) gameCharacter).returnItemAbilityValue(Ability.DEFENCE);
//            maxDefence = minDefence
//                    + gameCharacter.getCurrentAbilityValue(Ability.DEXTERITY)
//                    + ((Hero) gameCharacter).returnItemAbilityValue(Ability.DEXTERITY);
//        } else {
//            minDefence = gameCharacter.getCurrentAbilityValue(Ability.DEFENCE);
//            maxDefence = minDefence + gameCharacter.getCurrentAbilityValue(Ability.DEXTERITY);
//        }
//
//        return RandomNumberGenerator.getRandomNumber(minDefence, maxDefence);
//    }
//
//    private boolean criticalHit(GameCharacter gameCharacter) {
//        int criticalHit;
//        if (gameCharacter instanceof Hero) {
//            criticalHit = gameCharacter.getCurrentAbilityValue(Ability.LUCK)
//                    + gameCharacter.getCurrentAbilityValue(Ability.SKILL)
//                    + ((Hero) gameCharacter).returnItemAbilityValue(Ability.SKILL)
//                    + ((Hero) gameCharacter).returnItemAbilityValue(Ability.LUCK);
//        } else {
//            criticalHit = gameCharacter.getCurrentAbilityValue(Ability.LUCK)
//                    + gameCharacter.getCurrentAbilityValue(Ability.SKILL);
//        }
//        return criticalHit >= RandomNumberGenerator.getRandomNumber(0, 100);
//    }
}
