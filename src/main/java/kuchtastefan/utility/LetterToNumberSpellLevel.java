package kuchtastefan.utility;

import lombok.Getter;

@Getter
public enum LetterToNumberSpellLevel {

    A(1),
    B(3),
    C(5),
    D(7),
    E(9);

    private final int value;

    LetterToNumberSpellLevel(int value) {
        this.value = value;
    }

    public static String getStringFromValue(int value) {
        for (LetterToNumberSpellLevel enumValue : LetterToNumberSpellLevel.values()) {
            if (enumValue.value == value) {
                return enumValue.name();
            }
        }
        return null;
    }
}
