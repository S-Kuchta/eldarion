package kuchtastefan.service;

import kuchtastefan.ability.Ability;
import kuchtastefan.domain.Hero;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class FileService {

    private final Hero hero;

    public FileService(Hero hero) {
        this.hero = hero;
    }

    public void saveGame(int currentLevel) {
        try {
            Files.writeString(Path.of(generateFileName().toString()), generateSaveFile(currentLevel));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private StringBuilder generateFileName() {
        StringBuilder fileName = new StringBuilder();
        fileName.append("saved-games/");
        fileName.append(this.hero.getName());
        fileName.append(".txt");
        return fileName;
    }

    private StringBuilder generateSaveFile(int currentLevel) {
        StringBuilder saveGame = new StringBuilder();
        saveGame.append(currentLevel);
        saveGame.append(System.lineSeparator());
        saveGame.append(this.hero.getName());
        saveGame.append(System.lineSeparator());
        saveGame.append(this.hero.getUnspentAbilityPoints());
        saveGame.append(System.lineSeparator());
        for (Map.Entry<Ability, Integer> entry : this.hero.getAbilities().entrySet()) {
            saveGame.append(entry.getKey());
            saveGame.append(":");
            saveGame.append(entry.getValue());
            saveGame.append(System.lineSeparator());
        }
        return saveGame;
    }
}
