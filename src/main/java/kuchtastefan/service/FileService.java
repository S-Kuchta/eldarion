package kuchtastefan.service;

import kuchtastefan.ability.Ability;
import kuchtastefan.domain.Hero;
import kuchtastefan.utility.InputUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class FileService {
    public void saveGame(Hero hero, int currentLevel) {
        while (true) {
            System.out.println("How do you want to name your save?");
            final String name = InputUtils.readString();

            final String path = "saved-games/" + name + ".txt";

            if(new File(path).exists()) {
                System.out.println("Game with this name is already saved");
                continue;
            }

            try {
                Files.writeString(Path.of(path), this.heroDataToString(hero, currentLevel));
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

    private String heroDataToString(Hero hero, int currentLevel) {
        final StringBuilder sb = new StringBuilder();
        sb.append(currentLevel).append("\n");
        sb.append(hero.getName()).append("\n");
        sb.append(hero.getHeroAvailablePoints()).append("\n");
        for (Ability ability : Ability.values()) {
            sb.append(ability).append(":").append(hero.getAbilities().get(ability)).append("\n");
        }
        return sb.toString();
    }
}
