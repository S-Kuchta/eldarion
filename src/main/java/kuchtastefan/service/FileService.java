package kuchtastefan.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import kuchtastefan.ability.Ability;
import kuchtastefan.domain.EquippedItems;
import kuchtastefan.domain.GameLoaded;
import kuchtastefan.domain.Hero;
import kuchtastefan.item.Item;
import kuchtastefan.item.ItemType;
import kuchtastefan.utility.InputUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileService {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void saveGame(Hero hero, int currentLevel) {

        EquippedItems equippedItems = hero.getEquippedItems();
        String jsonStr = this.gson.toJson(equippedItems);
        System.out.println(jsonStr);

        while (true) {
            System.out.println("How do you want to name your save?");
            final String name = InputUtil.stringScanner();

            File file = new File("external-files/saved-games/" + name);
            boolean bool = file.mkdir();
            if (bool) {
                System.out.println("Folder created successfully");
            } else {
                System.out.println("Something went wrong");
            }

            final String pathForItems = "external-files/saved-games/" + name + "/" + name + "EquippedItem.json";
            final String path = "external-files/saved-games/" + name + ".txt";

            if (new File(path).exists()) {
                System.out.println("Game with this name is already saved");
                continue;
            }

            System.out.println(hero.getEquippedItems());

            try {
                Files.writeString(Path.of(path), generateSave(hero, currentLevel));
                Writer writer = Files.newBufferedWriter(Paths.get(pathForItems));
                this.gson.toJson(equippedItems, writer);
                writer.close();
                System.out.println("Game saved");
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

    private String generateSave(Hero hero, int currentLevel) {
        StringBuilder saveGame = new StringBuilder();
        saveGame.append(currentLevel).append(System.lineSeparator());
        saveGame.append(hero.getName()).append(System.lineSeparator());
        saveGame.append(hero.getUnspentAbilityPoints()).append(System.lineSeparator());



        for (Map.Entry<Ability, Integer> entry : hero.getAbilities().entrySet()) {
            hero.getWearingItemAbilityPoints().putIfAbsent(entry.getKey(), 0);
//            System.out.println(hero.getWearingItemAbilityPoints().get(entry.getKey()));
            saveGame.append(entry.getKey()).append(":")
                    .append(entry.getValue() - hero.getWearingItemAbilityPoints()
                    .get(entry.getKey()))
                    .append(System.lineSeparator());
        }
        return saveGame.toString();
    }

    public GameLoaded loadGame() {

        List<String> listOfSavedGames = returnFileList("external-files/saved-games");
        if (listOfSavedGames.isEmpty()) {
            return null;
        } else {
            String selectedSavedGame = selectSaveGame(listOfSavedGames);
            return setHeroAbilities(new File(selectedSavedGame));
        }
    }

    private EquippedItems loadEquippedItems(String selectedSaveGame) {
        final String pathForItems = "external-files/saved-games/" + selectedSaveGame + "/" + selectedSaveGame + "EquippedItem.json";
        EquippedItems equippedItems = new EquippedItems();

        try {
            FileReader fileReader = new FileReader(pathForItems);
            equippedItems = this.gson.fromJson(fileReader, EquippedItems.class);
            fileReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return equippedItems;
    }

    private GameLoaded setHeroAbilities(File file) {
        int currentLevel = 0;
        int unspentAbilityPoints = 0;
        String heroName = "";
        Map<Ability, Integer> abilities = new HashMap<>();

        try {
            Scanner scanner = new Scanner(file);
            int positionIndex = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                switch (positionIndex) {
                    case 0 -> currentLevel = Integer.parseInt(line);
                    case 1 -> heroName = line;
                    case 2 -> unspentAbilityPoints = Integer.parseInt(line);
                }
                String[] parts = line.split(":");
                if (parts.length >= 2) {
                    abilities.put(Ability.valueOf(parts[0]), Integer.parseInt(parts[1]));
                }
                positionIndex++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        EquippedItems equippedItems = loadEquippedItems(file.getName().replace(".txt", ""));

        return new GameLoaded(currentLevel, new Hero(heroName, abilities, unspentAbilityPoints), equippedItems);
    }

    private void printSavedGames(List<String> listOfSavedGames) {
        int index = 0;
        if (listOfSavedGames.isEmpty()) {
            System.out.println("List of saved games is empty");
        }

        for (String savedGame : listOfSavedGames) {
            System.out.println(index + ". " + savedGame.replace(".txt", ""));
            index++;
        }
    }

    private String selectSaveGame(List<String> listOfSavedGames) {
        printSavedGames(listOfSavedGames);
        while (true) {
            try {
                int loadGameChoice = InputUtil.intScanner();
                return "external-files/saved-games/" + listOfSavedGames.get(loadGameChoice);
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
            List<Item> item;
            for (String file : returnFileList(path)) {
                BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file));
                item = new Gson().fromJson(reader, new TypeToken<List<Item>>() {
                }.getType());

                for (Item item1 : item) {
                    item1.setItemType(ItemType.valueOf(file.replace(".json", "").toUpperCase()));
                }
                itemList.addAll(item);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return itemList;
    }
}

