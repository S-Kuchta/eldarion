package kuchtastefan.service;

import kuchtastefan.ability.HeroAbilityManager;
import kuchtastefan.constant.Constant;
import kuchtastefan.domain.Hero;
import kuchtastefan.utility.InputUtils;
import kuchtastefan.utility.PrintUtils;

public class GameManager {
    private final Hero hero;
    private final HeroAbilityManager heroAbilityManager;
    private int currentLevel;

    public GameManager() {
        this.hero = new Hero("");
        this.heroAbilityManager = new HeroAbilityManager(hero);
        this.currentLevel = Constant.INITIAL_LEVEL;
    }

    public void startGame() {
        initGame();

        while (this.currentLevel <= 5) {
            System.out.println("0. Fight " + "level " + this.currentLevel);
            System.out.println("1. Upgrade abilities (" + hero.getHeroAvailablePoints() + " points to spend.");
            System.out.println("2. Save game");
            System.out.println("3. Exit game");

            final int choice = InputUtils.readInt();
            switch (choice) {
                case 0 -> {
                    // TODO FIGHT
                    this.currentLevel += 1;
                }
                case 1 -> {
                    upgradeAbilities();
                }
                case 2-> {
                    // TODO save game
                }
                case 3 -> {
                    System.out.println("Are you sure?");
                    System.out.println("0. No");
                    System.out.println("1. Yes");
                    final int exitChoice = InputUtils.readInt();
                    if(exitChoice == 1) {
                        System.out.println("Bye");
                        return;
                    }
                    System.out.println("Continuing...");
                }
                default -> System.out.println("Invalid choice.");
            }
        }

        System.out.println("You have won the game! Congratulations!");
    }

    private void upgradeAbilities() {
        System.out.println("Your abilities are:");
        PrintUtils.printAbilities(hero);

        System.out.println("0. Go back");
        System.out.println("1. Spend points(" + this.hero.getHeroAvailablePoints() + " points to spend)");
        System.out.println("2. Remove points");

        final int choice = InputUtils.readInt();
        switch (choice) {
            case 0 -> {}
            case 1 -> this.heroAbilityManager.spendHeroAvailablePoints();
            case 2 -> this.heroAbilityManager.removeHeroAvailablePoints();
            default -> System.out.println("Invalid choice");
        }
    }

    private void initGame() {
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
