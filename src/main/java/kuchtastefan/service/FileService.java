package kuchtastefan.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import kuchtastefan.ability.Ability;
import kuchtastefan.domain.GameLoaded;
import kuchtastefan.domain.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemQuality;
import kuchtastefan.item.ItemType;
import kuchtastefan.utility.InputUtil;

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

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final String savedGamesPath = "external-files/saved-games/";

    public void saveGame(Hero hero, int currentLevel) {
        GameLoaded gameLoaded = new GameLoaded(currentLevel, hero);

        while (true) {
            System.out.println("How do you want to name your save?");
            final String name = InputUtil.stringScanner();

            final String path = this.savedGamesPath + name + ".json";

            if (new File(path).exists()) {
                System.out.println("Game with this name is already saved");
                System.out.println("Do you want to overwrite it?");
                System.out.println("0. no");
                System.out.println("1. yes");
                int choice = InputUtil.intScanner();
                switch (choice) {
                    case 0 -> {
                        continue;
                    }
                    case 1 -> {
                    }
                    default -> {
                        System.out.println("Enter valid number");
                        continue;
                    }
                }
            }

            try {
                Writer writer = Files.newBufferedWriter(Paths.get(path));
                this.gson.toJson(gameLoaded, writer);
                System.out.println("Game Saved");
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
                return gson.fromJson(bufferedReader, GameLoaded.class);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
    }

    private void printSavedGames(List<String> listOfSavedGames) {
        int index = 0;
        if (listOfSavedGames.isEmpty()) {
            System.out.println("List of saved games is empty");
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
                System.out.println("Enter valid number");
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

    public List<Item> item(List<Item> itemList) {
        String path = "external-files/items";
        try {
            List<Item> items;
            for (String file : returnFileList(path)) {
                BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file));
                items = new Gson().fromJson(reader, new TypeToken<List<Item>>() {
                }.getType());

                for (Item item : items) {
                    item.setItemType(ItemType.valueOf(file.replace(".json", "").toUpperCase()));
                    item.setPrice(50 * item.getItemLevel());
                    if (item.getItemQuality() == null) {
                        item.setItemQuality(ItemQuality.BASIC);
                    }

                    for (Ability ability : Ability.values()) {
                        item.getAbilities().putIfAbsent(ability, 0);
                    }
                }
                itemList.addAll(items);
                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return itemList;
    }

    private Item improveQualityOfItem(Item item, ItemQuality itemQuality) {
        item.setPrice(50 * item.getItemLevel());
        for (Ability ability : Ability.values()) {
            item.getAbilities().putIfAbsent(ability, 0);
        }
        item.setItemQuality(itemQuality);
        item.setName(item.getName() + " (" + itemQuality + ")");
        if (itemQuality == ItemQuality.BASIC) {
            return item;
        } else if (itemQuality == ItemQuality.IMPROVED) {
            item.setPrice(item.getPrice() * 2);
            for (Ability ability : Ability.values()) {
                item.getAbilities().put(ability, item.getAbilities().get(ability) + 1);
            }
            return item;
        } else {
            item.setPrice(item.getPrice() * 3);
            for (Ability ability : Ability.values()) {
                item.getAbilities().put(ability, item.getAbilities().get(ability) + 2);
            }
            return item;
        }
    }
}

