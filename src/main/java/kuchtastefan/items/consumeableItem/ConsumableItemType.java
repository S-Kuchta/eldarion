package kuchtastefan.items.consumeableItem;

public enum ConsumableItemType {
    POTION("Potions are used for restore health, increase ability etc. Can be used during combat"),
    FOOD("Food is used for restore health. Can be used only out of combat");

    private final String description;

    ConsumableItemType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
