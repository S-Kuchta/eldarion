package kuchtastefan.service;

import kuchtastefan.characters.QuestGiverCharacter;
import kuchtastefan.characters.enemy.EnemyList;
import kuchtastefan.characters.hero.GameLoaded;
import kuchtastefan.characters.hero.Hero;
import kuchtastefan.characters.hero.HeroAbilityManager;
import kuchtastefan.characters.hero.HeroCharacterService;
import kuchtastefan.characters.vendor.ConsumableVendorCharacter;
import kuchtastefan.characters.vendor.CraftingReagentItemVendorCharacter;
import kuchtastefan.characters.vendor.JunkVendorCharacter;
import kuchtastefan.constant.Constant;
import kuchtastefan.hint.HintUtil;
import kuchtastefan.items.ItemsLists;
import kuchtastefan.items.consumeableItem.ConsumableItemType;
import kuchtastefan.items.craftingItem.CraftingReagentItemType;
import kuchtastefan.quest.Quest;
import kuchtastefan.quest.QuestReward;
import kuchtastefan.quest.questObjectives.QuestKillObjective;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.regions.ForestRegionService;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

import java.util.List;


public class GameManager {
    private Hero hero;
    private final HeroAbilityManager heroAbilityManager;
    private int currentLevel;
    private final FileService fileService;
    private final ItemsLists itemsLists;
    private final BlacksmithService blacksmithService;
    private ForestRegionService forestRegionService;
    private final HeroCharacterService heroCharacterService;
    private final EnemyList enemyList;

    public GameManager() {
        this.hero = new Hero("");
        this.currentLevel = Constant.INITIAL_LEVEL;
        this.fileService = new FileService();
        this.heroAbilityManager = new HeroAbilityManager(this.hero);
        this.blacksmithService = new BlacksmithService();
        this.itemsLists = new ItemsLists();
        this.heroCharacterService = new HeroCharacterService(this.heroAbilityManager);
        this.enemyList = new EnemyList();
    }

    public void startGame() {
        this.initGame();

        while (true) {
            PrintUtil.printLongDivider();
            System.out.println("\t\t\t\t\t\t\t------ Mystic Hollow ------");
            PrintUtil.printLongDivider();
            System.out.println("\t0. Explore surrounding regions");
            System.out.println("\t1. Hero menu");
            System.out.println("\t2. Junk Merchant");
            System.out.println("\t3. Tavern");
            System.out.println("\t4. Alchemist");
            System.out.println("\t5. Blacksmith");
            System.out.println("\t6. Save game");
            System.out.println("\t7. Exit game");

            final int choice = InputUtil.intScanner();
            switch (choice) {
                case 0 -> exploreSurroundingRegions();
                case 1 -> this.heroCharacterService.heroCharacterMenu(this.hero);
                case 2 -> {
                    JunkVendorCharacter junkVendorCharacter = new JunkVendorCharacter("Dazres Heitholt", 8,
                            this.itemsLists.returnJunkItemListByItemLevel(this.hero.getLevel(), 0));
                    junkVendorCharacter.vendorMenu(this.hero);
                }
                case 3 -> this.tavernMenu();
                case 4 -> this.alchemistMenu();
                case 5 -> this.blacksmithService.blacksmithMenu(this.hero, this.itemsLists);
                case 6 -> this.fileService.saveGame(this.hero, this.currentLevel, this.forestRegionService);
                case 7 -> {
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
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void exploreSurroundingRegions() {
        System.out.println("0. Go back to the city");
        System.out.println("1. Go to " + this.forestRegionService.getRegionName());
        System.out.println("2. Go to highlands");
        final int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
            }
            case 1 -> this.forestRegionService.adventuringAcrossTheRegion(this.heroCharacterService);
            default -> System.out.println("Enter valid input");
        }
    }

    private void tavernMenu() {
        final ConsumableVendorCharacter cityFoodVendor = new ConsumableVendorCharacter("Ved Of Kaedwen", 8,
                this.itemsLists.returnConsumableItemListByTypeAndItemLevel(ConsumableItemType.FOOD, this.hero.getLevel(), null));

        QuestKillObjective questKillObjective = new QuestKillObjective("Kill 3 wolfs", enemyList.returnEnemyMap().get("Wolf").getName(), 3);
        QuestReward questReward = new QuestReward(30, 70, 2);
        questReward.generateRandomWearableItemsReward(1, this.itemsLists.returnWearableItemListByItemLevel(questReward.getQuestLevel(), null));


        Quest quest = new Quest("Dangerous in the forest",
                "In the forest are dangerous wolf, whos kill our miners. Please help us and kill them.", 1, List.of(questKillObjective), questReward);

        QuestGiverCharacter questGiverCharacter = new QuestGiverCharacter("Freya", 8, quest);
        for (QuestObjective questObjective : quest.getQuestObjectives()) {
            questObjective.checkCompleted(this.hero);
        }

        questGiverCharacter.getQuest().checkQuestObjectivesCompleted();


        PrintUtil.printDivider();
        System.out.println("\t\tTavern");
        PrintUtil.printDivider();

        System.out.println("0. Go back");
        System.out.println("1. " + cityFoodVendor.getName() + " (Food Merchant)");
        System.out.println("2. " + questGiverCharacter.getName());

        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
            }
            case 1 -> cityFoodVendor.vendorMenu(this.hero);
            case 2 -> questGiverCharacter.questGiverMenu(this.hero);
        }
    }

