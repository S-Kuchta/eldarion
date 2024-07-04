package kuchtastefan.utility;

public class CharacterCheck {

    public static boolean containsCharacter(String choice, String... specialCharacters) {
        for (String character : specialCharacters) {
            if (choice.contains(character)) {
                return true;
            }
        }

        return false;
    }
}
