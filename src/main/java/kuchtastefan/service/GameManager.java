package kuchtastefan.service;

import kuchtastefan.ability.Ability;
import kuchtastefan.ability.HeroAbilityManager;
import kuchtastefan.constant.Constant;
import kuchtastefan.domain.Enemy;
import kuchtastefan.domain.Hero;
import kuchtastefan.domain.LoadedGame;
import kuchtastefan.utility.EnemyGenerator;
import kuchtastefan.utility.InputUtils;
import kuchtastefan.utility.PrintUtils;

import java.util.Map;

public class GameManager {

    private final HeroAbilityManager heroAbilityManager;
    private final Map<Integer, Enemy> enemiesByLevel;
    private Hero hero;
    private final FileService fileService;
    private final BattleService battleService;
    private int currentLevel;

    public GameManager() {
        this.hero = new Hero("");
        this.fileService = new FileService();
        this.battleService = new BattleService();
        this.heroAbilityManager = new HeroAbilityManager(hero);
        this.currentLevel = Constant.INITIAL_LEVEL;
        this.enemiesByLevel = EnemyGenerator.createEnemies();

    }

    public void startGame() {
        initGame();

        while (this.currentLevel <= this.enemiesByLevel.size()) {
            final Enemy enemy = this.enemiesByLevel.get(this.currentLevel);
            System.out.println("0. Fight " + enemy.getName() + "(level " + this.currentLevel + ")");
            System.out.println("1. Upgrade abilities (" + hero.getHeroAvailablePoints() + " points to spend.");
            System.out.println("2. Save game");
            System.out.println("3. Exit game");

            final int choice = InputUtils.readInt();
            switch (choice) {
                case 0 -> {
                    if (battleService.isHeroReady(this.hero, enemy)) {
                        final int heroHealthBeforeBattle = this.hero.getAbilities().get(Ability.HEALTH);

                        final boolean hasHeroWon = this.battleService.battle(this.hero, enemy);

                        if (hasHeroWon) {
                            PrintUtils.printDivider();
                            System.out.println("You have won this battle! You have gained " + this.currentLevel + " ability points.");
                            this.hero.updateAvailablePoints(this.currentLevel);
                            this.currentLevel++;
                        } else {
                            System.out.println("You have lost");
                        }

                        this.hero.setAbility(Ability.HEALTH, heroHealthBeforeBattle);
                        System.out.println("You have full health now");
                        PrintUtils.printDivider();
                    }
                }
                case 1 -> {
                    this.upgradeAbilities();
                }
                case 2 -> {
                    this.fileService.saveGame(this.hero, this.currentLevel);
                }
                case 3 -> {
                    System.out.println("Are you sure?");
                    System.out.println("0. No");
                    System.out.println("1. Yes");
                    final int exitChoice = InputUtils.readInt();
                    if (exitChoice == 1) {
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
            case 0 -> {
            }
            case 1 -> this.heroAbilityManager.spendHeroAvailablePoints();
            case 2 -> this.heroAbilityManager.removeHeroAvailablePoints();
            default -> System.out.println("Invalid choice");
        }
    }

    private void initGame() {
        System.out.println("Welcome to the Gladiatus game!");
        System.out.println("0. Start new game");
        System.out.println("1. Load game");
        final int choice = InputUtils.readInt();
        switch (choice) {
            case 0 -> System.out.println("Let's go then");
            case 1 -> {
                final LoadedGame loadGame = fileService.loadGame();
                if (loadGame != null) {
                    this.hero = loadGame.getHero();
                    this.currentLevel = loadGame.getLevel();
                    return;
                }
            }
            default -> System.out.println("Invalid choice");
        }
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
