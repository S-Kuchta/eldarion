package kuchtastefan.service;

import kuchtastefan.ability.Ability;
import kuchtastefan.ability.HeroAbilityManager;
import kuchtastefan.constant.Constant;
import kuchtastefan.domain.Enemy;
import kuchtastefan.domain.GameLoaded;
import kuchtastefan.domain.Hero;
import kuchtastefan.item.Item;
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
    private List<Item> items = new ArrayList<>();

    public GameManager() {
        this.hero = new Hero("");
        this.currentLevel = Constant.INITIAL_LEVEL;
        this.fileService = new FileService();
        this.battleService = new BattleService();
        this.enemiesByLevel = EnemyGenerator.createEnemies();
        this.heroAbilityManager = new HeroAbilityManager(hero);
        this.itemList = new ItemList(new ArrayList<>());
    }

    public void startGame() {
        this.initGame();
        for (Item oneItem : this.items) {
//            if (oneItem.getType().equals(ItemType.AXE)) {
            System.out.println(oneItem.getName() + ", "
                    + oneItem.getAbilities() + ", "
                    + oneItem.getType() + " ("
                    + oneItem.getType().getDescription() + ")");
//            }
        }

        while (this.currentLevel <= this.enemiesByLevel.size()) {
            final Enemy enemy = this.enemiesByLevel.get(this.currentLevel);
            System.out.println("0. Fight " + enemy.getName() + " (level " + this.currentLevel + ")");
            System.out.println("1. Upgrade abilities (" + this.hero.getUnspentAbilityPoints() + " points to spend)");
            System.out.println("2. Save game");
            System.out.println("3. Exit game");
            System.out.println("4. Equip items");
            System.out.println("5. Remove equipped items");

            final int choice = InputUtil.intScanner();
            switch (choice) {
                case 0 -> {
                    if (this.battleService.isHeroReadyToBattle(this.hero, enemy, this.items)) {
                        final int heroHealthBeforeBattle = this.hero.getAbilities().get(Ability.HEALTH);
                        final boolean haveHeroWon = battleService.battle(this.hero, enemy);
                        if (haveHeroWon) {
                            PrintUtil.printDivider();
                            System.out.println("You have won this battle! You have gained " + this.currentLevel + " points to spend");
                            this.hero.updateAbilityPoints(this.currentLevel);
                            this.currentLevel++;
                        } else {
                            System.out.println("You have lost!");
                        }

                        this.hero.setAbility(Ability.HEALTH, heroHealthBeforeBattle);
                        System.out.println("You have full health now!");
                        PrintUtil.printDivider();
                    }
                }
                case 1 -> this.upgradeAbility();
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
                case 4 -> this.hero.equipItems(items);
                case 5 -> this.hero.removeEquippedItems(items);
                default -> System.out.println("Invalid choice.");
            }
        }

        System.out.println("You have won the game! Congratulations!");
    }

    public void inventoryMenu() {
        System.out.println("0. Items");
        int choice = InputUtil.intScanner();
//        switch (choice) {
//            case 0 ->
//        }
    }

    private void upgradeAbility() {
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
        this.items = fileService.item(itemList.getItemList());

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
                    this.hero.setEquippedItem(gameLoaded.getEquippedItems());
                    this.hero.equipItems(itemList.getItemList());
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
        System.out.println("Hello " + hero.getName() + ". Let's start the game!");
        PrintUtil.printDivider();

        this.heroAbilityManager.spendAbilityPoints();
    }
}
