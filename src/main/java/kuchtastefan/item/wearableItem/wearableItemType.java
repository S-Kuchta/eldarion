package kuchtastefan.item.wearableItem;

public enum wearableItemType {

    WEAPON("Weapon"),
    HEAD("Head armor"),
    BODY("Body armor"),
    HANDS("Hands armor"),
    BOOTS("Boots armor");

    private final String description;

    wearableItemType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
