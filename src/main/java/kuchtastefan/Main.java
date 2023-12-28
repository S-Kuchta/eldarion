package kuchtastefan;

import kuchtastefan.service.GameManager;
import kuchtastefan.utility.PrintUtil;

public class Main {

    public static void main(String[] args) {

        PrintUtil.printStringLetterByLetter("You unlocked new Achievements with private contributions!\nShow them off by including private contributions in your Profile in settings.\nYou unlocked new Achievements with private contributions!\nShow them off by including private contributions in your Profile in settings.");

        GameManager gameManager = new GameManager();
        gameManager.startGame();
    }
}