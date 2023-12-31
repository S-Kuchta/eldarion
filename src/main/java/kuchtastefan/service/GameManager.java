package kuchtastefan.service;

import kuchtastefan.ability.Ability;
import kuchtastefan.ability.HeroAbilityManager;
import kuchtastefan.constant.Constant;
import kuchtastefan.domain.Enemy;
import kuchtastefan.domain.GameLoaded;
import kuchtastefan.domain.Hero;
import kuchtastefan.item.wearableItem.wearableItem;
import kuchtastefan.item.ItemList;
import kuchtastefan.utility.EnemyGenerator;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameManager {
    private Hero hero;
    private final HeroAbilityManager heroAbilityManager;
    private int currentLevel;
    private final FileService fileService;
    private final Map<Integer, Enemy> enemiesByLevel;
    private final BattleService battleService;
    private final ItemList itemList;
    private List<wearableItem> wearableItems = new ArrayList<>();
    private final BlacksmithService blacksmithService;
    private final InventoryService inventoryService;
    private final ShopService shopService;

    public GameManager() {
        this.hero = new Hero("");
        this.currentLevel = Constant.INITIAL_LEVEL;
        this.fileService = new FileService();
        this.battleService = new BattleService();
        this.enemiesByLevel = EnemyGenerator.createEnemies();
        this.heroAbilityManager = new HeroAbilityManager(hero);
        this.itemList = new ItemList(new ArrayList<>());
        this.inventoryService = new InventoryService();
        this.shopService = new ShopService();
        this.blacksmithService = new BlacksmithService();
    }

    public void startGame() {
        this.initGame();

        while (this.currentLevel <= this.enemiesByLevel.size()) {
            final Enemy enemy = this.enemiesByLevel.get(this.currentLevel);
            System.out.println("0. Fight " + enemy.getName() + " (level " + this.currentLevel + ")");
            System.out.println("1. Upgrade abilities (" + this.hero.getUnspentAbilityPoints() + " points to spend)");
            System.out.println("2. Save game");
            System.out.println("3. Exit game");

            System.out.println("5. Inventory");
            System.out.println("6. Market");
            System.out.println("7. Blacksmith");

            final int choice = InputUtil.intScanner();
            switch (choice) {
                case 0 -> {
                    if (this.battleService.isHeroReadyToBattle(this.hero, enemy, this.wearableItems)) {
                        final int heroHealthBeforeBattle = this.hero.getAbilities().get(Ability.HEALTH);
                        final boolean haveHeroWon = battleService.battle(this.hero, enemy);
                        if (haveHeroWon) {
                            PrintUtil.printDivider();
                            System.out.println("You have won this battle! You have gained " + this.currentLevel + " points to spend");
                            this.hero.updateAbilityPoints(this.currentLevel);
                            this.hero.setHeroGold(50 * this.currentLevel);
                            this.currentLevel++;
                        } else {
                            System.out.println("You have lost!");
                        }

                        this.hero.setAbility(Ability.HEALTH, heroHealthBeforeBattle);
                        System.out.println("You have full health now!");
                        PrintUtil.printDivider();
                    }
                }
                case 1 -> this.upgradeAbilityMenu();
                case 2 -> fileService.saveGame(this.hero, this.currentLevel);
                case 3 -> {
                    System.out.println("Are you sure?");
                    System.out.println("0. No");
                    System.out.println("1. Yes");
                    final int exitChoice = InputUtil.intScanner();
                    if (exitChoice == 0) {
                        continue;
                    }
                    if (exitChoice == 1) {
                        System.out.println("Bye");
                        return;
                    }
                }
                case 4 -> {
                }
                case 5 -> this.inventoryMenu();
                case 6 -> this.shopMenu();
                case 7 -> blacksmith();
                default -> System.out.println("Invalid choice.");
            }
        }

        System.out.println("You have won the game! Congratulations!");
    }

    public void blacksmith() {
        System.out.println("0. Go back");
        System.out.println("1. Improve item quality");
        System.out.println("2. Destroy item");
        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
            }
            case 1 -> this.blacksmithService.improveItemQuality(this.hero);
            case 2 -> this.blacksmithService.destroyItem(this.hero);
            default -> System.out.println("Enter valid input");
        }
    }

    public void inventoryMenu() {
        System.out.println("0. Go back");
        System.out.println("1. Items");
        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
            }
            case 1 -> this.inventoryService.inventoryMenu(this.hero);
            default -> System.out.println("Enter valid input");
        }
    }

    public void shopMenu() {
        System.out.println("0. Go back");
        System.out.println("1. Items");
        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
            }
            case 1 -> this.shopService.shopMenu(this.hero, this.wearableItems);
            default -> System.out.println("Enter valid input");
        }
    }

    private void upgradeAbilityMenu() {
        System.out.println("0. Go Back");
        System.out.println("1. Spend points (" + this.hero.getUnspentAbilityPoints() + " points left)");
        System.out.println("2. Remove points");
        final int upgradeChoice = InputUtil.intScanner();
        if (upgradeChoice == 1) {
            this.heroAbilityManager.spendAbilityPoints();
        } else if (upgradeChoice == 2) {
            this.heroAbilityManager.removeAbilityPoints();
        }
    }

    private void initGame() {
        this.wearableItems = fileService.item(itemList.getItemList());

        System.out.println("Welcome to the Gladiatus game!");
        System.out.println("0. Start new game");
        System.out.println("1. Load game");

        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> System.out.println("Let's go then!");
            case 1 -> {
                final GameLoaded gameLoaded = fileService.loadGame();
                if (gameLoaded != null) {
                    this.hero = gameLoaded.getHero();
                    this.currentLevel = gameLoaded.getLevel();
                    this.heroAbilityManager.setHero(gameLoaded.getHero());
                    return;
                }
            }
            default -> System.out.println("Invalid choice");
        }

        System.out.println("Enter your name: ");
        final String name = InputUtil.stringScanner();
        PrintUtil.printDivider();

        this.hero.setName(name);
        System.out.println("\t\tHello " + hero.getName() + ". Let's start the game!");
        PrintUtil.printDivider();

        this.heroAbilityManager.spendAbilityPoints();
    }
}
