package kuchtastefan.service;

import kuchtastefan.ability.HeroAbilityManager;
import kuchtastefan.domain.Hero;
import kuchtastefan.utility.InputUtils;
import kuchtastefan.utility.PrintUtils;

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
        final String name = InputUtils.readString();
        this.hero.setName(name);
        System.out.println("Hello " + hero.getName() + ". Let's start the game!");
        PrintUtils.printDivider();

        PrintUtils.printAbilities(hero);
        PrintUtils.printDivider();
        this.heroAbilityManager.spendHeroAvailablePoints();
    }
}
