package kuchtastefan.utility;

import java.util.ArrayList;
import java.util.List;

public class RandomNameGenerator {
    private static final List<String> NAME_DB = new ArrayList<>();

    public static void addNamesToDb(List<String> names) {
        NAME_DB.addAll(names);
    }

    public static String getRandomName() {
        return NAME_DB.get(RandomNumberGenerator.getRandomNumber(0, NAME_DB.size() - 1));
    }
}
