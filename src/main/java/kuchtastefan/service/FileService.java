package kuchtastefan.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import kuchtastefan.ability.Ability;
import kuchtastefan.characters.enemy.Enemy;
import kuchtastefan.characters.enemy.EnemyType;
import kuchtastefan.characters.hero.GameLoaded;
import kuchtastefan.characters.hero.Hero;
import kuchtastefan.characters.hero.inventory.HeroInventory;
import kuchtastefan.hint.HintUtil;
import kuchtastefan.items.Item;
import kuchtastefan.items.ItemsLists;
import kuchtastefan.items.consumeableItem.ConsumableItem;
import kuchtastefan.items.consumeableItem.ConsumableItemType;
import kuchtastefan.items.craftingItem.CraftingReagentItem;
import kuchtastefan.items.craftingItem.CraftingReagentItemType;
import kuchtastefan.items.junkItem.JunkItem;
import kuchtastefan.items.questItem.QuestItem;
import kuchtastefan.items.wearableItem.WearableItem;
import kuchtastefan.items.wearableItem.WearableItemQuality;
import kuchtastefan.items.wearableItem.WearableItemType;
import kuchtastefan.quest.Quest;
import kuchtastefan.quest.QuestList;
import kuchtastefan.quest.questObjectives.QuestBringItemObjective;
import kuchtastefan.quest.questObjectives.QuestClearLocation;
import kuchtastefan.quest.questObjectives.QuestKillObjective;
import kuchtastefan.quest.questObjectives.QuestObjective;
import kuchtastefan.regions.ForestRegionService;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileService {
    private final RuntimeTypeAdapterFactory<QuestObjective> questObjectiveRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(QuestObjective.class)
            .registerSubtype(QuestKillObjective.class)
            .registerSubtype(QuestBringItemObjective.class)
            .registerSubtype(QuestClearLocation.class);

    private final RuntimeTypeAdapterFactory<Item> itemRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(Item.class)
            .registerSubtype(ConsumableItem.class)
            .registerSubtype(CraftingReagentItem.class)
            .registerSubtype(WearableItem.class)
            .registerSubtype(QuestItem.class)
            .registerSubtype(JunkItem.class);

    private final Gson gson = new GsonBuilder().registerTypeAdapterFactory(this.questObjectiveRuntimeTypeAdapterFactory).enableComplexMapKeySerialization().setPrettyPrinting().create();
    private final String savedGamesPath = "external-files/saved-games/";

    public void saveGame(Hero hero, int currentLevel, ForestRegionService forestRegionService) {
        GameLoaded gameLoaded = new GameLoaded(currentLevel, hero, HintUtil.getHintList(), forestRegionService.getDiscoveredLocations());
        Map<Item, Integer> tempItemMap = new HashMap<>(gameLoaded.getHero().getHeroInventory().getHeroInventory());
        gameLoaded.getHero().getHeroInventory().changeList();

        while (true) {
            System.out.println("How do you want to name your save?");
            final String name = InputUtil.stringScanner();

            final String path = this.savedGamesPath + name + ".json";

            if (new File(path).exists()) {
                System.out.println("\tGame with this name is already saved");
                System.out.println("\tDo you want to overwrite it?");
                System.out.println("\t0. no");
                System.out.println("\t1. yes");
                int choice = InputUtil.intScanner();
                switch (choice) {
                    case 0 -> {
                        continue;
                    }
                    case 1 -> {
                    }
                    default -> {
                        System.out.println("\tEnter valid input");
                        continue;
                    }
                }
            }

            try {
                Writer writer = Files.newBufferedWriter(Paths.get(path));
                this.gson.toJson(gameLoaded, writer);

                PrintUtil.printDivider();
                System.out.println("\tGame Saved");
                PrintUtil.printDivider();

                hero.getHeroInventory().getHeroInventory().putAll(tempItemMap);
                writer.close();
            } catch (IOException e) {
                System.out.println("Error while saving game");
                continue;
            } catch (InvalidPathException e) {
                System.out.println("Invalid characters in file name");
                continue;
            }

            break;
        }
    }

    public GameLoaded loadGame() {
        List<String> listOfSavedGames = returnFileList(this.savedGamesPath);

        if (listOfSavedGames.isEmpty()) {
            return null;
        } else {
            try {
                String selectedSavedGame = selectSaveGame(listOfSavedGames);
                BufferedReader bufferedReader = new BufferedReader(new FileReader(selectedSavedGame));

                GameLoaded gameLoaded = gson.fromJson(bufferedReader, GameLoaded.class);

                HeroInventory itemInventoryList = gameLoaded.getHero().getHeroInventory();
                Map<Item, Integer> heroInventory = itemInventoryList.getHeroInventory();

                Map<WearableItem, Integer> wearableItems = itemInventoryList.getWearableItemInventory();
                Map<CraftingReagentItem, Integer> craftingReagentItems = itemInventoryList.getCraftingReagentItemInventory();
                Map<ConsumableItem, Integer> consumableItems = itemInventoryList.getConsumableItemInventory();
                Map<QuestItem, Integer> questItems = itemInventoryList.getQuestItemInventory();
                Map<JunkItem, Integer> junkItems = itemInventoryList.getJunkItemInventory();

                for (Map<? extends Item, Integer> inventory : List.of(wearableItems, craftingReagentItems, consumableItems, questItems, junkItems)) {
                    heroInventory.putAll(inventory);
                    inventory.clear();
                }

                return gameLoaded;
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
            System.out.println(index + ". " + savedGame.replace(".json", ""));
            index++;
        }
    }

    private String selectSaveGame(List<String> listOfSavedGames) {
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

    public List<WearableItem> importWearableItemsFromFile() {

        List<WearableItem> wearableItemList = new ArrayList<>();
        String path = "external-files/items/wearable-item";
        try {
            List<WearableItem> WearableItems;
            for (String file : returnFileList(path)) {
                BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file));
                WearableItems = new Gson().fromJson(reader, new TypeToken<List<WearableItem>>() {
                }.getType());

                for (WearableItem wearableItem : WearableItems) {
                    wearableItem.setWearableItemType(WearableItemType.valueOf(file.replace(".json", "").toUpperCase()));
                    wearableItem.setPrice(50 * wearableItem.getItemLevel());
                    if (wearableItem.getItemQuality() == null) {
                        wearableItem.setItemQuality(WearableItemQuality.BASIC);
                    }

                    for (Ability ability : Ability.values()) {
                        wearableItem.getAbilities().putIfAbsent(ability, 0);
                    }
                }
                wearableItemList.addAll(WearableItems);
                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return wearableItemList;
    }

    public List<CraftingReagentItem> importCraftingReagentItemsFromFile() {
        String path = "external-files/items/crafting-reagent";
        List<CraftingReagentItem> craftingReagents = new ArrayList<>();
        try {
            List<CraftingReagentItem> craftingReagentItemsList;
            for (String file : returnFileList(path)) {
                BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file));
                craftingReagentItemsList = new Gson().fromJson(reader, new TypeToken<List<CraftingReagentItem>>() {
                }.getType());

                for (CraftingReagentItem craftingReagentItem : craftingReagentItemsList) {
                    craftingReagentItem.setCraftingReagentItemType(
                            CraftingReagentItemType.valueOf(file.toUpperCase().replace(".JSON", "")));
                }
                craftingReagents.addAll(craftingReagentItemsList);
                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return craftingReagents;
    }

    public List<ConsumableItem> importConsumableItemsFromFile() {
        String path = "external-files/items/consumable-item";
        List<ConsumableItem> consumableItems = new ArrayList<>();
        try {
            List<ConsumableItem> consumableItemList;
            for (String file : returnFileList(path)) {
                BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file));
                consumableItemList = new Gson().fromJson(reader, new TypeToken<List<ConsumableItem>>() {
                }.getType());

                for (ConsumableItem consumableItem : consumableItemList) {
                    consumableItem.setConsumableItemType(
                            ConsumableItemType.valueOf(file.toUpperCase().replace(".JSON", "")));

                    if (consumableItem.getIncreaseAbilityPoint() == null) {
                        consumableItem.setIncreaseAbilityPoint(new HashMap<>());
                    }

                    for (Ability ability : Ability.values()) {
                        consumableItem.getIncreaseAbilityPoint().putIfAbsent(ability, 0);
                    }

                    if (consumableItem.getRestoreAmount() == null) {
                        consumableItem.setRestoreAmount(0);
                    }
                }
                consumableItems.addAll(consumableItemList);
                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return consumableItems;
    }

    public List<QuestItem> importQuestItemsFromFile() {
        String path = "external-files/items/quest-item";
        List<QuestItem> questItems = new ArrayList<>();
        try {
            List<QuestItem> questItemList;
            for (String file : returnFileList(path)) {
                BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file));
                questItemList = new Gson().fromJson(reader, new TypeToken<List<QuestItem>>() {
                }.getType());

                questItems.addAll(questItemList);
                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return questItems;
    }

    public List<Enemy> importCreaturesFromFile(ItemsLists itemsLists) {
        String path = "external-files/creatures";
        List<Enemy> enemies = new ArrayList<>();
        try {
            List<Enemy> enemyList;
            for (String file : returnFileList(path)) {
                BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file));
                enemyList = new Gson().fromJson(reader, new TypeToken<List<Enemy>>() {
                }.getType());

                for (Enemy enemy : enemyList) {
                    enemy.setEnemyType(
                            EnemyType.valueOf(file.toUpperCase().replace(".JSON", "")));

                    enemy.setItemsLists(itemsLists);
                }

                enemies.addAll(enemyList);
                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return enemies;
    }

    public List<JunkItem> importJunkItemsFromFile() {
        String path = "external-files/items/junk-item";
        List<JunkItem> junkItems = new ArrayList<>();
        try {
            List<JunkItem> junkItemList;
            for (String file : returnFileList(path)) {
                BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file));
                junkItemList = new Gson().fromJson(reader, new TypeToken<List<JunkItem>>() {
                }.getType());

                for (JunkItem junkItem : junkItemList) {
                    if (junkItem.getItemLevel() == null) {
                        junkItem.setItemLevel(0);
                    }
                }

                junkItems.addAll(junkItemList);
                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return junkItems;
    }

//    public List<Quest> importQuestsListFromFile() {
//        String path = "external-files/quests";
//        List<Quest> questList = new ArrayList<>();
//        try {
//            List<? extends Quest> quests;
//            for (String file : returnFileList(path)) {
//                BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file));
//                quests = new Gson().fromJson(reader, new TypeToken<List<Quest>>() {
//                }.getType());
//
//                questList.addAll(quests);
//
//                reader.close();
//            }
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//
//        return questList;
//    }

    public List<Quest> importQuestsListFromFile() {
        String path = "external-files/quests";
        List<Quest> questList = new ArrayList<>();
        try {
            for (String file : returnFileList(path)) {
                BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file));
                Quest[] quests = gson.fromJson(reader, Quest[].class);
                questList = Arrays.asList(quests);

                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        questList.sort(Comparator.comparingInt(Quest::getQuestId));
        return questList;
    }
}

