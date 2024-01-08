package kuchtastefan.items.wearableItem;

public enum WearableItemType {

    WEAPON("Weapon"),
    HEAD("Head armor"),
    BODY("Body armor"),
    HANDS("Hands armor"),
    BOOTS("Boots armor");

    private final String description;

    WearableItemType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
