package kuchtastefan.actions.actionValue;

public record ActionValueRange(int minimumValue, int maximumValue) {
    public int getOnlyValue() {
        return minimumValue;
    }
}
