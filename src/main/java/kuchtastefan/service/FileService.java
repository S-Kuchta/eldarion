package kuchtastefan.service;

import kuchtastefan.ability.Ability;
import kuchtastefan.domain.GameLoaded;
import kuchtastefan.domain.Hero;
import kuchtastefan.utility.InputUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileService {

    public void saveGame(Hero hero, int currentLevel) {
        while (true) {
            System.out.println("How do you want to name your save?");
            final String name = InputUtil.stringScanner();

            final String path = "saved-games/" + name + ".txt";

            if (new File(path).exists()) {
                System.out.println("Game with this name is already saved");
                continue;
            }

            try {
                Files.writeString(Path.of(path), generateSave(hero, currentLevel));
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
            saveGame.append(entry.getKey()).append(":").append(entry.getValue()).append(System.lineSeparator());
        }
        return saveGame.toString();
    }

    public GameLoaded loadGame() {

        if(gameLoaded() == null) {
            return null;
        } else {
            System.out.println("Game loaded!");
            return gameLoaded();
        }

    }

    private GameLoaded gameLoaded() {
        int currentLevel = 0;
        int unspentAbilityPoints = 0;
        String heroName = "";
        Map<Ability, Integer> abilities = new HashMap<>();


        try {
            File file = new File(selectSaveGame());
            if (selectSaveGame().isEmpty()) {
                return null;
            }
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
        } catch (InvalidPathException e) {
            System.out.println("Invalid characters in file name!");
        }

        return new GameLoaded(
                currentLevel,
                new Hero(heroName, abilities, unspentAbilityPoints)
        );
    }

    private void printSavedGames(Set<String> listOfSavedGames) {
        int index = 0;
        if (listOfSavedGames.isEmpty()) {
            System.out.println("List of saved games is empty");
        }

        for (String savedGame : listOfSavedGames) {
            System.out.println(index + ". " + savedGame.replace(".txt", ""));
            index++;
        }
    }

    private String selectSaveGame() {
        Set<String> listOfSavedGames = Objects.requireNonNull(returnFileList());
        List<String> convertedListOfSavedGames = new ArrayList<>(listOfSavedGames);

        printSavedGames(listOfSavedGames);
        while (true) {
            try {
                int loadGameChoice = InputUtil.intScanner();
                return "saved-games/" + convertedListOfSavedGames.get(loadGameChoice);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Enter valid number");
            }
        }
    }

    private Set<String> returnFileList() {
        try (Stream<Path> stream = Files.list(Paths.get("saved-games"))) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
