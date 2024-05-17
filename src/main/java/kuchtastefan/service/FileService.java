package kuchtastefan.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import kuchtastefan.ability.Ability;
import kuchtastefan.actions.Action;
import kuchtastefan.character.hero.GameLoaded;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.npc.CharacterDB;
import kuchtastefan.character.npc.CharacterType;
import kuchtastefan.character.npc.NonPlayerCharacter;
import kuchtastefan.character.npc.enemy.EnemyGroup;
import kuchtastefan.character.npc.enemy.EnemyGroupDB;
import kuchtastefan.character.npc.vendor.VendorCharacter;
import kuchtastefan.character.npc.vendor.VendorCharacterDB;
import kuchtastefan.character.npc.vendor.vendorOffer.VendorItemList;
import kuchtastefan.character.npc.vendor.vendorOffer.VendorOfferDB;
import kuchtastefan.character.spell.Spell;
import kuchtastefan.character.spell.SpellDB;
import kuchtastefan.gameSettings.GameSetting;
import kuchtastefan.gameSettings.GameSettingsDB;
import kuchtastefan.hint.HintDB;
import kuchtastefan.item.ItemDB;
import kuchtastefan.item.specificItems.consumeableItem.ConsumableItem;
import kuchtastefan.item.specificItems.consumeableItem.ConsumableItemType;
import kuchtastefan.item.specificItems.craftingItem.CraftingReagentItem;
import kuchtastefan.item.specificItems.craftingItem.CraftingReagentItemType;
import kuchtastefan.item.specificItems.junkItem.JunkItem;
import kuchtastefan.item.specificItems.keyItem.KeyItem;
import kuchtastefan.item.specificItems.questItem.QuestItem;
import kuchtastefan.item.specificItems.questItem.UsableQuestItem;
import kuchtastefan.item.specificItems.wearableItem.WearableItem;
import kuchtastefan.item.specificItems.wearableItem.WearableItemQuality;
import kuchtastefan.item.specificItems.wearableItem.WearableItemType;
import kuchtastefan.quest.Quest;
import kuchtastefan.quest.QuestDB;
import kuchtastefan.quest.questGiver.QuestGiverCharacter;
import kuchtastefan.quest.questGiver.QuestGiverCharacterDB;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.quest.questObjectives.QuestObjectiveDB;
import kuchtastefan.utility.*;
import kuchtastefan.world.location.Location;
import kuchtastefan.world.location.LocationDB;
import kuchtastefan.world.region.Region;
import kuchtastefan.world.region.RegionDB;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileService {

    private final Gson gson = new GsonBuilder()

            .registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.actionsRuntimeTypeAdapterFactory)
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.itemsRuntimeTypeAdapterFactory)
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.locationStageRuntimeTypeAdapterFactory)
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.questRuntimeTypeAdapterFactory)
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.questObjectiveRuntimeTypeAdapterFactory)
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.gameCharactersRuntimeTypeAdapterFactory)
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.vendorRuntimeTypeAdapterFactory)
            .enableComplexMapKeySerialization().setPrettyPrinting().create();

    private final String savedGamesPath = "external-files/saved-games/";


    public void saveGame(Hero hero) {
        final GameLoaded gameLoaded = new GameLoaded(hero, HintDB.getHINT_DB()/*, hero.getHeroInventory().getHeroInventory()*/);
        gameLoaded.setVendorIdAndItemListId();

        while (true) {
            System.out.println("\tHow do you want to name your save?");
            final String name = InputUtil.stringScanner();
            final String path = this.savedGamesPath + name + ".json";

            if (new File(path).exists()) {
                if (!overwriteSaveGame()) {
                    continue;
                }
            }

            if (saveGame(gameLoaded, path, name)) {
                break;
            }
        }
    }

    public void autoSave(Hero hero) {
        if (GameSettingsDB.returnGameSettingValue(GameSetting.AUTO_SAVE)) {
            final GameLoaded gameLoaded = new GameLoaded(hero, HintDB.getHINT_DB()/*, hero.getHeroInventory().getHeroInventory()*/);
            gameLoaded.setVendorIdAndItemListId();

            final String path = this.savedGamesPath + hero.getNameWithoutColor() + "_AutoSave" + ".json";
            saveGame(gameLoaded, path, hero.getName());
        }
    }

    private boolean saveGame(GameLoaded gameLoaded, String path, String saveGameName) {
        String errorMessage;

        if (saveGameName.isEmpty()) {
            errorMessage = "Save game title can not be empty!";
        } else {
            try {
                final Writer writer = Files.newBufferedWriter(Paths.get(path));
                this.gson.toJson(gameLoaded, writer);
                writer.close();
                System.out.println(ConsoleColor.YELLOW + "\tGame saved!\n" + ConsoleColor.RESET);
                return true;
            } catch (IOException e) {
                errorMessage = "Error while saving game!";
            } catch (InvalidPathException e) {
                errorMessage = "Invalid characters in file name!";
            } catch (StackOverflowError e) {
                errorMessage = "";
                System.out.println(e.getMessage());
            }
        }

        System.out.println(ConsoleColor.RED + "\tSomething went wrong while saving the game!" + ConsoleColor.RESET);
        System.out.println("\t" + errorMessage + "\n");
        return false;
    }

    private boolean overwriteSaveGame() {
        System.out.println("\tGame with this name already exists!");
        System.out.println("\tDo you want to overwrite it?");
        PrintUtil.printMenuOptions("No", "Yes");
        int choice = InputUtil.intScanner();
        switch (choice) {
            case 0 -> {
                return false;
            }
            case 1 -> {
                return true;
            }
            default -> {
                PrintUtil.printEnterValidInput();
                return false;
            }
        }
    }

    public GameLoaded loadGame() {
        final List<String> listOfSavedGames = returnFileList(this.savedGamesPath);

        if (listOfSavedGames.isEmpty()) {
            return null;
        } else {
            try {
                String selectedSavedGame = selectSavedGame(listOfSavedGames);
                BufferedReader bufferedReader = new BufferedReader(new FileReader(selectedSavedGame));

                return this.gson.fromJson(bufferedReader, GameLoaded.class);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
    }

    private void printSavedGames(List<String> listOfSavedGames) {
        int index = 0;
        if (listOfSavedGames.isEmpty()) {
            System.out.println("\tList of saved games is empty");
        }

        for (String savedGame : listOfSavedGames) {
            PrintUtil.printIndexAndText(String.valueOf(index), savedGame.replace(".json", ""));
            System.out.println();
            index++;
        }
    }

    private String selectSavedGame(List<String> listOfSavedGames) {
        printSavedGames(listOfSavedGames);
        while (true) {
            try {
                int loadGameChoice = InputUtil.intScanner();
                return this.savedGamesPath + listOfSavedGames.get(loadGameChoice);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("\tEnter valid number");
            } catch (InvalidPathException e) {
                System.out.println("Save game path is invalid");
            }
        }
    }

    private List<String> returnFileList(String path) {
        try (Stream<Path> stream = Files.list(Paths.get(path))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    public void importWearableItemsFromFile() {

        String path = "external-files/items/wearable-item";
        try {
            for (String file : returnFileList(path)) {
                BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file));
                List<WearableItem> wearableItems = new Gson().fromJson(reader, new TypeToken<List<WearableItem>>() {
                }.getType());

                for (WearableItem wearableItem : wearableItems) {
                    wearableItem.setItemType(WearableItemType.valueOf(file.replace(".json", "").toUpperCase()));
                    wearableItemMissingValuesSet(wearableItem);
                    ItemDB.addItemToDB(wearableItem);
                }

                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void wearableItemMissingValuesSet(WearableItem wearableItem) {
        wearableItem.setPrice(70 * wearableItem.getItemLevel());

        if (wearableItem.getWearableItemQuality() == null) {
            wearableItem.setWearableItemQuality(WearableItemQuality.BASIC);
        }

        for (Ability ability : Ability.values()) {
            wearableItem.getAbilities().putIfAbsent(ability, 0);
        }
    }

    public void importCraftingReagentItemsFromFile() {
        String path = "external-files/items/crafting-reagent";
        try {
            for (String file : returnFileList(path)) {
                BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file));
                List<CraftingReagentItem> craftingReagentItemsList = new Gson().fromJson(reader, new TypeToken<List<CraftingReagentItem>>() {
                }.getType());

                for (CraftingReagentItem craftingReagentItem : craftingReagentItemsList) {
                    craftingReagentItem.setItemType(
                            CraftingReagentItemType.valueOf(file.toUpperCase().replace(".JSON", "")));

                    ItemDB.addItemToDB(craftingReagentItem);
                }

                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void importConsumableItemsFromFile() {
        String path = "external-files/items/consumable-item";
        try {
            for (String file : returnFileList(path)) {
                BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file));

                List<ConsumableItem> consumableItemList = this.gson.fromJson(reader, new TypeToken<List<ConsumableItem>>() {
                }.getType());

                for (ConsumableItem consumableItem : consumableItemList) {
                    consumableItem.setItemType(ConsumableItemType.valueOf(file.toUpperCase().replace(".JSON", "")));

                    for (Action action : consumableItem.getActionList()) {
                        action.setCurrentActionValue(action.getBaseActionValue());
                    }

                    ItemDB.addItemToDB(consumableItem);
                }


                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void importQuestItemsFromFile() {
        String path = "external-files/items/quest-item";
        try {
            for (String file : returnFileList(path)) {
                BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file));
                List<? extends QuestItem> questItemList;
                if (file.equals("usable_quest_item.json")) {
                    questItemList = new Gson().fromJson(reader, new TypeToken<List<UsableQuestItem>>() {
                    }.getType());
                } else {
                    questItemList = new Gson().fromJson(reader, new TypeToken<List<QuestItem>>() {
                    }.getType());
                }

                for (QuestItem questItem : questItemList) {
                    ItemDB.addItemToDB(questItem);
                }

                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void importJunkItemsFromFile() {
        String path = "external-files/items/junk-item";
        try {
            for (String file : returnFileList(path)) {

                BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file));
                List<JunkItem> junkItemList = new Gson().fromJson(reader, new TypeToken<List<JunkItem>>() {
                }.getType());

                for (JunkItem junkItem : junkItemList) {
                    if (junkItem.getItemLevel() == null) {
                        junkItem.setItemLevel(0);
                    }

                    ItemDB.addItemToDB(junkItem);
                }

                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void importKeyItemsFromFile() {
        String path = "external-files/items/key-item";
        try {
            for (String file : returnFileList(path)) {
                BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file));
                List<KeyItem> keyItemList = new Gson().fromJson(reader, new TypeToken<List<KeyItem>>() {
                }.getType());

                for (KeyItem keyItem : keyItemList) {
                    ItemDB.addItemToDB(keyItem);
                }

                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void importCreaturesFromFile() {
        String path = "external-files/creatures";
        try {
            for (String file : returnFileList(path)) {
                BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file));
                List<NonPlayerCharacter> characterList = this.gson.fromJson(reader, new TypeToken<List<NonPlayerCharacter>>() {
                }.getType());

                for (NonPlayerCharacter character : characterList) {
                    character.setCharacterType(CharacterType.valueOf(file.toUpperCase().replace(".JSON", "")));
                    CharacterDB.addCharacterToDB(character);
                }

                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void importQuestGiverFromFile() {
        String path = "external-files/quests/quest-giver";
        try {
            for (String file : returnFileList(path)) {
                BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file));

                List<QuestGiverCharacter> questGivers = this.gson.fromJson(reader, new TypeToken<List<QuestGiverCharacter>>() {
                }.getType());

                for (QuestGiverCharacter questGiverCharacter : questGivers) {
                    QuestGiverCharacterDB.addQuestGiverToDB(questGiverCharacter);
                }

                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void importQuestsListFromFile() {
        String path = "external-files/quests";

        try {
            for (String file : returnFileList(path)) {
                BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file));
                List<Quest> quests = this.gson.fromJson(reader, new TypeToken<List<Quest>>() {
                }.getType());

                for (Quest quest : quests) {
                    QuestDB.addQuestToDB(quest);
                }

                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void importQuestsObjectiveListFromFile() {
        String path = "external-files/quests/quest-objective";

        try {
            for (String file : returnFileList(path)) {
                BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file));
                List<QuestObjective> questObjectives = this.gson.fromJson(reader, new TypeToken<List<QuestObjective>>() {
                }.getType());

                for (QuestObjective questObjective : questObjectives) {
                    QuestObjectiveDB.addQuestObjectiveToDB(questObjective);
                }

                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Spell> importSpellsFromFile() {
        String path = "external-files/spells";
        List<Spell> spellList = new ArrayList<>();
        try {
            List<Spell> spells;
            for (String file : returnFileList(path)) {
                BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file));
                spells = this.gson.fromJson(reader, new TypeToken<List<Spell>>() {
                }.getType());

                for (Spell spell : spells) {
                    SpellDB.addSpellToDB(spell);
                }

                spellList.addAll(spells);
                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return spellList;
    }

    public void importLocationsFromFile() {
        String path = "external-files/locations";
        try {
            List<Location> locations;
            for (String file : returnFileList(path)) {
                BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file));
                locations = this.gson.fromJson(reader, new TypeToken<List<Location>>() {
                }.getType());

                for (Location location : locations) {
                    LocationDB.addLocationToDB(location);
                }

                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void importRegionsFromFile() {
        String path = "external-files/regions";
        try {
            List<Region> regions;
            for (String file : returnFileList(path)) {
                BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file));
                regions = this.gson.fromJson(reader, new TypeToken<List<Region>>() {
                }.getType());

                for (Region region : regions) {
                    RegionDB.addRegionToDB(region);
                }

                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void importEnemyGroupFromFile() {
        String path = "external-files/enemy-group";
        try {
            for (String file : returnFileList(path)) {
                BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file));

                EnemyGroupDB.ENEMY_GROUP_DB.addAll(this.gson.fromJson(reader, new TypeToken<List<EnemyGroup>>() {
                }.getType()));

                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void importNamesFromFile() {
        String path = "external-files/names/names.json";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            RandomNameGenerator.addNamesToDb(this.gson.fromJson(reader, new TypeToken<List<String>>() {
            }.getType()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void importVendorsFromFile() {
        String path = "external-files/shop-files/vendor.json";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));

            List<VendorCharacter> vendorCharacters = this.gson.fromJson(reader, new TypeToken<List<VendorCharacter>>() {
            }.getType());

            for (VendorCharacter vendorCharacter : vendorCharacters) {
                VendorCharacterDB.addVendorToDb(vendorCharacter);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void importVendorItemListsFromFile() {
        String path = "external-files/shop-files/vendor-item-lists.json";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));

            List<VendorItemList> vendorItemLists = this.gson.fromJson(reader, new TypeToken<List<VendorItemList>>() {
            }.getType());

            for (VendorItemList vendorItemList : vendorItemLists) {
                VendorOfferDB.addVendorItemsToDb(vendorItemList);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}

