package kuchtastefan.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import kuchtastefan.ability.Ability;
import kuchtastefan.characters.enemy.Enemy;
import kuchtastefan.characters.enemy.EnemyType;
import kuchtastefan.characters.hero.GameLoaded;
import kuchtastefan.characters.hero.Hero;
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
import kuchtastefan.regions.ForestRegionService;
import kuchtastefan.spell.Spell;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import kuchtastefan.utility.RuntimeTypeAdapterFactoryUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileService {
    private final Gson gson = new GsonBuilder().registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.actionsRuntimeTypeAdapterFactory)
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.itemsRuntimeTypeAdapterFactory)
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.actionsWithDurationTypeAdapterFactory)
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.questObjectiveRuntimeTypeAdapterFactory)
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactoryUtil.spellObjectiveRuntimeTypeAdapterFactory)
            .enableComplexMapKeySerialization().setPrettyPrinting().create();
    private final String savedGamesPath = "external-files/saved-games/";

    public void saveGame(Hero hero, int currentLevel, ForestRegionService forestRegionService) {
        GameLoaded gameLoaded = new GameLoaded(currentLevel, hero, HintUtil.getHintList(), forestRegionService.getDiscoveredLocations(), hero.getRegionActionsWithDuration(), hero.getHeroInventory().getHeroInventory());

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

                writer.close();
            } catch (IOException e) {
                System.out.println("\tError while saving game");
                continue;
            } catch (InvalidPathException e) {
                System.out.println("\tInvalid characters in file name");
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
                    wearableItemMissingValuesSet(wearableItem);
                }
                wearableItemList.addAll(WearableItems);
                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return wearableItemList;
    }

    private void wearableItemMissingValuesSet(WearableItem wearableItem) {
        wearableItem.setPrice(70 * wearableItem.getItemLevel());

        if (wearableItem.getWearableItemQuality() == null) {
            wearableItem.setItemQuality(WearableItemQuality.BASIC);
        }

        for (Ability ability : Ability.values()) {
            wearableItem.getAbilities().putIfAbsent(ability, 0);
        }
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

                consumableItemList = this.gson.fromJson(reader, new TypeToken<List<ConsumableItem>>() {
                }.getType());

                for (ConsumableItem consumableItem : consumableItemList) {
                    consumableItem.setConsumableItemType(
                            ConsumableItemType.valueOf(file.toUpperCase().replace(".JSON", "")));
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

    public List<Enemy> importCreaturesFromFile() {
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

    public List<Quest> importQuestsListFromFile() {
        String path = "external-files/quests";
        List<Quest> questList = new ArrayList<>();
        try {
            List<Quest> quests;
            for (String file : returnFileList(path)) {
                BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file));
                quests = this.gson.fromJson(reader, new TypeToken<List<Quest>>() {
                }.getType());

                for (Quest quest : quests) {
                    if (quest.getQuestReward().getItemsReward().isEmpty()) {
                        quest.getQuestReward().generateRandomWearableItemsReward(
                                1, ItemsLists.returnWearableItemListByItemLevel(
                                        quest.getQuestLevel(), null, false)
                        );
                    }

                    for (Item item : quest.getQuestReward().getItemsReward()) {
                        if (item instanceof WearableItem) {
                            wearableItemMissingValuesSet((WearableItem) item);
                        }
                    }

                }

                questList.addAll(quests);
                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        questList.sort(Comparator.comparingInt(Quest::getQuestId));
        return questList;
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
                    spell.setCanSpellBeCasted(true);
                    spell.setCurrentTurnCoolDown(spell.getTurnCoolDown());
                }

                spellList.addAll(spells);
                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return spellList;
    }
}

