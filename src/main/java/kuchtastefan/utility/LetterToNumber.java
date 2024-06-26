package kuchtastefan.utility;

import lombok.Getter;

@Getter
public enum LetterToNumber {

    A(1),
    B(2),
    C(3),
    D(4),
    E(5),
    F(6),
    G(7),
    H(8),
    I(9),
    J(10),
    K(11),
    L(12),
    M(13),
    N(14),
    O(15),
    P(16);

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
