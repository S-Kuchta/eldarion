package kuchtastefan.utility;

import lombok.Getter;

@Getter
public enum LetterToNumber {

    A(1),
    B(2),
    C(3),
    D(4),
    E(5);

    private final int value;

    LetterToNumber(int value) {
        this.value = value;
    }

    public static String getStringFromValue(int value) {
        for (LetterToNumber enumValue : LetterToNumber.values()) {
            if (enumValue.value == value) {
                return enumValue.name();
            }
        }
        return null;
    }
}
