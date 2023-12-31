package kuchtastefan.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import kuchtastefan.ability.Ability;
import kuchtastefan.domain.GameLoaded;
import kuchtastefan.domain.Hero;
import kuchtastefan.item.wearableItem.wearableItem;
import kuchtastefan.item.wearableItem.wearableItemQuality;
import kuchtastefan.item.wearableItem.wearableItemType;
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

    public List<wearableItem> item(List<wearableItem> wearableItemList) {
        String path = "external-files/items";
        try {
            List<wearableItem> wearableItems;
            for (String file : returnFileList(path)) {
                BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file));
                wearableItems = new Gson().fromJson(reader, new TypeToken<List<wearableItem>>() {
                }.getType());

                for (wearableItem wearableItem : wearableItems) {
                    wearableItem.setItemType(wearableItemType.valueOf(file.replace(".json", "").toUpperCase()));
                    wearableItem.setPrice(50 * wearableItem.getItemLevel());
                    if (wearableItem.getItemQuality() == null) {
                        wearableItem.setItemQuality(wearableItemQuality.BASIC);
                    }

                    for (Ability ability : Ability.values()) {
                        wearableItem.getAbilities().putIfAbsent(ability, 0);
                    }
                }
                wearableItemList.addAll(wearableItems);
                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return wearableItemList;
    }

    private wearableItem improveQualityOfItem(wearableItem wearableItem, wearableItemQuality wearableItemQuality) {
        wearableItem.setPrice(50 * wearableItem.getItemLevel());
        for (Ability ability : Ability.values()) {
            wearableItem.getAbilities().putIfAbsent(ability, 0);
        }
        wearableItem.setItemQuality(wearableItemQuality);
        wearableItem.setName(wearableItem.getName() + " (" + wearableItemQuality + ")");
        if (wearableItemQuality == wearableItemQuality.BASIC) {
            return wearableItem;
        } else if (wearableItemQuality == wearableItemQuality.IMPROVED) {
            wearableItem.setPrice(wearableItem.getPrice() * 2);
            for (Ability ability : Ability.values()) {
                wearableItem.getAbilities().put(ability, wearableItem.getAbilities().get(ability) + 1);
            }
            return wearableItem;
        } else {
            wearableItem.setPrice(wearableItem.getPrice() * 3);
            for (Ability ability : Ability.values()) {
                wearableItem.getAbilities().put(ability, wearableItem.getAbilities().get(ability) + 2);
            }
            return wearableItem;
        }
    }
}

