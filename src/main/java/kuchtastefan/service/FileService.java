package kuchtastefan.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import kuchtastefan.ability.Ability;
import kuchtastefan.domain.GameLoaded;
import kuchtastefan.domain.Hero;
import kuchtastefan.hint.Hint;
import kuchtastefan.hint.HintName;
import kuchtastefan.inventory.HeroInventory;
import kuchtastefan.item.Item;
import kuchtastefan.item.consumeableItem.ConsumableItem;
import kuchtastefan.item.consumeableItem.ConsumableItemType;
import kuchtastefan.item.craftingItem.CraftingReagentItem;
import kuchtastefan.item.craftingItem.CraftingReagentItemType;
import kuchtastefan.item.questItem.QuestItem;
import kuchtastefan.item.wearableItem.WearableItem;
import kuchtastefan.item.wearableItem.WearableItemQuality;
import kuchtastefan.item.wearableItem.WearableItemType;
import kuchtastefan.regions.ForestRegionService;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileService {

    private final Gson gson = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create();
    private final String savedGamesPath = "external-files/saved-games/";

    public void saveGame(Hero hero, int currentLevel, Map<HintName, Hint> hintUtil, ForestRegionService forestRegionService) {
        GameLoaded gameLoaded = new GameLoaded(currentLevel, hero, hintUtil, forestRegionService.getDiscoveredLocations());
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

                for (Map<? extends Item, Integer> inventory : List.of(wearableItems, craftingReagentItems, consumableItems, questItems)) {
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
}

