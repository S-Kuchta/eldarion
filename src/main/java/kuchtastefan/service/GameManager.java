package kuchtastefan.service;

import kuchtastefan.ability.HeroAbilityManager;
import kuchtastefan.constant.Constant;
import kuchtastefan.domain.Hero;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

public class GameManager {
    private final Hero hero;
    private final HeroAbilityManager heroAbilityManager;
    private int currentLevel;
    private final FileService fileService;

    public GameManager() {
        this.hero = new Hero("");
        this.heroAbilityManager = new HeroAbilityManager(hero);
        this.currentLevel = Constant.INITIAL_LEVEL;
        this.fileService = new FileService();
    }

    public void startGame() {

        startNewGameOrLoadExisting();

        while (this.currentLevel <= 5) {
            System.out.println("0. Fight " + "level " + this.currentLevel);
            System.out.println("1. Upgrade abilities (" + this.hero.getUnspentAbilityPoints() + " points to spend)");
            System.out.println("2. Save game");
            System.out.println("3. Exit game");

            final int choice = InputUtil.intScanner();
            switch (choice) {
                case 0 -> {
                    // TODO FIGHT
                    this.currentLevel += 1;
                }
                case 1 -> {
                    System.out.println("0. Go Back");
                    System.out.println("1. Spend points (" + hero.getUnspentAbilityPoints() + " points left)");
                    System.out.println("2. Remove points");
                    final int upgradeChoice = InputUtil.intScanner();
                    if (upgradeChoice == 1) {
                        this.heroAbilityManager.spendAbilityPoints();
                    } else if (upgradeChoice == 2) {
                        this.heroAbilityManager.removeAbilityPoints();
                    }
                }
                case 2 -> {
                    fileService.saveGame(hero, this.currentLevel);
                }
                case 3 -> {
                    System.out.println("Are you sure?");
                    System.out.println("0. No");
                    System.out.println("1. Yes");
                    final int exitChoice = InputUtil.intScanner();
                    if (exitChoice == 0) {
                        System.out.println("Continuing...");
                        continue;
                    }
                    if (exitChoice == 1) {
                        System.out.println("Bye");
                        return;
                    }
                }
                default -> System.out.println("Invalid choice.");
            }
        }

        System.out.println("You have won the game! Congratulations!");
    }

    private void startNewGameOrLoadExisting() {
        while (true) {
            System.out.println("0. Start new game");
            System.out.println("1. Load game");
            int choiceNewOrLoadGame = InputUtil.intScanner();
            switch (choiceNewOrLoadGame) {
                case 0 -> initGame();
                case 1 -> this.currentLevel = fileService.loadGame(this.hero);
                default -> System.out.println("Enter valid input");
            }
            break;
        }
    }

    private void initGame() {
        System.out.println("Welcome to the Gladiatus game!");

        System.out.println("Enter your name: ");
        final String name = InputUtil.stringScanner();
        PrintUtil.printDivider();

        final Hero hero = new Hero(name);
        this.hero.setName(name);
        System.out.println("Hello " + hero.getName() + ". Let's start the game!");
        PrintUtil.printDivider();

        heroAbilityManager.spendAbilityPoints();
    }
}
