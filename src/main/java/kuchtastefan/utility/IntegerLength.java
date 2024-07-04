package kuchtastefan.utility;

public class IntegerLength {

    public static int getIntegerLength(int number) {
        return (int) (Math.log10(number) + 1);
    }
}
