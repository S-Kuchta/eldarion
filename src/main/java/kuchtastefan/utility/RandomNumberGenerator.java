package kuchtastefan.utility;

import java.util.concurrent.ThreadLocalRandom;

public class RandomNumberGenerator {
    public static int getRandomNumber(int Min, int Max) {
        return ThreadLocalRandom.current().nextInt(Min, Max + 1);
    }
}
