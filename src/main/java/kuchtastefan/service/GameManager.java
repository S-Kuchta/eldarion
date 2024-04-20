package kuchtastefan.service;

import kuchtastefan.character.hero.CharacterClass;
import kuchtastefan.character.hero.GameLoaded;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.hero.HeroAbilityManager;
import kuchtastefan.character.npc.vendor.VendorDB;
import kuchtastefan.character.spell.Spell;
import kuchtastefan.character.spell.SpellDB;
import kuchtastefan.constant.Constant;
import kuchtastefan.gameSettings.GameSettingsDB;
import kuchtastefan.hint.HintDB;
import kuchtastefan.hint.HintName;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemDB;
import kuchtastefan.item.ItemType;
import kuchtastefan.item.consumeableItem.ConsumableItemType;
import kuchtastefan.item.craftingItem.CraftingReagentItemType;
import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.item.wearableItem.WearableItemType;
import kuchtastefan.quest.QuestDB;
import kuchtastefan.quest.questGiver.QuestGiverCharacterDB;
import kuchtastefan.utility.ConsoleColor;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.LetterToNumber;
import kuchtastefan.utility.PrintUtil;
import kuchtastefan.world.region.RegionDB;

import java.util.ArrayList;
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
            this.fileService.autoSave(this.hero);
            QuestGiverCharacterDB.setAllQuestGiversName(this.hero);

            PrintUtil.printLongDivider();
            System.out.println("\t\t\t\t\t\t\t------ Main Menu ------");
            PrintUtil.printLongDivider();

            PrintUtil.printIndexAndText("0", "Explore World");
            System.out.println();
            PrintUtil.printIndexAndText("1", "Hero menu");
            System.out.println();
            PrintUtil.printIndexAndText("2", "Save game");
            System.out.println();
            PrintUtil.printIndexAndText("3", "Exit game");
            System.out.println();

            final int choice = InputUtil.intScanner();
            switch (choice) {
                case 0 -> exploreSurroundingRegions();
                case 1 -> this.heroMenuService.heroCharacterMenu(this.hero);
                case 2 -> this.fileService.saveGame(this.hero);
                case 3 -> {
                    System.out.println("Are you sure?");
                    PrintUtil.printIndexAndText("0", "No");
                    System.out.println();
                    PrintUtil.printIndexAndText("1", "Yes");
                    System.out.println();
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
        PrintUtil.printIndexAndText("0", "Go back to the city");
        System.out.println();
        PrintUtil.printIndexAndText("1", "Go to " + RegionDB.returnRegionName(0));
        System.out.println();
        PrintUtil.printIndexAndText("2", "Go to highlands");
        System.out.println();

        final int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
            }
            case 1 -> RegionDB.returnRegion(0).adventuringAcrossTheRegion(heroMenuService, this.hero);
            default -> PrintUtil.printEnterValidInput();
        }
    }

    private void testMethod(ItemType itemType) {
        System.out.println(itemType.toString());
        System.out.println(itemType.name());
        System.out.println(itemType.getDescription());
    }

    private void initGame() {
        testMethod(ConsumableItemType.POTION);
        testMethod(WearableItemType.BOOTS);
        testMethod(CraftingReagentItemType.ALCHEMY_REAGENT);

        this.fileService.importWearableItemsFromFile();
        this.fileService.importCraftingReagentItemsFromFile();
        this.fileService.importConsumableItemsFromFile();
        this.fileService.importQuestItemsFromFile();
        this.fileService.importJunkItemsFromFile();

        GameSettingsDB.initializeGameSettings();

        SpellDB.SPELL_LIST.addAll(this.fileService.importSpellsFromFile());

        this.fileService.importQuestsListFromFile();
        this.fileService.importLocationsFromFile();
        this.fileService.importEnemyGroupFromFile();
        this.fileService.importQuestGiverFromFile();
        this.fileService.importCreaturesFromFile();
        this.fileService.importRegionsFromFile();
        this.fileService.importNamesFromFile();
        this.fileService.importVendorsFromFile();
        this.fileService.importVendorItemListsFromFile();

        HintDB.initializeHintList();

        Item item = ItemDB.returnItemFromDB(200);
        if (item instanceof WearableItem wearableItem) {
            System.out.println(wearableItem.getItemType().equals(WearableItemType.WEAPON));
        }


        System.out.println(ConsoleColor.YELLOW_UNDERLINED + "\t\tWelcome to the Eldarion!\t\t\n" + ConsoleColor.RESET);
        PrintUtil.printIndexAndText("0", "Start new game");
        System.out.println();
        PrintUtil.printIndexAndText("1", "Load game");
        System.out.println();

        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> System.out.println("\tLet's go then!");
            case 1 -> {
                final GameLoaded gameLoaded = fileService.loadGame();
                if (gameLoaded != null) {
                    this.hero = gameLoaded.getHero();
                    this.hero.setLevel(gameLoaded.getHero().getLevel());
                    this.heroAbilityManager.setHero(gameLoaded.getHero());
                    HintDB.getHINT_DB().putAll(gameLoaded.getHintUtil());
                    QuestDB.setInitialQuestsStatus(this.hero);
                    QuestDB.loadQuests(this.hero);
                    this.hero.getRegionActionsWithDuration().addAll(gameLoaded.getRegionActionsWithDuration());

                    return;
                }
            }
            default -> PrintUtil.printEnterValidInput();
        }

        System.out.println("\tEnter your name: ");
        final String name = InputUtil.stringScanner();
        PrintUtil.printLongDivider();

        System.out.println("\tSelect your class: ");
        int index = 0;
        List<CharacterClass> characterClassList = new ArrayList<>();
        for (CharacterClass characterClass : CharacterClass.values()) {
            if (!characterClass.equals(CharacterClass.NPC)) {
                System.out.println("\t" + index + ". " + characterClass.name());
                characterClassList.add(characterClass);
                index++;
            }
        }

        while (true) {
            try {
                final int heroClassChoice = InputUtil.intScanner();
                this.hero.setCharacterClass(characterClassList.get(heroClassChoice));
                break;
            } catch (IndexOutOfBoundsException e) {
                PrintUtil.printEnterValidInput();
            }
        }

        for (Spell spell : SpellDB.SPELL_LIST) {
            if (spell.getSpellLevel() == 0 && spell.getSpellClass().equals(this.hero.getCharacterClass())) {
                this.hero.getCharacterSpellList().add(spell);
            }
        }

        this.hero.setName(name);
        this.hero.setLevel(Constant.INITIAL_LEVEL);
        this.hero.gainExperiencePoints(0);
        QuestDB.setInitialQuestsStatus(this.hero);
        VendorDB.setRandomCurrentVendorCharacterItemListId(this.hero.getLevel());

        System.out.println("\t\tHello " + this.hero.getName() + ", Your class is: " + this.hero.getCharacterClass() + ". Let's start the game!");
        PrintUtil.printLongDivider();

        this.hero.setInitialEquip();
        this.heroAbilityManager.spendAbilityPoints();

        HintDB.printHint(HintName.WELCOME);
    }
}
