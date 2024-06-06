package kuchtastefan.service;

import kuchtastefan.character.hero.CharacterClass;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.hero.HeroAbilityManager;
import kuchtastefan.character.hero.save.GameLoaded;
import kuchtastefan.character.npc.vendor.VendorCharacterDB;
import kuchtastefan.character.spell.SpellDB;
import kuchtastefan.constant.Constant;
import kuchtastefan.gameSettings.GameSettingsDB;
import kuchtastefan.hint.HintDB;
import kuchtastefan.hint.HintName;
import kuchtastefan.quest.Quest;
import kuchtastefan.quest.QuestDB;
import kuchtastefan.quest.questGiver.QuestGiverCharacterDB;
import kuchtastefan.quest.questObjectives.QuestObjectiveDB;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.printUtil.PrintUtil;
import kuchtastefan.world.location.LocationDB;
import kuchtastefan.world.region.RegionDB;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GameManager {
    private Hero hero;
    private final HeroAbilityManager heroAbilityManager;
    private final FileService fileService;
    private final HeroMenuService heroMenuService;


    public GameManager() {
        this.hero = new Hero("");
        this.fileService = new FileService();
        this.heroAbilityManager = new HeroAbilityManager(this.hero);
        this.heroMenuService = new HeroMenuService(this.heroAbilityManager);
    }

    public void startGame() {
        this.initGame();

        while (true) {
            QuestGiverCharacterDB.setAllQuestGiversName(this.hero);

            System.out.println();
            PrintUtil.printLongDivider();
            System.out.println("\t\t\t\t\t\t\t------ Main Menu ------");
            PrintUtil.printLongDivider();

            PrintUtil.printMenuOptions("Explore World", "Hero menu", "Save game", "Exit game");

            final int choice = InputUtil.intScanner();
            switch (choice) {
                case 0 -> exploreSurroundingRegions();
                case 1 -> this.heroMenuService.heroCharacterMenu(this.hero);
                case 2 -> this.fileService.saveGame(this.hero);
                case 3 -> {
                    System.out.println("Are you sure?");
                    PrintUtil.printMenuOptions("No", "Yes");
                    final int exitChoice = InputUtil.intScanner();
                    if (exitChoice == 0) {
                        continue;
                    }
                    if (exitChoice == 1) {
                        System.out.println("\tBye");
                        return;
                    }
                }
                default -> PrintUtil.printEnterValidInput();
            }
        }
    }

    private void exploreSurroundingRegions() {
        PrintUtil.printMenuOptions("Go back to the city");
        for (int i = 0; i < RegionDB.getREGION_DB().size(); i++) {
            PrintUtil.printIndexAndText(String.valueOf(i + 1), "Go to " + RegionDB.returnRegionName(i));
            System.out.println();
        }

        while (true) {
            int choice = InputUtil.intScanner();
            if (choice == 0) {
                return;
            } else if (choice > 0 && choice <= RegionDB.getREGION_DB().size()) {
                RegionDB.returnRegion(choice - 1).adventuringAcrossTheRegion(heroMenuService, this.hero);
                break;
            } else {
                PrintUtil.printEnterValidInput();
            }
        }
    }

    private void initGame() {
        this.handleImportsFromFiles();

        System.out.println(ConsoleColor.YELLOW_UNDERLINED + "\t\tWelcome to the Eldarion!\t\t\n" + ConsoleColor.RESET);
        PrintUtil.printMenuOptions("Start new game", "Load game");

        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
                System.out.println("\tLet's go then!");
                startNewGame();
            }
            case 1 -> {
                final GameLoaded gameLoaded = fileService.loadGame();
                if (gameLoaded != null) {
                    loadGame(gameLoaded);
                }
            }
            default -> PrintUtil.printEnterValidInput();
        }

        List<Quest> quests = new ArrayList<>(QuestDB.getQUEST_DB().values());
        QuestDB.setInitialQuestsStatusFromGivenList(this.hero, quests);
    }

    private void startNewGame() {
        System.out.println("\tEnter your name: ");
        final String name = InputUtil.stringScanner();

        PrintUtil.printLongDivider();
        classSelect();
        this.hero.spellInit();
        this.hero.setName(name);
        this.hero.setLevel(Constant.INITIAL_LEVEL);
        this.hero.gainExperiencePoints(0);

        VendorCharacterDB.setRandomCurrentVendorCharacterItemListId(this.hero.getLevel());
        System.out.println("\t\tHello " + this.hero.getName() + ", Your character class is: " + this.hero.getCharacterClass() + ". Let's start the game!");
        PrintUtil.printLongDivider();

        this.hero.setInitialEquip();
        this.heroAbilityManager.spendAbilityPoints();

        HintDB.printHint(HintName.WELCOME);
    }

    private void loadGame(GameLoaded gameLoaded) {
        this.hero = gameLoaded.getHero();
        this.hero.setLevel(gameLoaded.getHero().getLevel());
        this.hero.setBuffsAndDebuffs(new HashSet<>());
        this.heroAbilityManager.setHero(gameLoaded.getHero());
        HintDB.getHINT_DB().putAll(gameLoaded.getHintUtil());

        LocationDB.syncWithSaveGame(gameLoaded.getHero().getSaveGameEntities().getHeroLocations());
        QuestDB.syncWithSaveGame(gameLoaded.getHero().getSaveGameEntities().getHeroQuests());
        QuestObjectiveDB.syncWithSaveGame(gameLoaded.getHero().getSaveGameEntities().getHeroQuestObjectives());
//        QuestDB.loadQuests(this.hero);
        VendorCharacterDB.setVendorCurrentCharacterItemListId(gameLoaded.getVendorIdAndItemListId());
    }

    private void classSelect() {
        System.out.println("\tSelect your character class: ");
        List<CharacterClass> characterClassList = new ArrayList<>(List.of(CharacterClass.values()));
        characterClassList.removeIf(characterClass -> characterClass.equals(CharacterClass.NPC));

        for (int i = 0; i < characterClassList.size(); i++) {
            PrintUtil.printIndexAndText(String.valueOf(i), characterClassList.get(i).toString());
        }

        System.out.println();
        while (true) {
            int choice = InputUtil.intScanner();
            if (choice >= 0 && choice < characterClassList.size()) {
                this.hero.setCharacterClass(characterClassList.get(choice));
                break;
            } else {
                PrintUtil.printEnterValidInput();
            }
        }
    }

    private void handleImportsFromFiles() {
        this.fileService.importWearableItemsFromFile();
        this.fileService.importCraftingReagentItemsFromFile();
        this.fileService.importConsumableItemsFromFile();
        this.fileService.importQuestItemsFromFile();
        this.fileService.importJunkItemsFromFile();
        this.fileService.importKeyItemsFromFile();

        GameSettingsDB.initializeGameSettings();

        SpellDB.SPELL_LIST.addAll(this.fileService.importSpellsFromFile());

        this.fileService.importQuestGiverFromFile();
        this.fileService.importEnemyGroupFromFile();
        this.fileService.importCreaturesFromFile();
        this.fileService.importLocationsFromFile();
        this.fileService.importQuestsObjectiveFromFile();
        this.fileService.importQuestsListFromFile();
        this.fileService.importRegionsFromFile();
        this.fileService.importNamesFromFile();
        this.fileService.importVendorsFromFile();
        this.fileService.importVendorItemListsFromFile();

        HintDB.initializeHintList();
    }
}
