package kuchtastefan.service;

import kuchtastefan.character.hero.*;
import kuchtastefan.character.npc.CharacterList;
import kuchtastefan.character.npc.QuestGiverCharacter;
import kuchtastefan.character.npc.vendor.ConsumableVendorCharacter;
import kuchtastefan.character.npc.vendor.CraftingReagentItemVendorCharacter;
import kuchtastefan.character.npc.vendor.JunkVendorCharacter;
import kuchtastefan.character.spell.Spell;
import kuchtastefan.character.spell.SpellsList;
import kuchtastefan.constant.Constant;
import kuchtastefan.gameSettings.GameSettingsService;
import kuchtastefan.hint.HintName;
import kuchtastefan.hint.HintUtil;
import kuchtastefan.item.ItemsLists;
import kuchtastefan.item.consumeableItem.ConsumableItemType;
import kuchtastefan.item.craftingItem.CraftingReagentItemType;
import kuchtastefan.quest.QuestMap;
import kuchtastefan.region.ForestRegionService;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private Hero hero;
    private final HeroAbilityManager heroAbilityManager;
    private final FileService fileService;
    private final BlacksmithService blacksmithService;
    private ForestRegionService forestRegionService;
    private final HeroMenuService heroMenuService;


    public GameManager() {
        this.hero = new Hero("");
        this.fileService = new FileService();
        this.heroAbilityManager = new HeroAbilityManager(this.hero);
        this.blacksmithService = new BlacksmithService();
        this.heroMenuService = new HeroMenuService(this.heroAbilityManager);
    }

    public void startGame() {
        this.initGame();

        while (true) {
            PrintUtil.printLongDivider();
            System.out.println("\t\t\t\t\t\t\t------ Mystic Hollow ------");
            PrintUtil.printLongDivider();
            PrintUtil.printIndexAndText("0", "Explore surrounding regions");
            System.out.println();
            PrintUtil.printIndexAndText("1", "Hero menu");
            System.out.println();
            PrintUtil.printIndexAndText("2", "Junk Merchant");
            System.out.println();
            PrintUtil.printIndexAndText("3", "Tavern");
            System.out.println();
            PrintUtil.printIndexAndText("4", "Alchemist");
            System.out.println();
            PrintUtil.printIndexAndText("5", "Blacksmith");
            System.out.println();
            PrintUtil.printIndexAndText("6", "Save game");
            System.out.println();
            PrintUtil.printIndexAndText("7", "Exit game");
            System.out.println();

            final int choice = InputUtil.intScanner();
            switch (choice) {
                case 0 -> exploreSurroundingRegions();
                case 1 -> this.heroMenuService.heroCharacterMenu(this.hero);
                case 2 -> {
                    JunkVendorCharacter junkVendorCharacter = new JunkVendorCharacter("Dazres Heitholt", 8,
                            ItemsLists.returnJunkItemListByItemLevel(this.hero.getLevel(), 0));
                    junkVendorCharacter.vendorMenu(this.hero);
                }
                case 3 -> this.tavernMenu();
                case 4 -> this.alchemistMenu();
                case 5 -> this.blacksmithService.blacksmithMenu(this.hero);
                case 6 -> this.fileService.saveGame(this.hero);
                case 7 -> {
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
        PrintUtil.printIndexAndText("1", "Go to " + this.forestRegionService.getRegionName());
        System.out.println();
        PrintUtil.printIndexAndText("2", "Go to highlands");
        System.out.println();

        final int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
            }
            case 1 -> this.forestRegionService.adventuringAcrossTheRegion(this.heroMenuService);
            default -> PrintUtil.printEnterValidInput();
        }
    }

    private void tavernMenu() {
        final ConsumableVendorCharacter cityFoodVendor = new ConsumableVendorCharacter("Ved Of Kaedwen", 8,
                ItemsLists.returnConsumableItemListByTypeAndItemLevel(ConsumableItemType.FOOD, this.hero.getLevel(), null));

        QuestGiverCharacter questGiverCharacter = CreateNewQuestGiverCharacter.questGiver("Freya", 4, this.hero);
//        QuestGiverCharacter questGiverCharacter = new QuestGiverCharacter("Freya", 8);
//        questGiverCharacter.addQuest(QuestList.questList.get(4));
//        questGiverCharacter.setNameBasedOnQuestsAvailable(this.hero);

        QuestGiverCharacter questGiverCharacter1 = new QuestGiverCharacter("Devom Of Cremora", 8);
        questGiverCharacter1.addQuest(QuestMap.mapIdQuest.get(2));
        questGiverCharacter1.setNameBasedOnQuestsAvailable(this.hero);

        PrintUtil.printDivider();
        System.out.println("\t\tTavern");
        PrintUtil.printDivider();

        PrintUtil.printIndexAndText("0", "Go back");
        System.out.println();
        PrintUtil.printIndexAndText("1", cityFoodVendor.getName() + " (Food Merchant)");
        System.out.println();
        PrintUtil.printIndexAndText("2", questGiverCharacter.getName());
        System.out.println();
        PrintUtil.printIndexAndText("3", questGiverCharacter1.getName());
        System.out.println();

        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
            }
            case 1 -> cityFoodVendor.vendorMenu(this.hero);
            case 2 -> questGiverCharacter.questGiverMenu(this.hero);
            case 3 -> questGiverCharacter1.questGiverMenu(this.hero);
        }
    }

    private void alchemistMenu() {
        final CraftingReagentItemVendorCharacter cityAlchemistReagentVendor = new CraftingReagentItemVendorCharacter("Meeden", 8,
                ItemsLists.returnCraftingReagentItemListByTypeAndItemLevel(CraftingReagentItemType.ALCHEMY_REAGENT, this.hero.getLevel(), 0));
        final ConsumableVendorCharacter cityPotionsVendor = new ConsumableVendorCharacter("Etaefush", 8,
                ItemsLists.returnConsumableItemListByTypeAndItemLevel(ConsumableItemType.POTION, this.hero.getLevel(), null));

        QuestGiverCharacter questGiverCharacter = new QuestGiverCharacter("High Priestess Elara", 8);
        questGiverCharacter.addQuest(QuestMap.mapIdQuest.get(3));
        questGiverCharacter.addQuest(QuestMap.mapIdQuest.get(5));
        questGiverCharacter.addQuest(QuestMap.mapIdQuest.get(6));

        questGiverCharacter.setNameBasedOnQuestsAvailable(this.hero);

        PrintUtil.printDivider();
        System.out.println("\t\tAlchemist shop");
        PrintUtil.printDivider();

        PrintUtil.printIndexAndText("0", "Go back");
        System.out.println();
        PrintUtil.printIndexAndText("1", "Create potion");
        System.out.println();
        PrintUtil.printIndexAndText("2", cityAlchemistReagentVendor.getName() + " (Alchemy reagents Merchant)");
        System.out.println();
        PrintUtil.printIndexAndText("3", cityPotionsVendor.getName() + " (Potions Merchant)");
        System.out.println();
        PrintUtil.printIndexAndText("4", questGiverCharacter.getName());
        System.out.println();

        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
            }
            case 1 -> System.out.println("Work in progress");
            case 2 -> cityAlchemistReagentVendor.vendorMenu(this.hero);
            case 3 -> cityPotionsVendor.vendorMenu(this.hero);
            case 4 -> questGiverCharacter.questGiverMenu(this.hero);
            default -> PrintUtil.printEnterValidInput();
        }
    }

    private void initGame() {
        ItemsLists.getWearableItemList().addAll(fileService.importWearableItemsFromFile());
        ItemsLists.getCraftingReagentItems().addAll(fileService.importCraftingReagentItemsFromFile());
        ItemsLists.getConsumableItems().addAll(fileService.importConsumableItemsFromFile());
        ItemsLists.getQuestItems().addAll(fileService.importQuestItemsFromFile());
        ItemsLists.getJunkItems().addAll(fileService.importJunkItemsFromFile());
        ItemsLists.initializeAllItemsMapToStringItemMap();

        GameSettingsService.initializeGameSettings();

//        QuestList.questList.addAll(this.fileService.importQuestsListFromFile());

        SpellsList.spellList.addAll(this.fileService.importSpellsFromFile());
        CharacterList.getEnemyList().addAll(this.fileService.importCreaturesFromFile());

        this.fileService.importQuestsListFromFile();
        this.fileService.importLocationsFromFile();
        this.fileService.importEnemyGroupFromFile();

        this.forestRegionService = new ForestRegionService("Silverwood Glade", "Magic forest", this.hero, 1, 5);

        HintUtil.initializeHintList();


        System.out.println("Welcome to the Gladiatus game!");
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
                    HintUtil.getHintList().putAll(gameLoaded.getHintUtil());
                    this.hero.getRegionActionsWithDuration().addAll(gameLoaded.getRegionActionsWithDuration());
                    this.forestRegionService.setHero(this.hero);
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

        for (Spell spell : SpellsList.spellList) {
            if (spell.getSpellLevel() == 0 && spell.getSpellClass().equals(this.hero.getCharacterClass())) {
                this.hero.getCharacterSpellList().add(spell);
            }
        }

        this.hero.setName(name);
        this.hero.setLevel(1);
        this.hero.gainExperiencePoints(0);
        System.out.println("\t\tHello " + this.hero.getName() + ", Your class is: " + this.hero.getCharacterClass() + ". Let's start the game!");
        PrintUtil.printLongDivider();

        this.heroAbilityManager.spendAbilityPoints();
        HintUtil.printHint(HintName.WELCOME);
    }
}
