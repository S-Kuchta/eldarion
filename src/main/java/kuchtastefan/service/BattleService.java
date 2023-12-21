package kuchtastefan.service;

import kuchtastefan.ability.Ability;
import kuchtastefan.domain.Enemy;
import kuchtastefan.domain.GameCharacter;
import kuchtastefan.domain.Hero;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import kuchtastefan.utility.RandomNumberGenerator;

public class BattleService {
    public boolean isHeroReadyToBattle(Hero hero, Enemy enemy) {
        System.out.println(hero.getName() + " VS " + enemy.getName());
        System.out.println("View your abilities:");
        PrintUtil.printCurrentAbilityPoints(hero);

        System.out.println("View enemy abilities:");
        PrintUtil.printCurrentAbilityPoints(enemy);

        System.out.println("Are you ready to fight?");
        System.out.println("0. No");
        System.out.println("1. Yes");

        final int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
                System.out.println("You have escaped from battle");
                return false;
            }
            case 1 -> {
                System.out.println("Let the battle begin");
                return true;
            }
            default -> {
                System.out.println("Invalid choice");
                return false;
            }
        }
    }

    public boolean battle(Hero hero, Enemy enemy) {
        int heroHealth = hero.getAbilityValue(Ability.HEALTH);
        int enemyHealth = enemy.getAbilityValue(Ability.HEALTH);

        while (true) {
            enemyHealth = battleRound(hero, enemy, enemyHealth, heroHealth, enemyHealth);
            if (enemyHealth <= 0) {
                return true;
            }

            heroHealth = battleRound(enemy, hero, heroHealth, heroHealth, enemyHealth);
            if (heroHealth <= 0) {
                return false;
            }
        }
    }

    private int battleRound(GameCharacter attacker, GameCharacter defender, int health, int heroHealth, int enemyHealth) {
        int damage = 0;
        int damageAfterDefense;

        try {
            Thread.sleep(800);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Your health: " + heroHealth);
        System.out.println("Enemy health: " + enemyHealth);
        if (criticalHit(attacker)) {
            System.out.println("Critical hit!");
            damage += (attack(attacker) * 2);
        } else {
            damage = attack(attacker);
        }

        damageAfterDefense = damageCheck(damage, defense(defender));

        health -= damageAfterDefense;
        System.out.println(attacker.getName() + " attacked " + defender.getName() + " for " + damageAfterDefense);
        System.out.println(defender.getName() + " healths are: " + health);
        PrintUtil.printDivider();
        return health;
    }

    private int damageCheck(int damage, int defence) {
        int totalDamage = damage - defence;

        return Math.max(totalDamage, 0);

    }

    private int attack(GameCharacter gameCharacter) {
        int minDamage = gameCharacter.getAbilityValue(Ability.ATTACK);
        int maxDamage = gameCharacter.getAbilityValue(Ability.ATTACK) +
                gameCharacter.getAbilityValue(Ability.DEXTERITY) +
                gameCharacter.getAbilityValue(Ability.SKILL);

        return RandomNumberGenerator.getRandomNumber(minDamage, maxDamage);
    }

    private int defense(GameCharacter gameCharacter) {
        int minDefence = gameCharacter.getAbilityValue(Ability.DEFENCE);
        int maxDefence = minDefence + gameCharacter.getAbilityValue(Ability.DEXTERITY);

        return RandomNumberGenerator.getRandomNumber(minDefence, maxDefence);
    }

    private boolean criticalHit(GameCharacter gameCharacter) {
        int criticalHit = gameCharacter.getAbilityValue(Ability.LUCK) + gameCharacter.getAbilityValue(Ability.SKILL);
        return criticalHit >= RandomNumberGenerator.getRandomNumber(0, 100);
    }
}
