package kuchtastefan.service;

import kuchtastefan.ability.HeroAbilityManager;
import kuchtastefan.domain.Hero;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

public class GameManager {
    private final Hero hero;
    private final HeroAbilityManager heroAbilityManager;

    public GameManager() {
        this.hero = new Hero("");
        this.heroAbilityManager = new HeroAbilityManager(hero);
    }

    public void startGame() {

        System.out.println("Welcome to the Gladiatus game!");
        System.out.println("Enter your name: ");
        final String name = InputUtil.stringScanner();
        PrintUtil.printDivider();

        final Hero hero = new Hero(name);
        System.out.println("Hello " + hero.getName() + ". Let's start the game!");
        PrintUtil.printDivider();

        heroAbilityManager.spendAbilityPoints();
    }
}