    private void alchemistMenu() {
        final CraftingReagentItemVendorCharacter cityAlchemistReagentVendor = new CraftingReagentItemVendorCharacter("Meeden", 8,
                this.itemsLists.returnCraftingReagentItemListByTypeAndItemLevel(CraftingReagentItemType.ALCHEMY_REAGENT, this.hero.getLevel(), 0));
        final ConsumableVendorCharacter cityPotionsVendor = new ConsumableVendorCharacter("Etaefush", 8,
                this.itemsLists.returnConsumableItemListByTypeAndItemLevel(ConsumableItemType.POTION, this.hero.getLevel(), null));

        PrintUtil.printDivider();
        System.out.println("\t\tAlchemist shop");
        PrintUtil.printDivider();

        System.out.println("\t0. Go back");
        System.out.println("\t1. Create potion");
        System.out.println("\t2. " + cityAlchemistReagentVendor.getName() + " (Alchemy reagents Merchant)");
        System.out.println("\t3. " + cityPotionsVendor.getName() + " (Potions Merchant)");

        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
            }
            case 1 -> System.out.println("Work in progress");
            case 2 -> cityAlchemistReagentVendor.vendorMenu(this.hero);
            case 3 -> cityPotionsVendor.vendorMenu(this.hero);
        }
    }

    private void initGame() {
        this.itemsLists.getWearableItemList().addAll(fileService.importWearableItemsFromFile());
        this.itemsLists.getCraftingReagentItems().addAll(fileService.importCraftingReagentItemsFromFile());
        this.itemsLists.getConsumableItems().addAll(fileService.importConsumableItemsFromFile());
        this.itemsLists.getQuestItems().addAll(fileService.importQuestItemsFromFile());
        this.itemsLists.getJunkItems().addAll(fileService.importJunkItemsFromFile());
        this.itemsLists.initializeAllItemsMapToStringItemMap();

        this.enemyList.getEnemyList().addAll(this.fileService.importCreaturesFromFile(this.itemsLists));

        this.forestRegionService = new ForestRegionService("Silverwood Glade", "Magic forest", this.itemsLists, this.hero, this.enemyList);

        HintUtil.initializeHintList();

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
                    HintUtil.getHintList().putAll(gameLoaded.getHintUtil());
                    this.forestRegionService.setHero(this.hero);
                    this.forestRegionService.getDiscoveredLocations().addAll(gameLoaded.getForestRegionDiscoveredLocation());
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
