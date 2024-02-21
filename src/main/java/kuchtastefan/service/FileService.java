package kuchtastefan.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import kuchtastefan.ability.Ability;
import kuchtastefan.actions.Action;
import kuchtastefan.character.enemy.Enemy;
import kuchtastefan.character.enemy.EnemyList;
import kuchtastefan.character.enemy.EnemyType;
import kuchtastefan.character.hero.CharacterClass;
import kuchtastefan.character.hero.GameLoaded;
import kuchtastefan.character.hero.Hero;
import kuchtastefan.character.spell.Spell;
import kuchtastefan.character.spell.SpellsList;
import kuchtastefan.hint.HintUtil;
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
import kuchtastefan.quest.QuestMap;
import kuchtastefan.regions.locations.Location;
import kuchtastefan.regions.locations.LocationMap;
import kuchtastefan.utility.InputUtil;
import kuchtastefan.utility.PrintUtil;
import kuchtastefan.utility.RuntimeTypeAdapterFactoryUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
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
            .enableComplexMapKeySerialization().setPrettyPrinting().create();
    private final String savedGamesPath = "external-files/saved-games/";

    public void saveGame(Hero hero, int currentLevel) {
        GameLoaded gameLoaded = new GameLoaded(currentLevel, hero, HintUtil.getHintList(),
                hero.getRegionActionsWithDuration(), hero.getHeroInventory().getHeroInventory());

        while (true) {
            System.out.println("How do you want to name your save?");
            final String name = InputUtil.stringScanner();

            final String path = this.savedGamesPath + name + ".json";

            if (new File(path).exists()) {
                System.out.println("\tGame with this name is already saved");
                System.out.println("\tDo you want to overwrite it?");
                PrintUtil.printIndexAndText("0", "No");
                System.out.println();
                PrintUtil.printIndexAndText("1", "Yes");
                System.out.println();
                int choice = InputUtil.intScanner();
                switch (choice) {
                    case 0 -> {
                        continue;
                    }
                    case 1 -> {
                    }
                    default -> {
                        PrintUtil.printEnterValidInput();
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
            PrintUtil.printIndexAndText(String.valueOf(index), savedGame.replace(".json", ""));
            System.out.println();
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
                    ItemsLists.getItemMapIdItem().put(wearableItem.getItemId(), wearableItem);
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

                    ItemsLists.getItemMapIdItem().put(craftingReagentItem.getItemId(), craftingReagentItem);
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

                    for (Action action : consumableItem.getActionList()) {
                        action.setNewCurrentActionValue(action.getMaxActionValue());
                    }

                    ItemsLists.getItemMapIdItem().put(consumableItem.getItemId(), consumableItem);
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

                for (QuestItem questItem : questItemList) {
                    ItemsLists.getItemMapIdItem().put(questItem.getItemId(), questItem);
                }


                questItems.addAll(questItemList);
                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return questItems;
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

    public List<Enemy> importCreaturesFromFile() {
        String path = "external-files/creatures";
        List<Enemy> enemies = new ArrayList<>();
        try {
            List<Enemy> enemyList;
            for (String file : returnFileList(path)) {
                BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file));
                enemyList = this.gson.fromJson(reader, new TypeToken<List<Enemy>>() {
                }.getType());

                for (Enemy enemy : enemyList) {
                    enemy.setEnemyType(
                            EnemyType.valueOf(file.toUpperCase().replace(".JSON", "")));

                    if (enemy.getCharacterSpellList() == null) {
                        enemy.setCharacterSpellList(new ArrayList<>());
                    }

                    System.out.println(enemy.getEnemyId());
                    enemy.addSpells();

                    if (enemy.getRegionActionsWithDuration() == null) {
                        enemy.setRegionActionsWithDuration(new HashSet<>());
                    }

                    if (enemy.getBattleActionsWithDuration() == null) {
                        enemy.setBattleActionsWithDuration(new HashSet<>());
                    }

                    EnemyList.getEnemyMap().put(enemy.getEnemyId(), enemy);
                }

                enemies.addAll(enemyList);
                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return enemies;
    }

    public void importQuestsListFromFile() {
        String path = "external-files/quests";

        try {
            for (String file : returnFileList(path)) {
                BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file));
                List<Quest> quests = this.gson.fromJson(reader, new TypeToken<List<Quest>>() {
                }.getType());

                for (Quest quest : quests) {
                    QuestMap.mapIdQuest.put(quest.getQuestId(), quest);
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
                    if (spell.getSpellClass().equals(CharacterClass.NPC)) {
                        SpellsList.getSpellMap().put(spell.getSpellId(), spell);
                    }

                    spell.setCanSpellBeCasted(true);
                    spell.setCurrentTurnCoolDown(spell.getTurnCoolDown() + 1);
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
                    location.setStageTotal(location.getLocationStages().size());
                    LocationMap.getMapIdLocation().put(location.getLocationId(), location);
                }

                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

